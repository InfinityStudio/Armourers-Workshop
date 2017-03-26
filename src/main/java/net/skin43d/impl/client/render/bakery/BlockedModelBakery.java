package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.client.render.bake.LegacyBakeSkinTask;
import riskyken.armourersWorkshop.common.skin.data.Skin;

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
