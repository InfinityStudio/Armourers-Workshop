package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import net.skin43d.impl.client.render.BakeSkinPart;
import net.skin43d.impl.client.render.BakedSkin;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;

/**
 * @author ci010
 */
public interface SkinBakery {
    BakeSkinPart getSkinPart(Skin3D.Part part);

    BakedSkin getBakedModel(Skin3D skin3D);

    ListenableFuture<BakedSkin> bake(Skin3D skin);
}
