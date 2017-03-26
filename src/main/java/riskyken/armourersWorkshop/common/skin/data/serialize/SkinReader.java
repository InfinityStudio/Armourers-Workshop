package riskyken.armourersWorkshop.common.skin.data.serialize;

import riskyken.armourersWorkshop.ArmourersWorkshop;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;
import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.exception.NewerFileVersionException;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;
import riskyken.armourersWorkshop.common.skin.data.SkinProperties;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static riskyken.armourersWorkshop.common.skin.data.Skin.*;

/**
 * @author ci010
 */
public class SkinReader {
    private SkinPartReader partSerializer;

    public Skin readSkin(DataInput stream, ArmourersWorkshop context) throws IOException, NewerFileVersionException, InvalidCubeTypeException {
        int fileVersion = stream.readInt();
        if (fileVersion > context.getFileVersion())
            throw new NewerFileVersionException();
        ISkinTypeRegistry skinRegistry = context.getSkinRegistry();
        SkinProperties properties = new SkinProperties();
        SkinType skinType;
        List<SkinPart> parts;
        int[] paintData = new int[0];
        if (fileVersion < 12) {
            String authorName = stream.readUTF();
            String customName = stream.readUTF();
            String tags = "";
            if (!(fileVersion < 4))
                tags = stream.readUTF();

            properties.setProperty(KEY_AUTHOR_NAME, authorName);
            properties.setProperty(KEY_CUSTOM_NAME, customName);
            if (!tags.equalsIgnoreCase(""))
                properties.setProperty(KEY_TAGS, tags);
        } else
            properties.readFromStream(stream);

        if (fileVersion < 5) {
            skinType = skinRegistry.getSkinTypeFromLegacyId(stream.readByte() - 1);
        } else {
            String regName = stream.readUTF();
            if (regName.equals(SkinTypeRegistry.skinSkirt.getRegistryName()))
                regName = SkinTypeRegistry.skinLegs.getRegistryName();
            skinType = skinRegistry.getSkinTypeFromRegistryName(regName);
        }

        if (skinType == null) throw new InvalidCubeTypeException();

        if (fileVersion > 7)
            if (stream.readBoolean()) {
                paintData = new int[context.getTextureSize()];
                for (int i = 0; i < context.getTextureSize(); i++)
                    paintData[i] = stream.readInt();
            }

        int size = stream.readByte();
        parts = new ArrayList<SkinPart>();
        for (int i = 0; i < size; i++)
            parts.add(partSerializer.readSkinPart(stream, context));

        return new Skin(properties, skinType, paintData, parts);
    }

    public void writeSkin(Skin skin, DataOutputStream stream, ArmourersWorkshop context) throws IOException {
        stream.writeInt(context.getFileVersion());
        skin.getProperties().writeToStream(stream);
        stream.writeUTF(skin.getSkinType().getRegistryName());
        if (skin.getPaintData() != null) {
            stream.writeBoolean(true);
            for (int i = 0; i < context.getTextureSize(); i++)
                stream.writeInt(skin.getPaintData()[i]);
        } else
            stream.writeBoolean(false);
        List<SkinPart> parts = skin.getParts();
        stream.writeByte(skin.getParts().size());
        for (int i = 0; i < parts.size(); i++)
            partSerializer.writeToStream(parts.get(i), stream);
    }

}
