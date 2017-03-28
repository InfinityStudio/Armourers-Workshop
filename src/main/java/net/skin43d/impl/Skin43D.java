package net.skin43d.impl;

import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.impl.cubes.CubeRegistry;
import net.skin43d.skin3d.SkinTypeRegistry;

/**
 * @author ci010
 */
public abstract class Skin43D {
    public static Skin43D instance() {
        return ModSkin43D.getProxy();
    }

    public abstract SkinTypeRegistry getSkinRegistry();

    //unused
    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinProvider getSkinProvider();

    public abstract SkinBakery getSkinBakery();

    public abstract CubeRegistry getCubeRegistry();

    public abstract Context getContext();

    public interface Context {
        int getTextureWidth();

        int getTextureHeight();

        int getTextureSize();

        int getFileVersion();

        boolean useSafeTexture();

        boolean useMultipassSkinRendering();

        int getNumberOfRenderLayers();

        double getLodDistance();

        int getMaxLodLevel();

        int getRenderDistance();

        boolean wireframeRender();

        boolean disableTexturePainting();
    }
}
