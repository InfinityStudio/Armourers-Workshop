package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import net.skin43d.skin3d.Skin3D;
import net.skin43d.impl.skin.Skin;

/**
 * @author ci010
 */
public interface SkinBakery {
    ListenableFuture<Skin> bake(Skin3D skin);
}
