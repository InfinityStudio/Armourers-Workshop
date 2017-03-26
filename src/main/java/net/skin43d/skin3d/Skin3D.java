package net.skin43d.skin3d;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.skin43d.impl.Context;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPart;
import riskyken.armourersWorkshop.client.skin.SkinModelTexture;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.cubes.ICube;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class Skin3D {
    private Map<String, Object> properties;
    private SkinType equipmentSkinType;
    private List<SkinPart> parts;
    private int[] paintData;

    @SideOnly(Side.CLIENT)
    private transient BakedData bakedData;

    @SideOnly(Side.CLIENT)
    public class BakedData {
        public final SkinModelTexture skinModelTexture;
        private int[] averageR = new int[10];
        private int[] averageG = new int[10];
        private int[] averageB = new int[10];

        public BakedData(SkinModelTexture skinModelTexture, int[] averageR, int[] averageG, int[] averageB) {
            this.skinModelTexture = skinModelTexture;
            this.averageR = averageR;
            this.averageG = averageG;
            this.averageB = averageB;
        }

        public int[] getAverageDyeColour(int dyeNumber) {
            return new int[]{averageR[dyeNumber], averageG[dyeNumber], averageB[dyeNumber]};
        }

    public Skin3D(Map<String, Object> properties, SkinType equipmentSkinType, int[] paintData, List<SkinPart> equipmentSkinParts) {
        this.properties = properties;
        this.equipmentSkinType = equipmentSkinType;
        this.paintData = null;
        //Check if the paint data has any paint on it.
        if (paintData != null) {
            boolean validPaintData = false;
            for (int i = 0; i < Context.instance().getTextureSize(); i++)
                if (paintData[i] >>> 16 != 255) {
                    validPaintData = true;
                    break;
                }
            if (validPaintData) this.paintData = paintData;
        }
        this.parts = equipmentSkinParts;
    }


    public int getPartCount() {
        return parts.size();
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
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getPartType() == skinPartType) {
                return parts.get(i);
            }
        }
        return null;
    }

    public List<ISkinPart> getSubParts() {
        ArrayList<ISkinPart> partList = new ArrayList<ISkinPart>();
        for (int i = 0; i < parts.size(); i++)
            partList.add(parts.get(i));
        return partList;
    }
//
//    public String getCustomName() {
//        return properties.getPropertyString(KEY_CUSTOM_NAME, "");
//    }
//
//    public String getAuthorName() {
//        return properties.getPropertyString(KEY_AUTHOR_NAME, "");
//    }

    public int getTotalOfCubeType(ICube cube) {
        int totalOfCube = 0;
        int cubeId = cube.getId();
        for (int i = 0; i < parts.size(); i++) {
            totalOfCube += parts.get(i).getClientSkinPartData().totalCubesInPart[cubeId];
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
}
