package riskyken.armourersWorkshop.common.config;

import net.minecraftforge.common.MinecraftForge;

public final class ConfigSynchronizeHandler {
    
    public ConfigSynchronizeHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    //TODO other way to sync config
//    @SubscribeEvent
//    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
//        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP) {
//            MessageServerSyncConfig message = new MessageServerSyncConfig();
//            PacketHandler.networkWrapper.sendTo(message, (EntityPlayerMP) event.entity);
//        }
//    }
    
//    public static void resyncConfigs() {
//        MessageServerSyncConfig message = new MessageServerSyncConfig();
//        PacketHandler.networkWrapper.sendToAll(message);
//    }
}
