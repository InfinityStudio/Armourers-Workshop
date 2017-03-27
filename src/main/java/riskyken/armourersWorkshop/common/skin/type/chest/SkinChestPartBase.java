package riskyken.armourersWorkshop.common.skin.type.chest;

import java.awt.Point;

import net.skin43d.skin3d.SkinType;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinPartTypeTextured;
import riskyken.armourersWorkshop.client.render.core.armourer.ModelChest;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinPartTypeBase;

public class SkinChestPartBase extends AbstractSkinPartTypeBase implements ISkinPartTypeTextured {
    
    public SkinChestPartBase(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-6, -24, -14, 12, 35, 28);
        this.guideSpace = new Rectangle3D(-4, -12, -2, 8, 12, 4);
        this.offset = new Point3D(0, -1, 0);
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
        ModelChest.MODEL.renderChest(scale);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }

    @Override
    public Point getTextureLocation() {
        return new Point(16, 16);
    }

    @Override
    public boolean isTextureMirrored() {
        return false;
    }

    @Override
    public Point3D getTextureModelSize() {
        return new Point3D(8, 12, 4);
    }
}
