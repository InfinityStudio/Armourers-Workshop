package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.client.render.bake.LegacyBakeSkinTask;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class AsyncModelBakery implements SkinBakery {
    private ListeningExecutorService service;

    public AsyncModelBakery(ListeningExecutorService  service) {
        this.service = service;
    }

    @Override
    public ListenableFuture<Skin> bake(Skin3D skin) {
       return service.submit(new LegacyBakeSkinTask((Skin) skin));
    }
}
