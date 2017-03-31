package net.skin43d.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.entity.Entity;
import net.skin43d.SkinProvider;
import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.impl.skin.Skin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @author ci010
 */
public abstract class AbstractSkinProvider implements SkinProvider {
    private Cache<String, Skin> skinCache;
    private SkinBakery bakery;
    private ListeningExecutorService service;

    private static final Skin EMPTY = new Skin(null, null, null, null);

    public AbstractSkinProvider(SkinBakery bakery, ListeningExecutorService service) {
        this.bakery = bakery;
        this.service = service;
        this.skinCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .concurrencyLevel(1)
                .removalListener(new RemovalListener<String, Skin>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Skin> notification) {
                        Skin s = notification.getValue();
                        if (s != null) {
                            s.blindPaintTexture();
                            s.cleanUpDisplayLists();
                        }
                    }
                })
                .build();
    }

    /**
     * Force to request the skin and cache again.
     *
     * @param entity   The entity
     * @param skinType The skin type
     */
    public void requestSkin(final Entity entity, final SkinType skinType) {
        final ListenableFuture<Skin> future = service.submit(requestSkinTask(entity, skinType));
        future.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    Skin skin = future.get();
                    if (skin != null) {
                        bakery.bake(skin);
                        skinCache.put(createKey(entity, skinType), skin);
                    } else skinCache.put(createKey(entity, skinType), EMPTY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, service);
    }

    protected abstract Callable<Skin> requestSkinTask(Entity entity, SkinType skinType);

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType) {
        String key = createKey(entity, skinType);
        Skin skin = skinCache.getIfPresent(key);
        skinCache.put(key, EMPTY);
        if (skin == null) requestSkin(entity, skinType);
        if (skin == EMPTY) return null;
        return skin;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, SkinType skinType) {
        return null;
    }

    protected String createKey(Entity entity, SkinType skinType) {
        return entity.getUniqueID() + skinType.getRegistryName();
    }
}
