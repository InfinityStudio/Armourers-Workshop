package riskyken.armourersWorkshop.client.render.bake;

import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.armourersWorkshop.api.common.skin.data.ISkin;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;

@SideOnly(Side.CLIENT)
public final class QueueModelBakery implements SkinBakery {

    public static final QueueModelBakery INSTANCE = new QueueModelBakery();

    private CompletionService<Skin> skinCompletion;
    private AtomicInteger bakingQueue = new AtomicInteger(0);

    private QueueModelBakery() {
        Executor skinDownloadExecutor = Executors.newFixedThreadPool(ConfigHandlerClient.maxModelBakingThreads);
        skinCompletion = new ExecutorCompletionService<Skin>(skinDownloadExecutor);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.side == Side.CLIENT & event.type == Type.CLIENT & event.phase == Phase.END)
            checkBakery();
    }

    public void receivedUnbakedModel(Skin skin) {
        bakingQueue.incrementAndGet();
        skinCompletion.submit(new BakeSkinTask(skin));
    }

    private void checkBakery() {
        Future<Skin> futureSkin = skinCompletion.poll();
        while (futureSkin != null) {
            try {
                Skin skin = futureSkin.get();
                if (skin != null) {
                    bakingQueue.decrementAndGet();
                    ClientSkinCache.INSTANCE.receivedModelFromBakery(skin);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            futureSkin = skinCompletion.poll();
        }
    }

    public int getBakingQueueSize() {
        return bakingQueue.get();
    }

    @Override
    public void bake(ISkin skin) {
        this.receivedUnbakedModel((Skin) skin);
    }
}
