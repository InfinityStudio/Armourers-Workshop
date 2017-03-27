package net.skin43d.impl.client.render.nbake;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import net.skin43d.impl.client.SkinModelTexture;

/**
 * @author ci010
 */
public class BakedSkin {
    private SkinModelTexture texture;

    private int[] averageR = new int[10];
    private int[] averageG = new int[10];
    private int[] averageB = new int[10];

    private SkinType type;
    private BakeSkinPart[] skinParts;

    public BakedSkin(SkinModelTexture texture, int[] averageR, int[] averageG, int[] averageB, BakeSkinPart[] skinParts) {
        this.texture = texture;
        this.averageR = averageR;
        this.averageG = averageG;
        this.averageB = averageB;
        this.skinParts = skinParts;
    }

    public BakeSkinPart getPart(SkinPartType skinPart) {
        return skinParts[type.getSkinParts().indexOf(skinPart)];
    }

    public SkinModelTexture getTexture() {
        return texture;
    }

    public int[] getAverageR() {
        return averageR;
    }

    public int[] getAverageG() {
        return averageG;
    }

    public int[] getAverageB() {
        return averageB;
    }

    public BakeSkinPart[] getSkinParts() {
        return skinParts;
    }
}
