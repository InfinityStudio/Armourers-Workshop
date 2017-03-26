package riskyken.armourersWorkshop.common.skin.type.block;

import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.Point3D;
import riskyken.armourersWorkshop.api.common.skin.Rectangle3D;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinPartTypeBase;

public class SkinBlockPartBase extends AbstractSkinPartTypeBase {

    public SkinBlockPartBase(SkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-8, -8, -8, 16, 16, 16);
        this.guideSpace = new Rectangle3D(0, 0, 0, 0, 0, 0);
        this.offset = new Point3D(0, -1, 0);
    }

    @Override
    public String getPartName() {
        return "base";
    }

    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
    }
    
    @Override
    public int getMaximumMarkersNeeded() {
        return 1;
    }
    
    @Override
    public int getMinimumMarkersNeeded() {
        return 0;
    }
}
