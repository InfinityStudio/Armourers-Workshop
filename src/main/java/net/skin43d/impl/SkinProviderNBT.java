package net.skin43d.impl;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.SkinIOUtils;
import org.apache.commons.io.FileUtils;
import net.skin43d.impl.skin.Skin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * /**
 * The skin cache location is stored in entity's nbt.
 * MC Server needs to have a support for this. (Or client transformation/adaption)
 *
 * @author ci010
 * @see SkinProviderNBT#putLocation(Entity, SkinType, String)
 */
public abstract class SkinProviderNBT extends AbstractSkinProvider {
    private String cachedDirName;

    public SkinProviderNBT(SkinBakery bakery, ListeningExecutorService service, @Nonnull String cachedDirName) {
        super(bakery, service);
        Preconditions.checkNotNull(cachedDirName);
        this.cachedDirName = cachedDirName;
    }

    @Override
    protected Callable<Skin> requestSkinTask(final Entity entity, final SkinType skinType) {
        NBTTagCompound skin43d = null;
        if (entity.getEntityData().hasKey("skin43d")) skin43d = entity.getEntityData().getCompoundTag("skin43d");
        final String loc = skin43d != null ? skin43d.getString(skinType.getRegistryName()) : "";
        if (loc.isEmpty())
            return new Callable<Skin>() {
                @Override
                public Skin call() throws Exception {
                    return null;
                }
            };
        File dir = new File(Minecraft.getMinecraft().mcDataDir, cachedDirName);
        if (!dir.exists()) dir.mkdir();
        final File file = new File(dir, loc);
        if (file.exists()) return new Callable<Skin>() {
            @Override
            public Skin call() throws Exception {
                return SkinIOUtils.loadSkinFromFileByBuffer(file);
            }
        };
        else return new Callable<Skin>() {
            @Override
            public Skin call() throws Exception {
                URL url = getRemoteSkinLocation(entity, skinType, loc);
                if (url == null) return null;//unknown skin
                try {
                    FileUtils.copyURLToFile(url, file);
                } catch (IOException e) {
                    //if there is no such file or internet error, just return null;
                    //maybe someday, someone will need to handle this exception;
                    return null;
                }
                if (file.exists()) return SkinIOUtils.loadSkinFromFileByBuffer(file);
                return null;
            }
        };
    }

    @Nullable
    protected abstract URL getRemoteSkinLocation(Entity entity, SkinType type, String skinId);

    /**
     * Put the skin location into entity.
     * The helper method for client adaptor.
     *
     * @param entity   The entity will be set skin
     * @param type     The type of the skin
     * @param location The location of the skin
     */
    public static void putLocation(Entity entity, SkinType type, String location) {
        NBTTagCompound skin43d;
        if (entity.getEntityData().hasKey("skin43d")) {
            skin43d = entity.getEntityData().getCompoundTag("skin43d");
        } else entity.getEntityData().setTag("skin43d", skin43d = new NBTTagCompound());
        skin43d.setString(type.getRegistryName(), location);
    }
}
