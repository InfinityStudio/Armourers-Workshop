package net.skin43d.impl.client;

import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import net.skin43d.impl.client.render.BakedFace;
import riskyken.armourersWorkshop.client.render.core.SkinModel;
import riskyken.armourersWorkshop.client.skin.SkinModelTexture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author ci010
 */
public class BakedSkinModel {
    private SkinModelTexture texture;
    private List<BakedFace> colouredFaces;

    public HashMap<ModelKey, SkinModel> dyeModels;
    public int[] totalCubesInPart;

    private int[] averageR = new int[10];
    private int[] averageG = new int[10];
    private int[] averageB = new int[10];



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
