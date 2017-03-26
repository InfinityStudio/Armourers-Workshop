package net.skin43d.impl;

import net.minecraft.entity.Entity;
import net.skin43d.SkinInfo;
import net.skin43d.SkinInfoProvider;
import net.skin43d.SkinRepository;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class SkinProviderDirect implements SkinInfoProvider {
    private SkinRepository repository;

    @Override
    public SkinInfo getSkinInfoForEntity(Entity entity) {
        return null;
    }

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType) {
        return null;
    }

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType, int slotIndex) {
        return null;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, SkinType skinType, int slotIndex) {
        return null;
    }
}
