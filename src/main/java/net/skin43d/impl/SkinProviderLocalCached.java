package net.skin43d.impl;

import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.entity.Entity;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.SkinIOUtils;
import org.apache.commons.io.FileUtils;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public abstract class SkinProviderLocalCached extends AbstractSkinProvider {
    public SkinProviderLocalCached(SkinBakery bakery, ListeningExecutorService service) {
        super(bakery, service);
    }

    @Override
    protected Callable<Skin> requestSkinTask(final Entity entity, final SkinType skinType) {
        final File file = getCachedLocation(entity, skinType);
        if (file.exists())
            return new Callable<Skin>() {
                @Override
                public Skin call() throws Exception {
                    return SkinIOUtils.loadSkinFromStream(new FileInputStream(file));
                }
            };
        else
            return new Callable<Skin>() {
                @Override
                public Skin call() throws Exception {
                    URL url = getRemoteSkinLocation(entity, skinType);
                    if (url == null) return null;//unknown skin
                    try {
                        FileUtils.copyURLToFile(url, file);
                    } catch (IOException e) {
                        //if there is no such file or internet error, just return null.
                        return null;
                    }
                    if (file.exists()) return SkinIOUtils.loadSkinFromStream(new FileInputStream(file));
                    return null;
                }
            };
    }

    @Nonnull
    protected abstract File getCachedLocation(Entity entity, SkinType skinType);

    @Nullable
    protected abstract URL getRemoteSkinLocation(Entity entity, SkinType type);
}
