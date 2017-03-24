package riskyken.armourersWorkshop.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import riskyken.armourersWorkshop.common.lib.LibModInfo;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientGuiAdminPanel;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientGuiSetArmourerSkinProps;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientGuiToolOptionUpdate;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientKeyPress;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientRequestSkinData;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientRequestSkinId;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientSkinPart;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientSkinWardrobeUpdate;
import riskyken.armourersWorkshop.common.network.messages.client.MessageClientToolPaintBlock;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerClientCommand;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerEntitySkinData;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerLibraryFileList;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerLibrarySendSkin;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerPlayerLeftTrackingRange;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerSkinDataSend;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerSkinIdSend;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerSkinInfoUpdate;
import riskyken.armourersWorkshop.common.network.messages.server.MessageServerSkinWardrobeUpdate;

public class PacketHandler {

    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LibModInfo.CHANNEL);
    private static int packetId = 0;
    
    public static void init() {
        registerMessage(MessageServerSkinInfoUpdate.class, MessageServerSkinInfoUpdate.class, Side.CLIENT);
        registerMessage(MessageServerPlayerLeftTrackingRange.class, MessageServerPlayerLeftTrackingRange.class, Side.CLIENT);
        registerMessage(MessageServerLibraryFileList.class, MessageServerLibraryFileList.class, Side.CLIENT);
        registerMessage(MessageClientKeyPress.class, MessageClientKeyPress.class, Side.SERVER);
        registerMessage(MessageClientGuiToolOptionUpdate.class, MessageClientGuiToolOptionUpdate.class, Side.SERVER);
        registerMessage(MessageClientSkinWardrobeUpdate.class, MessageClientSkinWardrobeUpdate.class, Side.SERVER);
        registerMessage(MessageServerSkinWardrobeUpdate.class, MessageServerSkinWardrobeUpdate.class, Side.CLIENT);
        registerMessage(MessageClientRequestSkinData.class, MessageClientRequestSkinData.class, Side.SERVER);
        registerMessage(MessageServerSkinDataSend.class, MessageServerSkinDataSend.class, Side.CLIENT);
        registerMessage(MessageClientGuiSetArmourerSkinProps.class, MessageClientGuiSetArmourerSkinProps.class, Side.SERVER);
        registerMessage(MessageServerClientCommand.class, MessageServerClientCommand.class, Side.CLIENT);
        registerMessage(MessageServerEntitySkinData.class, MessageServerEntitySkinData.class, Side.CLIENT);
        registerMessage(MessageServerLibrarySendSkin.class, MessageServerLibrarySendSkin.class, Side.CLIENT);
        registerMessage(MessageClientSkinPart.class, MessageClientSkinPart.class, Side.SERVER);
        registerMessage(MessageClientRequestSkinId.class, MessageClientRequestSkinId.class, Side.SERVER);
        registerMessage(MessageServerSkinIdSend.class, MessageServerSkinIdSend.class, Side.CLIENT);
        registerMessage(MessageClientToolPaintBlock.class, MessageClientToolPaintBlock.class, Side.SERVER);
        registerMessage(MessageClientGuiAdminPanel.class, MessageClientGuiAdminPanel.class, Side.SERVER);
    }
    
    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId, side);
        packetId++;
    }
}
