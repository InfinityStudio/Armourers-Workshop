package net.skin43d.impl;

import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.entity.Entity;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.skin3d.SkinType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.net.URL;

/**
 * @author ci010
 */
public abstract class SkinProviderDirect extends SkinProviderLocalCached {
    public SkinProviderDirect(SkinBakery bakery, ListeningExecutorService service) {
        super(bakery, service);
    }

    @Nonnull
    @Override
    protected File getCachedLocation(Entity entity, SkinType skinType) {
        return null;
    }

    @Nullable
    @Override
    protected URL getRemoteSkinLocation(Entity entity, SkinType type) {
        return null;
    }
}
