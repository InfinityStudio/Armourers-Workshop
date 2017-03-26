package riskyken.armourersWorkshop.client.render.bake;

import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.util.concurrent.ExecutorService;

/**
 * @author ci010
 */
public class AsyncModelBakery implements SkinBakery {
    private ExecutorService service;

    public AsyncModelBakery(ExecutorService service) {
        this.service = service;
    }
    @Override
    public void bake(Skin3D skin) {
        service.submit(new LegacyBakeSkinTask((Skin) skin));
    }
}
