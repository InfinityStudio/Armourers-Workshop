package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.skin43d.skin3d.Skin3D;
import net.skin43d.impl.skin.Skin;

/**
 * @author ci010
 */
public class BlockedModelBakery implements SkinBakery {
    @Override
    public ListenableFuture<Skin> bake(Skin3D skin) {
        try {
            Skin call = new LegacyBakeSkinTask((Skin) skin).call();
            return Futures.immediateFuture(call);
        } catch (Exception e) {
            return Futures.immediateFailedFuture(e);
        }
    }
}
