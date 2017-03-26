package net.skin43d.impl;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author ci010
 */
@Mod(modid = ModSkin43D.MOD_ID, name = ModSkin43D.NAME, version = ModSkin43D.VERSION)
public class ModSkin43D {
    public static final String MOD_ID = "skin43D", NAME = "Skin For 3D", VERSION = "0.1";
    public static final String COMMON_PROXY = "net.skin43d.impl.ContextProxy",
            CLIENT_PROXY = "net.skin43d.impl.client.ClientContextProxy";

    public static final int FILE_VERSION = 12;

    public static final int TEXTURE_WIDTH = 64;
    public static final int TEXTURE_HEIGHT = 32;
    public static final int TEXTURE_SIZE = TEXTURE_WIDTH * TEXTURE_HEIGHT;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    private static ContextProxy proxy;

    static ContextProxy getProxy() {
        return proxy;
    }

    @Mod.EventHandler
    public void perInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
