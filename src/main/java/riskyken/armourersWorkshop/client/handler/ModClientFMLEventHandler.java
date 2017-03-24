package riskyken.armourersWorkshop.client.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.lib.LibModInfo;

public class ModClientFMLEventHandler {

    private static final String DOWNLOAD_URL = "http://minecraft.curseforge.com/mc-mods/229523-armourers-workshop/files";
    private boolean shownUpdateInfo = false;
    private boolean showmDevWarning;
    public static float renderTickTime;
    public static int skinRendersThisTick = 0;
    public static int skinRenderLastTick = 0;

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equals(LibModInfo.ID)) {
            ConfigHandlerClient.loadConfigFile();
        }
    }

//    public void onPlayerTickEndEvent() {
//        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
//        if (!shownUpdateInfo && UpdateCheck.updateFound) {
//            shownUpdateInfo = true;
//            ChatComponentText updateMessage = new ChatComponentText(TranslateUtils.translate("chat.armourersworkshop:updateAvailable", UpdateCheck.remoteModVersion) + " ");
//            ChatComponentText updateURL = new ChatComponentText(TranslateUtils.translate("chat.armourersworkshop:updateDownload"));
//            updateURL.getChatStyle().setUnderlined(true);
//            updateURL.getChatStyle().setColor(EnumChatFormatting.BLUE);
//            updateURL.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(TranslateUtils.translate("chat.armourersworkshop:updateDownloadRollover"))));
//            updateURL.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, DOWNLOAD_URL));
//            updateMessage.appendSibling(updateURL);
//            player.addChatMessage(updateMessage);
//        }
//        if (!showmDevWarning && LibModInfo.DEVELOPMENT_VERSION) {
//            ChatComponentText devWarning = new ChatComponentText(TranslateUtils.translate("chat.armourersworkshop:devWarning"));
//            devWarning.getChatStyle().setColor(EnumChatFormatting.RED);
//            player.addChatMessage(devWarning);
//            showmDevWarning = true;
//        }
//    }

    //cancel the key message sending...
//    @SubscribeEvent
//    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
//        if (Keybindings.openCustomArmourGui.isPressed() & ConfigHandler.allowEquipmentWardrobe) {
//            PacketHandler.networkWrapper.sendToServer(new MessageClientKeyPress((byte) 0));
//        }
//        if (Keybindings.undo.isPressed()) {
//            PacketHandler.networkWrapper.sendToServer(new MessageClientKeyPress((byte) 1));
//        }
//    }

    //cancel official update...
//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (event.side == Side.CLIENT) {
//            if (event.type == Type.PLAYER) {
//                if (event.phase == Phase.END) {
//                    onPlayerTickEndEvent();
//                }
//            }
//        }
//    }

    @SubscribeEvent
    public void onRenderTickEvent(RenderTickEvent event) {
        if (event.phase == Phase.START) {
            renderTickTime = event.renderTickTime;
            skinRenderLastTick = skinRendersThisTick;
            skinRendersThisTick = 0;
        }
    }
}
