package net.cijhn;

import com.google.common.cache.Cache;
import riskyken.armourersWorkshop.api.common.skin.data.ISkin;
import riskyken.armourersWorkshop.client.render.bake.SkinBakery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ci010
 */
public class SkinStorageImpl implements SkinRepository {
//    private Cache<Object, SkinInfo> skinCache;

    private Map<Object, ISkin> skinHashMap;
    private SkinBakery skinBakery;

    public SkinStorageImpl(SkinBakery skinBakery) {
        this.skinBakery = skinBakery;
        this.skinHashMap = new HashMap<Object, ISkin>();
    }

    @Override
    public boolean registerSkin(Object key, ISkin skin) {
        if (skinHashMap.containsKey(key))
            return false;
        skinHashMap.put(key, skin);
        skinBakery.bake(skin);
        return true;
    }

    @Override
    public Collection<ISkin> getAllStorage() {
        return skinHashMap.values();
    }

    @Override
    public ISkin getSkin(Object key) {
        return skinHashMap.get(key);
    }
}
