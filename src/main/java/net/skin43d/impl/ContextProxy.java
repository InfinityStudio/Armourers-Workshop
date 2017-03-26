package net.skin43d.impl;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import removequ.EquipmentWardrobeData;
import riskyken.armourersWorkshop.api.common.skin.type.SkinTypeRegistry;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;

/**
 * @author ci010
 */
public class ContextProxy extends Context {
    private EquipmentWardrobeProvider equipmentWardrobeHandler;
    private SkinTypeRegistryImpl skinTypeRegistry;
    private CubeRegistry cubeRegistry;

    protected void preInit(FMLPreInitializationEvent event) {

    }

    protected void init(FMLInitializationEvent event) {
        skinTypeRegistry = new SkinTypeRegistryImpl();
        cubeRegistry = new CubeRegistry();
        equipmentWardrobeHandler = new EquipmentWardrobeProvider() {
            @Override
            public void setEquipmentWardrobeData(PlayerPointer playerPointer, EquipmentWardrobeData ewd) {

            }

            @Override
            public EquipmentWardrobeData getEquipmentWardrobeData(PlayerPointer playerPointer) {
                return null;
            }

            @Override
            public void removeEquipmentWardrobeData(PlayerPointer playerPointer) {

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
    public CubeRegistry getCubeRegistry() {
        return cubeRegistry;
    }
}
