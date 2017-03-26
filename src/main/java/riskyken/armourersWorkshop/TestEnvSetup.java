package riskyken.armourersWorkshop;

import net.skin43d.utils.SkinIOUtils;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.io.File;

/**
 * @author ci010
 */
public class TestEnvSetup  {
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
//        ArmourersWorkshopMod.proxy.getSkinRepository().cacheSkin(KEY, skin);
    }

}
