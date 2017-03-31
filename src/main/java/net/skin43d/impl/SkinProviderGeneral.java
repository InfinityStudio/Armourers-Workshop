package net.skin43d.impl;

import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.entity.Entity;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.impl.skin.Skin;
import net.skin43d.skin3d.SkinType;

import java.util.concurrent.Callable;

/**
 *
 * @author ci010
 */
public class SkinProviderGeneral extends AbstractSkinProvider {
    public SkinProviderGeneral(SkinBakery bakery, ListeningExecutorService service) {
        super(bakery, service);
    }

    @Override
    protected Callable<Skin> requestSkinTask(Entity entity, SkinType skinType) {
        return null;
    }
}
