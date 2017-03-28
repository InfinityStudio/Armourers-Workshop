package net.skin43d.impl.skin.serialize;

import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.exception.NewerFileVersionException;
import net.skin43d.impl.Skin43D;
import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.SkinTypeRegistry;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;
import net.skin43d.impl.skin.SkinProperties;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.skin43d.impl.skin.Skin.*;

/**
 * @author ci010
 */
public class SkinReader {
    private SkinPartReader partSerializer = new SkinPartReader();

    public Skin readSkin(DataInput stream, Skin43D skin43D, boolean isShield) throws IOException, NewerFileVersionException, InvalidCubeTypeException {
        int fileVersion = stream.readInt();
        if (fileVersion > skin43D.getContext().getFileVersion())
            throw new NewerFileVersionException();
        SkinTypeRegistry skinRegistry = skin43D.getSkinRegistry();
        SkinProperties properties = new SkinProperties();
        SkinType skinType;
        List<SkinPart> parts;
        int[] paintData = null;
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
            if (regName.equals(skinRegistry.getSkinSkirt().getRegistryName()))
                regName = skinRegistry.getSkinLegs().getRegistryName();
            skinType = skinRegistry.getSkinTypeFromRegistryName(regName);
        }

        if (skinType == null) throw new InvalidCubeTypeException();

        if (skinType == skinRegistry.getSkinSword() && isShield)
            skinType = skinRegistry.getSkinShield();

        if (fileVersion > 7)
            if (stream.readBoolean()) {
                paintData = new int[skin43D.getContext().getTextureSize()];
                for (int i = 0; i < skin43D.getContext().getTextureSize(); i++)
                    paintData[i] = stream.readInt();
            }

        int size = stream.readByte();
        parts = new ArrayList<SkinPart>();
        for (int i = 0; i < size; i++)
            parts.add(partSerializer.readSkinPart(stream, skin43D, fileVersion));

        return new Skin(properties, skinType, paintData, parts);
    }

    public Skin readSkin(DataInput stream, Skin43D skin43D) throws IOException, NewerFileVersionException, InvalidCubeTypeException {
        return readSkin(stream, skin43D, false);
    }

    public void writeSkin(Skin skin, DataOutputStream stream, Skin43D skin43D) throws IOException {
        stream.writeInt(skin43D.getContext().getFileVersion());
        skin.getProperties().writeToStream(stream);
        stream.writeUTF(skin.getSkinType().getRegistryName());
        if (skin.getPaintData() != null) {
            stream.writeBoolean(true);
            for (int i = 0; i < skin43D.getContext().getTextureSize(); i++)
                stream.writeInt(skin.getPaintData()[i]);
        } else
            stream.writeBoolean(false);
        List<SkinPart> parts = skin.getParts();
        stream.writeByte(skin.getParts().size());
        for (int i = 0; i < parts.size(); i++)
            partSerializer.writeToStream(parts.get(i), stream);
    }
}
