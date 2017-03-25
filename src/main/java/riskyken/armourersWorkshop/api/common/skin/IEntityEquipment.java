package riskyken.armourersWorkshop.api.common.skin;

import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;


public interface IEntityEquipment {
    void addEquipment(ISkinType skinType, int slotIndex, ISkinPointer skinPointer);
    
    void removeEquipment(ISkinType skinType, int slotIndex);
    
    boolean haveEquipment(ISkinType skinType, int slotIndex);
    
    int getEquipmentId(ISkinType skinType, int slotIndex);
    
    ISkinPointer getSkinPointer(ISkinType skinType, int slotIndex);
    
    ISkinDye getSkinDye(ISkinType skinType, int slotIndex);
    
    int getNumberOfSlots();
}
