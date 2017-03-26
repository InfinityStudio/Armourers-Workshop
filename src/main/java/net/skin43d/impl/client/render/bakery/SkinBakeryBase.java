package net.skin43d.impl.client.render.bakery;

import net.skin43d.impl.client.render.BakeSkinPart;
import net.skin43d.impl.client.render.BakedSkin;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;

import java.util.Map;

/**
 * @author ci010
 */
public abstract class SkinBakeryBase implements SkinBakery {
    private Map<Skin3D, BakedSkin> cached;

    @Override
    public BakeSkinPart getSkinPart(Skin3D.Part part) {
        return null;
    }

    @Override
    public BakedSkin getBakedModel(Skin3D skin3D) {
        return null;
    }

    protected void cache(Skin3D skin3D, BakedSkin call) {

    }
}
