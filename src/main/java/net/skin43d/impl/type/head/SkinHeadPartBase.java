package net.skin43d.impl.type.head;

import java.awt.Point;

import org.lwjgl.opengl.GL11;

import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import net.skin43d.skin3d.ISkinPartTypeTextured;
import net.skin43d.skin3d.SkinType;
import net.skin43d.impl.client.model.ModelHead;
import net.skin43d.impl.type.AbstractSkinPartTypeBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkinHeadPartBase extends AbstractSkinPartTypeBase implements ISkinPartTypeTextured {
    
    public SkinHeadPartBase(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-16, -12, -16, 32, 32, 32);
        this.guideSpace = new Rectangle3D(-4, 0, -4, 8, 8, 8);
        this.offset = new Point3D(0, 0, 0);
    }
    
    @Override
    public String getPartName() {
        return "base";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
        GL11.glTranslated(0, this.buildingSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.guideSpace.getY() * scale, 0);
        ModelHead.MODEL.render(scale, showSkinOverlay);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }

    @Override
    public Point getTextureLocation() {
        return new Point(0, 0);
    }

    @Override
    public boolean isTextureMirrored() {
        return false;
    }
    
    @Override
    public Point3D getTextureModelSize() {
        return new Point3D(8, 8, 8);
    }
}
