package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public interface SkinBakery {
    ListenableFuture<Skin> bake(Skin3D skin);
}
