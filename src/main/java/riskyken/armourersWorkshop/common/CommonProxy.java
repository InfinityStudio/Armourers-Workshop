package riskyken.armourersWorkshop.common;

import net.cijhn.EquipmentWardrobeProvider;
import net.cijhn.SkinInfoProvider;
import net.cijhn.SkinRepository;
import net.minecraft.block.Block;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.EntityEquipmentData;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;

import java.io.File;

public class CommonProxy {
    public void preInit(File configDir) {
        SkinTypeRegistry.init();
        CubeRegistry.init();
//        ConfigHandler.init(new File(configDir, "common.cfg"));
//        ConfigHandlerClient.init(new File(configDir, "client.cfg"));
    }

    public SkinInfoProvider getSkinProvider() {
        throw new UnsupportedOperationException();
    }

    public SkinRepository getSkinRepository() {
        throw new UnsupportedOperationException();
    }

    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        throw new UnsupportedOperationException();
    }

    public void init() {
    }

    public void postInit() {
    }
}
