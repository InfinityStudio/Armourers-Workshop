package riskyken.armourersWorkshop.api.common.skin;

import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;


public interface IEntityEquipment {
    void addEquipment(SkinType skinType, int slotIndex, ISkinPointer skinPointer);
    
    void removeEquipment(SkinType skinType, int slotIndex);
    
    boolean haveEquipment(SkinType skinType, int slotIndex);
    
    int getEquipmentId(SkinType skinType, int slotIndex);
    
    ISkinPointer getSkinPointer(SkinType skinType, int slotIndex);
    
    ISkinDye getSkinDye(SkinType skinType, int slotIndex);
    
    int getNumberOfSlots();
}
