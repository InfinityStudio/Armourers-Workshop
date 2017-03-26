package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import net.skin43d.impl.client.render.BakeSkinPart;
import net.skin43d.impl.client.render.BakeTask;
import net.skin43d.impl.client.render.BakedSkin;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
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
    public BakeSkinPart getSkinPart(Skin3D.Part part) {
        return null;
    }

    @Override
    public BakedSkin getBakedModel(Skin3D skin3D) {
        return null;
    }

    @Override
    public ListenableFuture<BakedSkin> bake(Skin3D skin) {
       return service.submit(new BakeTask((Skin) skin));
    }
}
