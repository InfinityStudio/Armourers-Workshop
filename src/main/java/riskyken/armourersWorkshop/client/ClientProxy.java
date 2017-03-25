package riskyken.armourersWorkshop.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cijhn.EquipmentWardrobeProvider;
import net.cijhn.SkinProvider;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import riskyken.armourersWorkshop.TestEnvSetup;
import riskyken.armourersWorkshop.client.handler.*;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.client.render.PlayerTextureHandler;
import riskyken.armourersWorkshop.client.render.SkinModelRenderer;
import riskyken.armourersWorkshop.client.render.bake.QueueModelBakery;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.CommonProxy;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.lib.LibModInfo;
import riskyken.armourersWorkshop.common.skin.EntityEquipmentData;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.utils.ModLogger;

import java.io.File;
import java.lang.reflect.Field;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private SkinProvider provider;

    @Override
    public EntityPlayer getLocalPlayer() {
        return SkinModelRenderer.INSTANCE.targetPlayer;
    }

    public SkinProvider getSkinProvider() {
        return provider;
    }

    public PlayerTextureHandler getPlayerTextureHandler() {
        return playerTextureHandler;
    }

    public EquipmentWardrobeProvider getEquipmentWardrobeHandler() {
        return equipmentWardrobeHandler;
    }

    public static EquipmentWardrobeProvider equipmentWardrobeHandler;
    public static PlayerTextureHandler playerTextureHandler;

    private static boolean shadersModLoaded;
    private static boolean moreplayermodelsLoaded;
    private static boolean coloredLightsLoaded;
    private static boolean smartMovingLoaded;
    private static boolean jrbaClientLoaded;

    public static int renderPass;
    public static IIcon dyeBottleSlotIcon;

    public static boolean isJrbaClientLoaded() {
        return jrbaClientLoaded;
    }

    public ClientProxy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void preInit(File configDir) {
        super.preInit(configDir);
        provider = new TestEnvSetup();
        //TODO init skinProvider
        enableCrossModSupport();
        spamSillyMessages();
    }

    public void preInit() {

    }

    @Override
    public void initRenderers() {
        SkinModelRenderer.init();
        //TODO this renderer need to be hook on other things...
//        MinecraftForgeClient.registerItemRenderer(Items.diamond_boots, new RenderItemEquipmentSkin());
    }

    @Override
    public void init() {
        super.init();
        equipmentWardrobeHandler = new EquipmentWardrobeHandler();
        playerTextureHandler = new PlayerTextureHandler();
        ClientSkinCache.init();
        FMLCommonHandler.instance().bus().register(new ModClientFMLEventHandler());
        MinecraftForge.EVENT_BUS.register(new DebugTextHandler());
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 1) {
            dyeBottleSlotIcon = event.map.registerIcon(LibItemResources.SLOT_DYE_BOTTLE);
        }
    }

    private void enableCrossModSupport() {
        try {
            Class.forName("shadersmodcore.client.Shaders");
            ModLogger.log("Shaders mod support active");
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

    public static SkinRenderType getSkinRenderType() {
        switch (ConfigHandlerClient.skinRenderType) {
            case 1: //Force render event
                return SkinRenderType.RENDER_EVENT;
            case 2: //Force model attachment
                return SkinRenderType.MODEL_ATTACHMENT;
            case 3: //Force render layer
                return SkinRenderType.RENDER_LAYER;
            default: //Auto
                if (moreplayermodelsLoaded) {
                    return SkinRenderType.RENDER_EVENT;
                }
                if (shadersModLoaded & !smartMovingLoaded) {
                    return SkinRenderType.RENDER_EVENT;
                }
                if (coloredLightsLoaded & !smartMovingLoaded) {
                    return SkinRenderType.RENDER_EVENT;
                }
                if (jrbaClientLoaded) {
                    return SkinRenderType.RENDER_EVENT;
                }
                return SkinRenderType.MODEL_ATTACHMENT;
        }
    }

    public static boolean useSafeTextureRender() {
        if (shadersModLoaded) {
            return true;
        }
        if (ConfigHandlerClient.skinTextureRenderOverride) {
            return true;
        }
        if (coloredLightsLoaded) {
            return true;
        }
        return false;
    }

    public static boolean useMultipassSkinRendering() {
        return ConfigHandlerClient.multipassSkinRendering;
    }

    public static int getNumberOfRenderLayers() {
        if (useMultipassSkinRendering()) {
            return 4;
        } else {
            return 2;
        }
    }

    private void spamSillyMessages() {
        if (Loader.isModLoaded("Tails")) {
            ModLogger.log("Tails detected! - Sand praising module active.");
        }
        if (Loader.isModLoaded("BuildCraft|Core")) {
            ModLogger.log("Buildcraft detected! - Enabling knishes support.");
        }
        if (Loader.isModLoaded("integratedcircuits")) {
            ModLogger.log("Integrated Circuits detected! - Applying cosplay to mannequins.");
        }
    }

    //TODO Remove this and use IWorldAccess
    public static void playerLeftTrackingRange(PlayerPointer playerPointer) {
        equipmentWardrobeHandler.removeEquipmentWardrobeData(playerPointer);
    }

    @Override
    public int getPlayerModelCacheSize() {
        return ClientSkinCache.INSTANCE.getCacheSize();
    }

    @Override
    public void receivedEquipmentData(Skin skin) {
        QueueModelBakery.INSTANCE.receivedUnbakedModel(skin);
    }


    @Override
    public void receivedEquipmentData(EntityEquipmentData equipmentData, int entityId) {
    }

    @Override
    public int getBlockRenderType(Block block) {
        return super.getBlockRenderType(block);
    }

    public enum SkinRenderType {
        RENDER_EVENT,
        MODEL_ATTACHMENT,
        RENDER_LAYER
    }
}
