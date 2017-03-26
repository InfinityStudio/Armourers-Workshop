package riskyken.armourersWorkshop.common.skin.data.serialize;

import net.skin43d.impl.Context;
import org.apache.logging.log4j.Level;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.exception.InvalidCubeTypeException;
import riskyken.armourersWorkshop.common.skin.cubes.CubeMarkerData;
import riskyken.armourersWorkshop.common.skin.data.SkinCubeData;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;
import net.skin43d.utils.ModLogger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ci010
 */
public class SkinPartReader {
    public void writeToStream(SkinPart part, DataOutput output) throws IOException {
        output.writeUTF(part.getPartType().getRegistryName());
        part.getCubeData().writeToStream(output);
        List<CubeMarkerData> markerBlocks = part.getMarkerBlocks();
        output.writeInt(markerBlocks.size());
        for (CubeMarkerData markerBlock : markerBlocks)
            writeCubeMaker(output, markerBlock);
    }

    public SkinPart readSkinPart(DataInput input, Context context) throws IOException, InvalidCubeTypeException {
        SkinPartType skinPart;
        if (context.getFileVersion() < 6) {
            skinPart = context.getSkinRegistry().getSkinPartTypeFromId(input.readByte());
            if (skinPart == null) {
                ModLogger.log(Level.ERROR, "Skin part was null");
                throw new IOException("Skin part was null");
            }
        } else {
            String regName = input.readUTF();
            if (regName.equals("armourers:skirt.base"))
                regName = "armourers:legs.skirt";
            if (regName.equals("armourers:bow.base"))
                regName = "armourers:bow.frame1";
            skinPart = context.getSkinRegistry().getSkinPartTypeFromName(regName);

            if (skinPart == null) {
                ModLogger.log(Level.ERROR, "Skin part was null - reg name: " + regName);
                throw new IOException("Skin part was null - reg name: " + regName);
            }
        }

        SkinCubeData cubeData = new SkinCubeData();
        cubeData.readFromStream(input, context.getFileVersion(), skinPart);
        List<CubeMarkerData> markerBlocks = new ArrayList<CubeMarkerData>();
        if (context.getFileVersion() > 8) {
            int markerCount = input.readInt();
            for (int i = 0; i < markerCount; i++)
                markerBlocks.add(readCubeMaker(input));
        }
        return new SkinPart(cubeData, skinPart, markerBlocks);
    }

    public CubeMarkerData readCubeMaker(DataInput input) throws IOException {
        return new CubeMarkerData(input.readByte(),
                input.readByte(),
                input.readByte(),
                input.readByte());
    }

    public void writeCubeMaker(DataOutput output, CubeMarkerData markerData) throws IOException {
        output.writeByte(markerData.x);
        output.writeByte(markerData.y);
        output.writeByte(markerData.z);
        output.writeByte(markerData.meta);
    }
}
