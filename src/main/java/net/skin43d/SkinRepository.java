package net.skin43d;

import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;

import java.util.Collection;

/**
 * @author ci010
 */
public interface SkinRepository {
    boolean registerSkin(Object key, Skin3D skin);

    Collection<Skin3D> getAllStorage();

    Skin3D getSkin(Object key);
}
