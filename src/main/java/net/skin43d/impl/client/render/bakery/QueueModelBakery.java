package net.skin43d.impl.client.render.bakery;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.skin3d.Skin3D;
import net.skin43d.impl.skin.Skin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SideOnly(Side.CLIENT)
public final class QueueModelBakery implements SkinBakery {
    private CompletionService<Skin> skinCompletion;
    private AtomicInteger bakingQueue = new AtomicInteger(0);

    public QueueModelBakery() {
        Executor skinDownloadExecutor = Executors.newFixedThreadPool(2);
        skinCompletion = new ExecutorCompletionService<Skin>(skinDownloadExecutor);
        FMLCommonHandler.instance().bus().register(this);
    }

    public int getBakingQueueSize() {
        return bakingQueue.get();
    }

    @Override
    public ListenableFuture<Skin> bake(Skin3D skin) {
        bakingQueue.incrementAndGet();
        skinCompletion.submit(new LegacyBakeSkinTask((Skin) skin));
        return null;
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
