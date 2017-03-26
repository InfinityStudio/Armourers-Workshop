package net.skin43d;

import riskyken.armourersWorkshop.api.common.skin.data.ISkin;

import java.util.Collection;

/**
 * @author ci010
 */
public interface SkinRepository {
    boolean registerSkin(Object key, ISkin skin);

    Collection<ISkin> getAllStorage();

    ISkin getSkin(Object key);
}
