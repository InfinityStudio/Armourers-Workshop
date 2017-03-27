package net.skin43d.impl.type.sheild;

import net.skin43d.impl.type.AbstractSkinPartTypeBase;
import net.skin43d.skin3d.SkinType;

/**
 * @author ci010
 */
public class SkinShieldPartBase extends AbstractSkinPartTypeBase {
    public SkinShieldPartBase(SkinType baseType) {
        super(baseType);
    }

    @Override
    public String getPartName() {
        return "base";
    }

    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
    }
}
