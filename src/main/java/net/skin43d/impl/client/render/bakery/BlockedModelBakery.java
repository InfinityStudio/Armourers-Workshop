package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.skin43d.impl.client.render.BakeTask;
import net.skin43d.impl.client.render.BakedSkin;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class BlockedModelBakery extends SkinBakeryBase implements SkinBakery {
    @Override
    public ListenableFuture<BakedSkin> bake(Skin3D skin) {
        try {
            BakedSkin call = new BakeTask((Skin) skin).call();
            this.cache(skin, call);
            return Futures.immediateFuture(call);
        } catch (Exception e) {
            return Futures.immediateFailedFuture(e);
        }
    }
}
