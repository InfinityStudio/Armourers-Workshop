package net.skin43d.impl.type.legs;

import java.awt.Point;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import net.skin43d.skin3d.ISkinPartTypeTextured;
import net.skin43d.skin3d.SkinType;
import net.skin43d.impl.client.model.ModelLegs;
import net.skin43d.impl.type.AbstractSkinPartTypeBase;

public class SkinLegsPartRightLeg extends AbstractSkinPartTypeBase implements ISkinPartTypeTextured {
    
    public SkinLegsPartRightLeg(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-3, -8, -8, 11, 9, 16);
        this.guideSpace = new Rectangle3D(-2, -12, -2, 4, 12, 4);
        this.offset = new Point3D(-6, -5, 0);
    }
    
    @Override
    public String getPartName() {
        return "rightLeg";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
        GL11.glTranslated(0, this.buildingSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.guideSpace.getY() * scale, 0);
        ModelLegs.MODEL.renderRightLeg(scale);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }
    
    @Override
    public Point getTextureLocation() {
        return new Point(0, 16);
    }

    @Override
    public boolean isTextureMirrored() {
        return false;
    }

    @Override
    public Point3D getTextureModelSize() {
        return new Point3D(4, 12, 4);
    }
}
