package net.skin43d.impl;

import net.minecraftforge.common.config.Configuration;

/**
 * @author ci010
 */
public class Skin43DConfig implements Skin43D.Context {
    private final int WIDTH = 64, HEIGHT = 32, SIZE = WIDTH * HEIGHT, FILE_VERSION = 12;

    private int maxLodLevels = 4;
    private double lodDistance = 32F;
    private int maxSkinRenderDistance = 128;
    private boolean useMultipassSkinRendering = true, useSafeTexture = false, wireframeRender = false, disableTexturePainting = false;

    Skin43DConfig(Configuration configuration) {
        maxLodLevels = configuration.getInt("MaxLodLevels")
    }

    @Override
    public int getTextureWidth() {
        return WIDTH;
    }

    @Override
    public int getTextureHeight() {
        return HEIGHT;
    }

    @Override
    public int getTextureSize() {
        return SIZE;
    }

    @Override
    public int getFileVersion() {
        return FILE_VERSION;
    }

    @Override
    public boolean useSafeTexture() {
        return useSafeTexture;
    }

    @Override
    public boolean useMultipassSkinRendering() {
        return useMultipassSkinRendering;
    }

    @Override
    public int getNumberOfRenderLayers() {
        return useMultipassSkinRendering() ? 4 : 2;
    }

    @Override
    public double getLodDistance() {
        return lodDistance;
    }

    @Override
    public int getMaxLodLevel() {
        return maxLodLevels;
    }

    @Override
    public int getRenderDistance() {
        return maxSkinRenderDistance;
    }

    @Override
    public boolean wireframeRender() {
        return wireframeRender;
    }

    @Override
    public boolean disableTexturePainting() {
        return disableTexturePainting;
    }
}
