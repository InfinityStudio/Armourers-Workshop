package net.skin43d.impl;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author ci010
 */
@Mod(modid = ModSkin43D.MOD_ID, name = ModSkin43D.NAME, version = ModSkin43D.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.10.2]")
public class ModSkin43D {
    public static final String MOD_ID = "skin43d", NAME = "Skin For 3D", VERSION = "0.1";
    public static final String COMMON_PROXY = "net.skin43d.impl.Skin43DProxy",
            CLIENT_PROXY = "net.skin43d.impl.client.ClientSkin43DProxy";

    public static final int FILE_VERSION = 12;

    public static final int TEXTURE_WIDTH = 64;
    public static final int TEXTURE_HEIGHT = 32;
    public static final int TEXTURE_SIZE = TEXTURE_WIDTH * TEXTURE_HEIGHT;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static Skin43DProxy proxy;

    static Skin43DProxy getProxy() {
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
