package net.skin43d;

import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class SkinProviderDirect implements SkinInfoProvider {
    private SkinRepository repository;

    @Override
    public void deploy() {

    }

    @Override
    public SkinInfo getSkin(Entity entity) {
        return null;
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }
}
