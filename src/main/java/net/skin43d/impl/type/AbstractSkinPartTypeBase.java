package net.skin43d.impl.type;

import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;

public abstract class AbstractSkinPartTypeBase implements SkinPartType {

    private SkinType baseType;
    protected Rectangle3D buildingSpace;
    protected Rectangle3D guideSpace;
    protected Point3D offset;

    public AbstractSkinPartTypeBase(SkinType baseType) {
        this.baseType = baseType;
    }

    @Override
    public SkinType getBaseType() {
        return baseType;
    }

    @Override
    public Rectangle3D getBuildingSpace() {
        return this.buildingSpace;
    }

    @Override
    public Rectangle3D getGuideSpace() {
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
