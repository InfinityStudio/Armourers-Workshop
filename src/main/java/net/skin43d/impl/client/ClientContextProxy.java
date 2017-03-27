package net.skin43d.impl.client;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.skin43d.SkinProvider;
import net.skin43d.impl.ContextProxy;
import net.skin43d.impl.SkinProviderLocal;
import net.skin43d.impl.Test;
import net.skin43d.impl.client.render.engine.RenderEngine;
import net.skin43d.utils.ModLogger;
import net.skin43d.utils.SkinIOUtils;
import org.apache.logging.log4j.Level;
import net.skin43d.impl.client.render.bakery.AsyncModelBakery;
import net.skin43d.impl.client.render.engine.attach.RenderEngineAttach;
import net.skin43d.impl.client.render.engine.core.RenderEngineSpecial;
import net.skin43d.impl.skin.Skin;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author ci010
 */
public class ClientContextProxy extends ContextProxy {
    private SkinProvider provider;
    private RenderEngine renderEngine;
    private ListeningExecutorService service;

    private String dir = "D:\\Storage\\Desktop\\testSkin";

    @Override
    public SkinProvider getSkinProvider() {
        return provider;
    }

    @Override
    protected void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    protected void init(FMLInitializationEvent event) {
        super.init(event);
        enableCrossModSupport();
        SkinRenderType type = getSkinRenderType();
        if (type == SkinRenderType.MODEL_ATTACHMENT)
            this.renderEngine = new RenderEngineAttach();
        else if (type == SkinRenderType.RENDER_EVENT)
            this.renderEngine = new RenderEngineSpecial();
        else this.renderEngine = new RenderEngineAttach();//TODO handle this exception
        this.renderEngine.deploy();
        service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        MinecraftForge.EVENT_BUS.register(new PlayerTextureHandler());
        AsyncModelBakery bakery = new AsyncModelBakery(service);

        /////TEST CODE//////
        File file = new File(dir, "c");
        List<Skin> ls = Lists.newArrayList();
        for (File skF : file.listFiles()) {
            Skin skin = SkinIOUtils.loadSkinFromFileByBuffer(skF);
            System.out.println("LOADING " + skin);
            bakery.bake(skin);
            ls.add(skin);
        }
        this.provider = new SkinProviderLocal(ls.toArray(new Skin[ls.size()]));
        /////TEST CODE//////
    }

    @Override
    protected void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        Test.setup();
    }

    public enum SkinRenderType {
        RENDER_EVENT,
        MODEL_ATTACHMENT,
        RENDER_LAYER
    }

    private SkinRenderType getSkinRenderType() {
//        if (moreplayermodelsLoaded)
//            return SkinRenderType.RENDER_EVENT;
//        if (shadersModLoaded & !smartMovingLoaded)
//            return SkinRenderType.RENDER_EVENT;
//        if (coloredLightsLoaded & !smartMovingLoaded)
//            return SkinRenderType.RENDER_EVENT;
//        if (jrbaClientLoaded)
//            return SkinRenderType.RENDER_EVENT;
        return SkinRenderType.MODEL_ATTACHMENT;
    }

    private boolean shadersModLoaded;
    private boolean moreplayermodelsLoaded;
    private boolean coloredLightsLoaded;
    private boolean smartMovingLoaded;
    private boolean jrbaClientLoaded;

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
}
