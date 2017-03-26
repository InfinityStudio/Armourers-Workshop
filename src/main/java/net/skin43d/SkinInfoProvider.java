package net.skin43d;

import net.minecraft.entity.Entity;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.common.skin.data.Skin;


/**
 * @author ci010
 */
public interface SkinInfoProvider {
    SkinInfo getSkinInfoForEntity(Entity entity);

    Skin getSkinInfoForEntity(Entity entity, SkinType skinType);

    Skin getSkinInfoForEntity(Entity entity, SkinType skinType, int slotIndex);

    ISkinDye getPlayerDyeData(Entity entity, SkinType skinType);

    ISkinDye getPlayerDyeData(Entity entity, SkinType skinType, int slotIndex);

//    byte[] getPlayerExtraColours(Entity entity);
}
