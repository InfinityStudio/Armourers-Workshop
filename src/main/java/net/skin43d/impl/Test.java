package net.skin43d.impl;

import com.google.common.util.concurrent.ListeningExecutorService;
import net.minecraft.entity.Entity;
import net.skin43d.impl.client.BakedSkinModel;
import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.SkinIOUtils;
import riskyken.armourersWorkshop.client.render.bake.SkinBakery;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class Test extends SkinProviderBase {
    public Test(SkinBakery bakery, ListeningExecutorService service) {
        super(bakery, service);
    }

    private static String loc = "D:\\Storage\\Desktop\\Angel Wings.armour";
    private static Skin skin;

    public static void setup() {
        File file = new File(loc);
        skin = SkinIOUtils.loadSkinFromFile(file);
    }

    @Override
    public BakedSkinModel getBakedModel(Entity entity, SkinType type) {
        return null;
    }

    @Override
    protected Callable<Skin> requestSkinTask(Entity entity, SkinType skinType) {
        return new Callable<Skin>() {
            @Override
            public Skin call() throws Exception {
                return skin;
            }
        };
    }
}
