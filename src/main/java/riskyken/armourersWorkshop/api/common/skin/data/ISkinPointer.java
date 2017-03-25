package riskyken.armourersWorkshop.api.common.skin.data;

import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;

public interface ISkinPointer {
    int getSkinId();
    
    ISkinType getSkinType();
    
    ISkinDye getSkinDye();
}
