package riskyken.armourersWorkshop.common;

import net.cijhn.SkinProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.common.config.ConfigHandler;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.EntityEquipmentData;
import riskyken.armourersWorkshop.common.skin.SkinExtractor;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;
import riskyken.armourersWorkshop.utils.SkinIOUtils;

import java.io.File;

public class CommonProxy {


    public void preInit(File configDir) {
        SkinTypeRegistry.init();
        ConfigHandler.init(new File(configDir, "common.cfg"));
        ConfigHandlerClient.init(new File(configDir, "client.cfg"));
        SkinIOUtils.makeLibraryDirectory();
        SkinExtractor.extractSkins();
        SkinTypeRegistry.init();
        CubeRegistry.init();
    }

    public EntityPlayer getLocalPlayer() {
        throw new IllegalArgumentException();
    }

    public SkinProvider getSkinProvider() {
        throw new UnsupportedOperationException();
    }

    public void initRenderers() {
    }

    public void init() {
        initRenderers();
    }

    public void postInit(){
    }

    public void registerKeyBindings() {

    }

    public void addEquipmentData(PlayerPointer playerPointer, EntityEquipmentData equipmentData) {

    }

    public int getPlayerModelCacheSize() {
        return 0;
    }

    public void receivedEquipmentData(Skin equipmentData) {

    }

//    public void receivedCommandFromSever(CommandType command) {
//
//    }
//
//    public void receivedAdminPanelCommand(EntityPlayer player, AdminPanelCommand command) {
//        switch (command) {
//            case RECOVER_SKINS:
//                SkinIOUtils.recoverSkins(player);
//                break;
//        }
//    }

    public void receivedEquipmentData(EntityEquipmentData equipmentData, int entityId) {

    }

    public void receivedSkinFromLibrary(String fileName, Skin skin) {

    }

    public int getBlockRenderType(Block block) {
        return 0;
    }


}
