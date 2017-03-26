package net.skin43d;

import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.client.render.bake.SkinBakery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ci010
 */
public class SkinStorageImpl implements SkinRepository {
//    private Cache<Object, SkinInfo> skinCache;

    private Map<Object, Skin3D> skinHashMap;
    private SkinBakery skinBakery;

    public SkinStorageImpl(SkinBakery skinBakery) {
        this.skinBakery = skinBakery;
        this.skinHashMap = new HashMap<Object, Skin3D>();
    }

    @Override
    public boolean registerSkin(Object key, Skin3D skin) {
        if (skinHashMap.containsKey(key))
            return false;
        skinHashMap.put(key, skin);
        skinBakery.bake(skin);
        return true;
    }

    @Override
    public Collection<Skin3D> getAllStorage() {
        return skinHashMap.values();
    }

    @Override
    public Skin3D getSkin(Object key) {
        return skinHashMap.get(key);
    }
}
