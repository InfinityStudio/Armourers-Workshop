package net.skin43d.impl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.render.nbake.BakeSkinPart;
import net.skin43d.impl.client.render.nbake.BakedSkin;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class SkinProviderLocal implements SkinProvider {
    private ImmutableMap<String, Skin> skins;

    public SkinProviderLocal(Skin... skin) {
        ImmutableMap.Builder<String, Skin> builder = ImmutableMap.builder();
        for (Skin s : skin)
            builder.put(s.getSkinType().getRegistryName(), s);
        skins = builder.build();
    }

    @Override
    public BakedSkin getBakedModel(Entity entity, SkinType type) {
        return null;
    }

    @Override
    public BakeSkinPart getBakedModel(Entity entity, SkinType type, SkinPartType partType) {
        return null;
    }

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType) {
        return skins.get(skinType.getRegistryName());
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, SkinType skinType) {
        return null;
    }
}
