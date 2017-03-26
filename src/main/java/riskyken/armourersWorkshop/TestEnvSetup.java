package riskyken.armourersWorkshop;

import net.skin43d.SkinInfo;
import net.skin43d.SkinInfoProvider;
import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.utils.SkinIOUtils;

import java.io.File;

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
    public void deploy() {

    }

    @Override
    public SkinInfo getSkin(Entity entity) {
        return SkinInfo.EMPTY;
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {
        return skin;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }
}
