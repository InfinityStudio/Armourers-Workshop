package net.skin43d.impl;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.PlayerTextureHandler;
import net.skin43d.utils.ModLogger;
import org.apache.logging.log4j.Level;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;
import riskyken.armourersWorkshop.client.ClientProxy;
import riskyken.armourersWorkshop.client.render.RenderEngine;
import riskyken.armourersWorkshop.client.render.engine.attach.RenderEngineAttach;
import riskyken.armourersWorkshop.client.render.engine.special.RenderEngineSpecial;

/**
 * @author ci010
 */
public class ContextProxy extends Context {
    private SkinProvider provider;
    private EquipmentWardrobeProvider equipmentWardrobeHandler;
    private RenderEngine renderEngine;

    void preInit(FMLPreInitializationEvent event) {

    }

    void init(FMLInitializationEvent event) {
        ClientProxy.SkinRenderType type = getSkinRenderType();
        if (type == ClientProxy.SkinRenderType.MODEL_ATTACHMENT)
            this.renderEngine = new RenderEngineAttach();
        else if (type == ClientProxy.SkinRenderType.RENDER_EVENT)
            this.renderEngine = new RenderEngineSpecial();
        else this.renderEngine = new RenderEngineAttach();//TODO handle this exception
        this.renderEngine.deploy();
        MinecraftForge.EVENT_BUS.register(new PlayerTextureHandler());
    }

    void postInit(FMLPostInitializationEvent event) {

    }

    private boolean shadersModLoaded;
    private boolean moreplayermodelsLoaded;
    private boolean coloredLightsLoaded;
    private boolean smartMovingLoaded;
    private boolean jrbaClientLoaded;

    private ClientProxy.SkinRenderType getSkinRenderType() {
        if (moreplayermodelsLoaded)
            return ClientProxy.SkinRenderType.RENDER_EVENT;
        if (shadersModLoaded & !smartMovingLoaded)
            return ClientProxy.SkinRenderType.RENDER_EVENT;
        if (coloredLightsLoaded & !smartMovingLoaded)
            return ClientProxy.SkinRenderType.RENDER_EVENT;
        if (jrbaClientLoaded)
            return ClientProxy.SkinRenderType.RENDER_EVENT;
        return ClientProxy.SkinRenderType.MODEL_ATTACHMENT;
//        switch (ConfigHandlerClient.skinRenderType) {
//            case 1: //Force render event
//                return ClientProxy.SkinRenderType.RENDER_EVENT;
//            case 2: //Force model attachment
//                return ClientProxy.SkinRenderType.MODEL_ATTACHMENT;
//            case 3: //Force render layer
//                return ClientProxy.SkinRenderType.RENDER_LAYER;
//            default: //Auto
//                if (moreplayermodelsLoaded)
//                    return ClientProxy.SkinRenderType.RENDER_EVENT;
//                if (shadersModLoaded & !smartMovingLoaded)
//                    return ClientProxy.SkinRenderType.RENDER_EVENT;
//                if (coloredLightsLoaded & !smartMovingLoaded)
//                    return ClientProxy.SkinRenderType.RENDER_EVENT;
//                if (jrbaClientLoaded)
//                    return ClientProxy.SkinRenderType.RENDER_EVENT;
//                return ClientProxy.SkinRenderType.MODEL_ATTACHMENT;
//        }
    }

    private void enableCrossModSupport() {
        try {
            Class.forName("shadersmodcore.client.Shaders");
            ModLogger.log("Shaders impl support active");
            shadersModLoaded = true;
        } catch (Exception e) {
        }
        if (Loader.isModLoaded("moreplayermodels")) {
            moreplayermodelsLoaded = true;
            ModLogger.log("More Player Models support active");
        }
        if (Loader.isModLoaded("easycoloredlights")) {
            coloredLightsLoaded = true;
            ModLogger.log("Colored Lights support active");
        }
        if (Loader.isModLoaded("SmartMoving")) {
            smartMovingLoaded = true;
            ModLogger.log("Smart Moving support active");
        }
        try {
            Class.forName("JinRyuu.JBRA.JBRA");
            jrbaClientLoaded = true;
            ModLogger.log("JRBA Client support active");
        } catch (Exception e) {
        }
        if (moreplayermodelsLoaded & smartMovingLoaded) {
            ModLogger.log(Level.WARN, "Smart Moving and More Player Models are both installed. Armourer's Workshop cannot support this.");
        }
        if (coloredLightsLoaded & smartMovingLoaded) {
            ModLogger.log(Level.WARN, "Colored Lights and Smart Moving are both installed. Armourer's Workshop cannot support this.");
        }

        ModLogger.log("Skin render type set to: " + getSkinRenderType().toString().toLowerCase());
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
    public ISkinTypeRegistry getSkinRegistry() {
        return null;
    }

    @Override
    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        return equipmentWardrobeHandler;
    }

    @Override
    public SkinProvider getSkinProvider() {
        return provider;
    }
}
