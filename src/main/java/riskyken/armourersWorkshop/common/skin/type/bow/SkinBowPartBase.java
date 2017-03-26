package riskyken.armourersWorkshop.common.skin.type.bow;

import net.skin43d.skin3d.SkinType;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import riskyken.armourersWorkshop.client.render.core.armourer.ModelHand;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinPartTypeBase;

public class SkinBowPartBase extends AbstractSkinPartTypeBase {
    
    public SkinBowPartBase(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-10, -20, -46, 20, 62, 64);
        this.guideSpace = new Rectangle3D(-2, -2, 2, 4, 4, 8);
        this.offset = new Point3D(-21, 0, 0);
    }

    @Override
    public String getPartName() {
        return "frame1";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
        GL11.glTranslated(0, this.buildingSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.guideSpace.getY() * scale, 0);
        ModelHand.MODEL.render(scale);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }
    
    @Override
    public int getMinimumMarkersNeeded() {
        return 1;
    }
    
    @Override
    public int getMaximumMarkersNeeded() {
        return 1;
    }
    
    @Override
    public boolean isPartRequired() {
        return true;
    }
}
