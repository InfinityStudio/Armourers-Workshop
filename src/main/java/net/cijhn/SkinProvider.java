package net.cijhn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.skin.data.Skin;


/**
 * @author ci010
 */
public interface SkinProvider {
    void requestSkin(EntityPlayer player);

    Skin getSkin(SkinIdentity identity);

    Skin getSkin(Entity entity, ISkinType skinType, int slotIndex);

    ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex);

    byte[] getPlayerExtraColours(Entity entity);
}
