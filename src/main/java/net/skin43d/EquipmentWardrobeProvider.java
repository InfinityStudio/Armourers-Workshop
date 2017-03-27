package net.skin43d;

import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.EquipmentWardrobeData;

/**
 * @author ci010
 */
public interface EquipmentWardrobeProvider {
    void setEquipmentWardrobeData(PlayerPointer playerPointer, EquipmentWardrobeData ewd);

    EquipmentWardrobeData getEquipmentWardrobeData(PlayerPointer playerPointer);

    void removeEquipmentWardrobeData(PlayerPointer playerPointer);
}
