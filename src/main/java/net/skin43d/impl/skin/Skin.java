package net.skin43d.impl.skin;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.impl.Context;
import net.skin43d.skin3d.SkinType;
import org.lwjgl.opengl.GL11;
import net.skin43d.utils.Rectangle3D;
import net.skin43d.skin3d.Skin3D;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.client.SkinModelTexture;
import net.skin43d.impl.cubes.ICube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skin implements Skin3D {

    public static final int FILE_VERSION = 12;

    public static final String KEY_AUTHOR_NAME = "authorName";
    public static final String KEY_AUTHOR_UUID = "authorUUID";
    public static final String KEY_CUSTOM_NAME = "customName";
    public static final String KEY_TAGS = "tags";

    public static final String KEY_BLOCK_GLOWING = "blockGlowing";
    public static final String KEY_BLOCK_LADDER = "blockLadder";
    public static final String KEY_BLOCK_NO_COLLISION = "blockNoCollision";
    public static final String KEY_BLOCK_SEAT = "blockSeat";

    public static final String KEY_WINGS_MAX_ANGLE = "wingsMaxAngle";
    public static final String KEY_WINGS_MIN_ANGLE = "wingsMinAngle";
    public static final String KEY_WINGS_IDLE_SPEED = "wingsIdleSpeed";
    public static final String KEY_WINGS_FLYING_SPEED = "wingsFlyingSpeed";

    public static final String KEY_ARMOUR_OVERRIDE = "armourOverride";

    public static final String KEY_FILE_NAME = "fileName";

    private SkinProperties properties;
    private SkinType equipmentSkinType;
    private int[] paintData;
    private List<SkinPart> parts;

    private int lightHash = 0;
    @SideOnly(Side.CLIENT)
    public SkinModelTexture skinModelTexture;
    @SideOnly(Side.CLIENT)
    public int paintTextureId;
    private int[] averageR = new int[10];
    private int[] averageG = new int[10];
    private int[] averageB = new int[10];

    public void setAverageDyeValues(int[] r, int[] g, int[] b) {
        this.averageR = r;
        this.averageG = g;
        this.averageB = b;
    }

    @SideOnly(Side.CLIENT)
    public Rectangle3D getSkinBounds() {
        int x = 0;
        int y = 0;
        int z = 0;

        int width = 1;
        int height = 1;
        int depth = 1;

        for (int i = 0; i < getPartCount(); i++) {
            if (!(getSkinType() == Context.instance().getSkinRegistry().getSkinBow() && i > 0)) {

                SkinPart skinPart = getParts().get(i);
                Rectangle3D bounds = skinPart.getPartBounds();

                width = Math.max(width, bounds.getWidth());
                height = Math.max(height, bounds.getHeight());
                depth = Math.max(depth, bounds.getDepth());

                x = bounds.getX();
                y = bounds.getY();
                z = bounds.getZ();

                if (hasPaintData()) {
                    Rectangle3D skinRec = skinPart.getPartType().getGuideSpace();

                    width = Math.max(width, skinRec.getWidth());
                    height = Math.max(height, skinRec.getHeight());
                    depth = Math.max(depth, skinRec.getDepth());

                    x = Math.max(x, skinRec.getX());
                    y = Math.max(y, skinRec.getY());
                    z = Math.max(z, skinRec.getZ());
                }

            }
        }

        if (getPartCount() == 0) {
            for (int i = 0; i < getSkinType().getSkinParts().size(); i++) {
                SkinPartType part = getSkinType().getSkinParts().get(i);

                Rectangle3D skinRec = part.getGuideSpace();

                width = Math.max(width, skinRec.getWidth());
                height = Math.max(height, skinRec.getHeight());
                depth = Math.max(depth, skinRec.getDepth());

                x = Math.min(x, skinRec.getX());
                y = Math.max(y, skinRec.getY());
                z = Math.min(z, skinRec.getZ());
            }
        }

        return new Rectangle3D(x, y, z, width, height, depth);
    }

    public int[] getAverageDyeColour(int dyeNumber) {
        return new int[]{averageR[dyeNumber], averageG[dyeNumber], averageB[dyeNumber]};
    }

    public SkinProperties getProperties() {
        return properties;
    }

    public Skin(SkinProperties properties, SkinType equipmentSkinType, int[] paintData, List<SkinPart> equipmentSkinParts) {
        this.properties = properties;
        this.equipmentSkinType = equipmentSkinType;
        this.paintData = null;
        if (paintData != null) {//Check if the paint data has any paint on it.
            boolean validPaintData = false;
            for (int i = 0; i < Context.instance().getTextureSize(); i++) {
                if (paintData[i] >>> 16 != 255) {
                    validPaintData = true;
                    break;
                }
            }
            if (validPaintData) {
                this.paintData = paintData;
            }
        }

        this.parts = equipmentSkinParts;
    }

    @SideOnly(Side.CLIENT)
    public void cleanUpDisplayLists() {
        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).getBakedPart().cleanUpDisplayLists();
        }
        if (hasPaintData()) {
            skinModelTexture.deleteGlTexture();
        }
    }

    @SideOnly(Side.CLIENT)
    public void blindPaintTexture() {
        if (hasPaintData()) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, paintTextureId);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getModelCount() {
        int count = 0;
        for (int i = 0; i < parts.size(); i++) {
            count += parts.get(i).getModelCount();
        }
        return count;
    }

    public int getPartCount() {
        return parts.size();
    }

    public int lightHash() {
        if (lightHash == 0)
            lightHash = this.hashCode();
        return lightHash;
    }

    @Override
    public SkinType getSkinType() {
        return equipmentSkinType;
    }

    public boolean hasPaintData() {
        return paintData != null;
    }

    public int[] getPaintData() {
        return paintData;
    }

    public List<SkinPart> getParts() {
        return parts;
    }

    public SkinPart getSkinPartFromType(SkinPartType skinPartType) {
        for (int i = 0; i < parts.size(); i++)
            if (parts.get(i).getPartType() == skinPartType)
                return parts.get(i);
        return null;
    }

    @Override
    public List<Part> getSubParts() {
        ArrayList<Part> partList = new ArrayList<Part>();
        for (int i = 0; i < parts.size(); i++)
            partList.add(parts.get(i));
        return partList;
    }

    public String getCustomName() {
        return properties.getPropertyString(KEY_CUSTOM_NAME, "");
    }

    public String getAuthorName() {
        return properties.getPropertyString(KEY_AUTHOR_NAME, "");
    }

    public int getTotalCubes() {
        int totalCubes = 0;
        for (int i = 0; i < Context.instance().getCubeRegistry().getTotalCubes(); i++) {
            ICube cube = Context.instance().getCubeRegistry().getCubeFormId((byte) i);
            totalCubes += getTotalOfCubeType(cube);
        }
        return totalCubes;
    }

    public int getTotalOfCubeType(ICube cube) {
        int totalOfCube = 0;
        int cubeId = cube.getId();
        for (int i = 0; i < parts.size(); i++) {
            totalOfCube += parts.get(i).getBakedPart().totalCubesInPart[cubeId];
        }
        return totalOfCube;
    }

    @Override
    public int hashCode() {
        String result = this.toString();
        for (int i = 0; i < parts.size(); i++) {
            result += parts.get(i).toString();
        }
        return result.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Skin other = (Skin) obj;
        if (properties == null) {
            if (other.properties != null)
                return false;
        } else if (!properties.equals(other.properties))
            return false;
        if (parts == null) {
            if (other.parts != null)
                return false;
        } else if (!parts.equals(other.parts))
            return false;
        if (equipmentSkinType != other.equipmentSkinType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String returnString = "Skin [properties=" + properties + ", type=" + equipmentSkinType.getName().toUpperCase();
        if (this.paintData != null) {
            returnString += ", paintData=" + Arrays.hashCode(paintData);
        }
        returnString += "]";
        return returnString;
    }
}
