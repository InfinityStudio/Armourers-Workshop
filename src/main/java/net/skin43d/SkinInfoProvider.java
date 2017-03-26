package net.skin43d;

import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.skin.data.Skin;


/**
 * @author ci010
 */
public interface SkinInfoProvider {
    void deploy();

    SkinInfo getSkin(Entity entity);

    Skin getSkin(Entity entity, ISkinType skinType, int slotIndex);

    ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex);

//    byte[] getPlayerExtraColours(Entity entity);
}