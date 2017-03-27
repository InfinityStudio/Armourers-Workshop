package net.skin43d.impl;

import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import riskyken.armourersWorkshop.api.common.skin.type.SkinTypeRegistry;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;

/**
 * @author ci010
 */
public abstract class Context {
    public static Context instance() {
        return ModSkin43D.getProxy();
    }

    public abstract int getTextureWidth();

    public abstract int getTextureHeight();

    public abstract int getTextureSize();

    public abstract int getFileVersion();

    public abstract boolean useSafeTexture();

    public abstract boolean useMultipassSkinRendering();

    public abstract int getNumberOfRenderLayers();

    public abstract SkinTypeRegistry getSkinRegistry();

    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinProvider getSkinProvider();

    public abstract SkinBakery getSkinBakery();

    public abstract CubeRegistry getCubeRegistry();
}
