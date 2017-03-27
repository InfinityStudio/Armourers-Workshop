package net.skin43d.impl;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.skin3d.SkinType;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author ci010
 */
public abstract class SkinProviderNBT extends SkinProviderLocalCached {
    private String cachedDirName;

    public SkinProviderNBT(SkinBakery bakery, ListeningExecutorService service, @Nonnull String cachedDirName) {
        super(bakery, service);
        Preconditions.checkNotNull(cachedDirName);
        this.cachedDirName = cachedDirName;
    }

    @Nonnull
    @Override
    protected File getCachedLocation(Entity entity, SkinType skinType) {
        final String loc = entity.getEntityData().getString(skinType.getRegistryName());
        File dir = new File(Minecraft.getMinecraft().mcDataDir, cachedDirName);
        if (!dir.exists()) dir.mkdir();
        return new File(dir, loc);
    }
}
