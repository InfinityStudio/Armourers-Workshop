package riskyken.armourersWorkshop;

import net.cijhn.SkinIdentity;
import net.cijhn.SkinProvider;
import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.ClientProxy;
import riskyken.armourersWorkshop.client.render.model.bake.ModelBakery;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.utils.SkinIOUtils;

import java.io.File;

/**
 * @author ci010
 */
public class TestEnvSetup implements SkinProvider {
    private static Skin skin;

    //
    private static String loc = "D:\\Storage\\Desktop\\Angel Wings.armour";

    static void setup() {
        File file = new File(loc);
        skin = SkinIOUtils.loadSkinFromFile(file);
        System.out.println(skin);
        ConfigHandlerClient.skinRenderType = 2;
        System.out.println("==============================");
        System.out.println(skin.serverId);
        System.out.println("==============================");
        ModelBakery.INSTANCE.receivedUnbakedModel(skin);
        ClientSkinCache.INSTANCE.addServerIdMap(skin);
    }

    @Override
    public Skin getSkin(SkinIdentity identity) {
        return null;
    }

    @Override
    public Skin getSkin(ISkinPointer skinPointer) {
        return skin;
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {
        return skin;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }

    @Override
    public byte[] getPlayerExtraColours(Entity entity) {
        return new byte[0];
    }
}
