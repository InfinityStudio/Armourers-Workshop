package net.skin43d.impl.type.chest;

import java.awt.Point;

import net.skin43d.skin3d.SkinType;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import net.skin43d.skin3d.ISkinPartTypeTextured;
import net.skin43d.impl.client.model.ModelChest;
import net.skin43d.impl.type.AbstractSkinPartTypeBase;

public class SkinChestPartLeftArm extends AbstractSkinPartTypeBase implements ISkinPartTypeTextured {
    
    public SkinChestPartLeftArm(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-11, -16, -14, 14, 32, 28);
        this.guideSpace = new Rectangle3D(-3, -10, -2, 4, 12, 4);
        this.offset = new Point3D(10, -7, 0);
    }
    
    @Override
    public String getPartName() {
        return "leftArm";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
        GL11.glTranslated(0, this.buildingSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.guideSpace.getY() * scale, 0);
        ModelChest.MODEL.renderLeftArm(scale);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }

    @Override
    public Point getTextureLocation() {
        return new Point(40, 16);
    }

    @Override
    public boolean isTextureMirrored() {
        return true;
    }

    @Override
    public Point3D getTextureModelSize() {
        return new Point3D(4, 12, 4);
    }
}
