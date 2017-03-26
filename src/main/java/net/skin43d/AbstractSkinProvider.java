package net.skin43d;

import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;


/**
 * @author ci010
 */
public abstract class AbstractSkinProvider implements SkinInfoProvider {
    private SkinRepository skinStorage;

    public AbstractSkinProvider(SkinRepository skinStorage) {
        this.skinStorage = skinStorage;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }
}
