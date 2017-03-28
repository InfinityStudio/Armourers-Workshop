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
    private boolean useMultipassSkinRendering = true, disableTexturePainting = false;

    private boolean useSafeTexture = false, wireframeRender = false;

    Skin43DConfig() {
    }

    Skin43DConfig(Configuration config) {
        loadConfig(config);
    }

    public void loadConfig(Configuration config) {
        maxLodLevels = config.getInt("maxLodLevels", "client", 4, 0, 4,
                "Number of LOD models to create. Higher number should give a boost to framerate at a small cost to VRAM.");
        disableTexturePainting = config.getBoolean("disableTexturePainting", "skin43d", false,
                "Disables replacing the players texture with a painted version.\n"
                        + "Disabling this may fix issues with the players texture rendering\n"
                        + "incorrectly or showing the steve skin.");
        useMultipassSkinRendering = config.getBoolean("multipassSkinRendering", "client", true,
                "When enabled skin will render in multiple passes to reduce visual artifacts.\n"
                        + "Disabling this will improve skin rendering performance at the cost of visual quality.");
        lodDistance = config.getFloat("lodDistance", "client", 32F, 8, 128,
                "Distance away that skins will have lod applied to them.");
        maxSkinRenderDistance = config
                .get("client", "maxSkinRenderDistance", 8192,
                        "The max distance away squared that skins will render.")
                .getInt(8192);
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
