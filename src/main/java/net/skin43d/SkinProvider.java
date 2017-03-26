package net.skin43d;

import net.minecraft.entity.Entity;
import net.skin43d.impl.client.render.nbake.BakeSkinPart;
import net.skin43d.impl.client.render.nbake.BakedSkin;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.common.skin.data.Skin;


/**
 * @author ci010
 */
public interface SkinProvider {
    BakedSkin getBakedModel(Entity entity, SkinType type);

    BakeSkinPart getBakedModel(Entity entity, SkinType type, SkinPartType partType);

    Skin getSkinInfoForEntity(Entity entity, SkinType skinType);

    ISkinDye getPlayerDyeData(Entity entity, SkinType skinType);
}
