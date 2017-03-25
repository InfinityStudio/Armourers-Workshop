package riskyken.armourersWorkshop.client.render.bake;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.armourersWorkshop.api.common.skin.data.ISkin;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SideOnly(Side.CLIENT)
public final class QueueModelBakery implements SkinBakery {
    private CompletionService<Skin> skinCompletion;
    private AtomicInteger bakingQueue = new AtomicInteger(0);

    public QueueModelBakery() {
        Executor skinDownloadExecutor = Executors.newFixedThreadPool(ConfigHandlerClient.maxModelBakingThreads);
        skinCompletion = new ExecutorCompletionService<Skin>(skinDownloadExecutor);
        FMLCommonHandler.instance().bus().register(this);
    }

    public int getBakingQueueSize() {
        return bakingQueue.get();
    }

    @Override
    public void bake(ISkin skin) {
        bakingQueue.incrementAndGet();
        skinCompletion.submit(new BakeSkinTask((Skin) skin));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.side == Side.CLIENT & event.type == Type.CLIENT & event.phase == Phase.END)
            checkBakery();
    }

    private void checkBakery() {
        Future<Skin> futureSkin = skinCompletion.poll();
        while (futureSkin != null) {
            try {
                Skin skin = futureSkin.get();
                if (skin != null) {
                    bakingQueue.decrementAndGet();
//                    ClientSkinCache.INSTANCE.receivedModelFromBakery(skin);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            futureSkin = skinCompletion.poll();
        }
    }
}
