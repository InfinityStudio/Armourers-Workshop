package net.skin43d.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.skin43d.SkinProvider;
import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.skin.Skin;

import java.util.Map;

/**
 * @author ci010
 */
public class SkinProviderLocal implements SkinProvider {
    private ImmutableMap<String, Skin> skins;

    public SkinProviderLocal(Skin... skin) {
        Map<String, Skin> map = Maps.newTreeMap();
        for (Skin s : skin)
            map.put(s.getSkinType().getRegistryName(), s);
        skins = ImmutableMap.copyOf(map);
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
