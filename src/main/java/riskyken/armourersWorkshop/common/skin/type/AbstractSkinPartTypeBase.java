package riskyken.armourersWorkshop.common.skin.type;

import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.IRectangle3D;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;

public abstract class AbstractSkinPartTypeBase implements SkinPartType {

    private SkinType baseType;
    protected IRectangle3D buildingSpace;
    protected IRectangle3D guideSpace;
    protected Point3D offset;
    
    public AbstractSkinPartTypeBase(SkinType baseType) {
        this.baseType = baseType;
    }
    
    @Override
    public IRectangle3D getBuildingSpace() {
        return this.buildingSpace;
    }

    @Override
    public IRectangle3D getGuideSpace() {
        return this.guideSpace;
    }

    @Override
    public Point3D getOffset() {
        return this.offset;
    }
    
    @Override
    public String getRegistryName() {
        return baseType.getRegistryName() + "." + getPartName();
    }
    
    @Override
    public int getMinimumMarkersNeeded() {
        return 0;
    }
    
    @Override
    public int getMaximumMarkersNeeded() {
        return 0;
    }
    
    @Override
    public boolean isPartRequired() {
        return false;
    }
}
