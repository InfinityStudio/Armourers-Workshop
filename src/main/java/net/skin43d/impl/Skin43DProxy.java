package net.skin43d.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import riskyken.EquipmentWardrobeData;
import net.skin43d.skin3d.SkinTypeRegistry;
import net.skin43d.impl.cubes.CubeRegistry;

/**
 * @author ci010
 */
public class Skin43DProxy extends Skin43D {
    private EquipmentWardrobeProvider equipmentWardrobeHandler;
    private SkinTypeRegistryImpl skinTypeRegistry;
    private CubeRegistry cubeRegistry;

    private int maxLodLevels = 4;
    private double lodDistance = 32F;
    private int maxSkinRenderDistance = 128;
    private boolean useMultipassSkinRendering = true, useSafeTexture = false, wireframeRender = false, disableTexturePainting = false;

    protected void preInit(FMLPreInitializationEvent event) {

    }

    protected void init(FMLInitializationEvent event) {
        skinTypeRegistry = new SkinTypeRegistryImpl();
        MinecraftForge.EVENT_BUS.register(skinTypeRegistry);
        cubeRegistry = new CubeRegistry();
        equipmentWardrobeHandler = new EquipmentWardrobeProvider() {
            @Override
            public void setEquipmentWardrobeData(EntityPlayer playerPointer, EquipmentWardrobeData ewd) {

            }

            @Override
            public EquipmentWardrobeData getEquipmentWardrobeData(EntityPlayer playerPointer) {
                return null;
            }

            @Override
            public void removeEquipmentWardrobeData(EntityPlayer playerPointer) {

            }
        };
    }

    protected void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public int getTextureWidth() {
        return ModSkin43D.TEXTURE_WIDTH;
    }

    @Override
    public int getTextureHeight() {
        return ModSkin43D.TEXTURE_HEIGHT;
    }

    @Override
    public int getTextureSize() {
        return ModSkin43D.TEXTURE_SIZE;
    }

    @Override
    public int getFileVersion() {
        return ModSkin43D.FILE_VERSION;
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
        if (useMultipassSkinRendering())
            return 4;
        else
            return 2;
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

    @Override
    public SkinTypeRegistry getSkinRegistry() {
        return skinTypeRegistry;
    }

    @Override
    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        return equipmentWardrobeHandler;
    }

    @Override
    public SkinProvider getSkinProvider() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SkinBakery getSkinBakery() {
        return null;
    }

    @Override
    public CubeRegistry getCubeRegistry() {
        return cubeRegistry;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
