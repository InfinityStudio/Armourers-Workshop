package riskyken.armourersWorkshop.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.client.PlayerTextureHandler;
import net.skin43d.utils.ModLogger;
import org.apache.logging.log4j.Level;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.client.render.RenderEngine;
import riskyken.armourersWorkshop.common.CommonProxy;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private SkinProvider provider;
    private EquipmentWardrobeProvider equipmentWardrobeHandler;
    private RenderEngine renderEngine;

    public SkinProvider getSkinProvider() {
        return provider;
    }

    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        return equipmentWardrobeHandler;
    }

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
//        Minecraft.getMinecraft().theWorld.addWorldAccess(new WorldListener());
//        provider = new AbstractSkinProvider(skinRepository);
        //TODO init skinProvider
        enableCrossModSupport();
        spamSillyMessages();
    }

    @Override
    public void init() {
//        SkinRenderType type = getSkinRenderType();
//        if (type == SkinRenderType.MODEL_ATTACHMENT)
//            this.renderEngine = new RenderEngineAttach();
//        else if (type == SkinRenderType.RENDER_EVENT)
//            this.renderEngine = new RenderEngineSpecial();
//        else this.renderEngine = new RenderEngineAttach();//TODO handle this exception
//        this.renderEngine.deploy();
//        this.equipmentWardrobeHandler = new EquipmentWardrobeHandler();
//        ClientSkinCache.init();
        MinecraftForge.EVENT_BUS.register(new PlayerTextureHandler());
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

    @SubscribeEvent
    public void join(EntityJoinWorldEvent joinWorldEvent) {
        if (joinWorldEvent.entity.worldObj.isRemote && joinWorldEvent.entity instanceof EntityPlayer) {
            EntityPlayer entity = (EntityPlayer) joinWorldEvent.entity;
            entity.getEntityData();
        }
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

//        ModLogger.log("Skin render type set to: " + getSkinRenderType().toString().toLowerCase());
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

    public enum SkinRenderType {
        RENDER_EVENT,
        MODEL_ATTACHMENT,
        RENDER_LAYER
    }
}
