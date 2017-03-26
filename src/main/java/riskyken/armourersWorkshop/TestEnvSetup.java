package riskyken.armourersWorkshop;

import net.skin43d.SkinInfo;
import net.skin43d.SkinInfoProvider;
import net.minecraft.entity.Entity;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import net.skin43d.utils.SkinIOUtils;

import java.io.File;
import java.util.Collections;
import java.util.UUID;

/**
 * @author ci010
 */
public class TestEnvSetup implements SkinInfoProvider {
    private static Skin skin;
    public static final Object KEY = new Object();

    //
    private static String loc = "D:\\Storage\\Desktop\\Angel Wings.armour";

    static void setup() {
        File file = new File(loc);
        skin = SkinIOUtils.loadSkinFromFile(file);
        System.out.println(skin.getSkinType().getRegistryName());
        System.out.println(skin);
        ConfigHandlerClient.skinRenderType = 2;
        ArmourersWorkshopMod.proxy.getSkinRepository().registerSkin(KEY, skin);
    }

    @Override
    public SkinInfo getSkinInfoForEntity(Entity entity) {
        return new SkinInfo(UUID.randomUUID(), Collections.singletonMap("armourers:wings", skin));
    }

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType) {
        return null;
    }

    @Override
    public Skin getSkinInfoForEntity(Entity entity, SkinType skinType, int slotIndex) {
        return skin;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, SkinType skinType, int slotIndex) {
        return null;
    }
}
