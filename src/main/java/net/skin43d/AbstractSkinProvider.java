package net.skin43d;

import net.minecraft.entity.Entity;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;


/**
 * @author ci010
 */
public abstract class AbstractSkinProvider implements SkinInfoProvider {
    private SkinRepository skinStorage;

    public AbstractSkinProvider(SkinRepository skinStorage) {
        this.skinStorage = skinStorage;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, SkinType skinType, int slotIndex) {
        return null;
    }
}
