package riskyken.armourersWorkshop.common;

import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinInfoProvider;
import net.skin43d.SkinRepository;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;

import java.io.File;

public class CommonProxy {
    public void preInit(File configDir) {
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
