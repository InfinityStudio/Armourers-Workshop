package net.cijhn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.api.common.skin.IEntityEquipment;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public interface SkinProvider {
    Skin getSkin(ISkinPointer skinPointer);

    Skin getSkin(Entity entity, ISkinType skinType, int slotIndex);

    ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex);

    byte[] getPlayerExtraColours(Entity entity);
}
