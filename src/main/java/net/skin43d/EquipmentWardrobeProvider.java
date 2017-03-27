package net.skin43d;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.EquipmentWardrobeData;

/**
 * @author ci010
 */
public interface EquipmentWardrobeProvider {
    void setEquipmentWardrobeData(EntityPlayer playerPointer, EquipmentWardrobeData ewd);

    EquipmentWardrobeData getEquipmentWardrobeData(EntityPlayer playerPointer);

    void removeEquipmentWardrobeData(EntityPlayer playerPointer);
}
