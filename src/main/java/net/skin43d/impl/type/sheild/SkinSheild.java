package net.skin43d.impl.type.sheild;

import net.skin43d.impl.type.AbstractSkinTypeBase;
import net.skin43d.skin3d.SkinPartType;

import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public class SkinSheild extends AbstractSkinTypeBase {
    private List<SkinPartType> partTypes;

    public SkinSheild() {
        partTypes = Collections.<SkinPartType>singletonList(new SkinShieldPartBase(this));
    }

    @Override
    public List<SkinPartType> getSkinParts() {
        return partTypes;
    }

    @Override
    public String getRegistryName() {
        return "armourers:shield";
    }

    @Override
    public String getName() {
        return "Shield";
    }
}
