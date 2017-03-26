package net.skin43d.impl;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinInfoProvider;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;

/**
 * @author ci010
 */
public class ContextProxy extends Context {

    void preInit(FMLPreInitializationEvent event) {

    }

    void init(FMLInitializationEvent event) {

    }

    void postInit(FMLPostInitializationEvent event) {

    }


    @Override
    public int getTextureSize() {
        return 0;
    }

    @Override
    public int getFileVersion() {
        return ModSkin43D.FILE_VERSION;
    }

    @Override
    public ISkinTypeRegistry getSkinRegistry() {
        return null;
    }

    @Override
    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        return null;
    }

    @Override
    public SkinInfoProvider getSkinProvider() {
        return null;
    }
}
