package net.skin43d.impl.skin.serialize;

import net.skin43d.impl.Skin43D;
import org.apache.logging.log4j.Level;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.impl.cubes.CubeMarkerData;
import net.skin43d.impl.skin.SkinPart;
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

    public SkinPart readSkinPart(DataInput input, Skin43D skin43D, int fileVersion) throws IOException, InvalidCubeTypeException {
        SkinPartType skinPart;
        if (fileVersion < 6) {
            skinPart = skin43D.getSkinRegistry().getSkinPartTypeFromId(input.readByte());
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
            skinPart = skin43D.getSkinRegistry().getSkinPartTypeFromName(regName);

            if (skinPart == null) {
                ModLogger.log(Level.ERROR, "Skin part was null - reg name: " + regName);
                throw new IOException("Skin part was null - reg name: " + regName);
            }
        }

        SkinPart.Data cubeData = new SkinPart.Data();
        cubeData.readFromStream(input, fileVersion, skinPart);
        List<CubeMarkerData> markerBlocks = new ArrayList<CubeMarkerData>();
        if (fileVersion > 8) {
            int markerCount = input.readInt();
            for (int i = 0; i < markerCount; i++)
                markerBlocks.add(readCubeMaker(input));
        }
        return new SkinPart(cubeData, skinPart, markerBlocks);
    }

    public CubeMarkerData readCubeMaker(DataInput input) throws IOException {
        return new CubeMarkerData(input.readByte(), input.readByte(), input.readByte(), input.readByte());
    }

    public void writeCubeMaker(DataOutput output, CubeMarkerData markerData) throws IOException {
        output.writeByte(markerData.x);
        output.writeByte(markerData.y);
        output.writeByte(markerData.z);
        output.writeByte(markerData.meta);
    }
}
