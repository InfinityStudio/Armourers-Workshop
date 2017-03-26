package net.skin43d.impl.client.render.nbake;

import com.google.common.collect.Maps;
import net.skin43d.impl.client.render.BakedFace;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.client.render.core.BakedCubes;
import riskyken.armourersWorkshop.common.skin.data.SkinDye;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class BakeSkinPart {
    public static final SkinDye blankDye = new SkinDye();
    private int[] averageR = new int[10];
    private int[] averageG = new int[10];
    private int[] averageB = new int[10];

    public BakeSkinPart(int[] averageR, int[] averageG, int[] averageB, List<BakedFace>[] colouredFaces, int[] totalCubesInPart) {
        this.averageR = averageR;
        this.averageG = averageG;
        this.averageB = averageB;
        this.colouredFaces = colouredFaces;
        this.totalCubesInPart = totalCubesInPart;
        this.dyeModels = Maps.newHashMap();
    }

    public BakeSkinPart(List<BakedFace>[] colouredFaces, int[] totalCubesInPart) {
        this.colouredFaces = colouredFaces;
        this.totalCubesInPart = totalCubesInPart;
    }

    private List<BakedFace>[] colouredFaces;
    private Map<ModelKey, BakedCubes> dyeModels;
    private int[] totalCubesInPart;

    public List<BakedFace>[] getColouredFaces() {
        return colouredFaces;
    }

    public Map<ModelKey, BakedCubes> getDyeModels() {
        return dyeModels;
    }

    public int[] getTotalCubesInPart() {
        return totalCubesInPart;
    }

    public BakedCubes getModelForDye(ISkinDye skinDye, byte[] extraColour) {
        if (skinDye == null)
            skinDye = blankDye;
        ModelKey modelKey = new ModelKey(skinDye, extraColour);
        BakedCubes skinModel = dyeModels.get(modelKey);
        if (skinModel == null) {
            skinModel = new BakedCubes(colouredFaces);
            dyeModels.put(modelKey, skinModel);
        }
        return skinModel;
    }

    public int[] getAverageDyeColour(int dyeNumber) {
        return new int[]{averageR[dyeNumber], averageG[dyeNumber], averageB[dyeNumber]};
    }

    private class ModelKey {
        private ISkinDye skinDye;
        byte[] extraColours;

        public ModelKey(ISkinDye skinDye, byte[] extraColours) {
            this.skinDye = skinDye;
            this.extraColours = extraColours;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(extraColours);
            result = prime * result
                    + ((skinDye == null) ? 0 : skinDye.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ModelKey other = (ModelKey) obj;
            if (!Arrays.equals(extraColours, other.extraColours))
                return false;
            if (skinDye == null) {
                if (other.skinDye != null)
                    return false;
            } else if (!skinDye.equals(other.skinDye))
                return false;
            return true;
        }
    }

}
