package net.cijhn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.api.common.skin.IEntityEquipment;
import riskyken.armourersWorkshop.common.data.PlayerPointer;

/**
 * @author ci010
 */
public interface PlayerEquipmentDataProvider {
    IEntityEquipment getPlayerCustomEquipmentData(Entity entity);

    int getSkinDataMapSize();

    void addEquipmentData(PlayerPointer playerPointer, IEntityEquipment equipmentData);

    void removeEquipmentData(PlayerPointer playerPointer);

    boolean playerHasCustomHead(EntityPlayer player);
}
