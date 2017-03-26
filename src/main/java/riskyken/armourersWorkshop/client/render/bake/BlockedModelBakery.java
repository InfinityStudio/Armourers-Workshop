package riskyken.armourersWorkshop.client.render.bake;

import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class BlockedModelBakery implements SkinBakery {
    @Override
    public void bake(Skin3D skin) {
        try {
            new LegacyBakeSkinTask((Skin) skin).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
