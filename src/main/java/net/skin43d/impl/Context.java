package net.skin43d.impl;

import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.impl.cubes.CubeRegistry;
import net.skin43d.skin3d.SkinTypeRegistry;

/**
 * @author ci010
 */
public abstract class Context {
    public static Context instance() {
        return ModSkin43D.proxy;
    }

    public abstract SkinTypeRegistry getSkinRegistry();

    //unused
    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinProvider getSkinProvider();

    public abstract SkinBakery getSkinBakery();

    public abstract CubeRegistry getCubeRegistry();

    public abstract int getTextureWidth();

    public abstract int getTextureHeight();

    public abstract int getTextureSize();

    public abstract int getFileVersion();

    public abstract boolean useSafeTexture();

    public abstract boolean useMultipassSkinRendering();

    public abstract int getNumberOfRenderLayers();

    public abstract double getLodDistance();

    public abstract int getMaxLodLevel();

    public abstract int getRenderDistance();

    public abstract boolean wireframeRender();

    public abstract boolean disableTexturePainting();
}
