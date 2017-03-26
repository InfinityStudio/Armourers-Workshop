package riskyken.armourersWorkshop.client.render.bake;

import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ci010
 */
public class AsyncModelBakery implements SkinBakery {
    private ExecutorService service;

    public AsyncModelBakery(int threads) {
        if (threads <= 0)
            service = Executors.newCachedThreadPool();
        else
            service = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void bake(Skin3D skin) {
        service.submit(new BakeSkinTask((Skin) skin));
    }
}
