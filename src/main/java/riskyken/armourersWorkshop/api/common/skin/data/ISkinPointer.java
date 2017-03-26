package riskyken.armourersWorkshop.api.common.skin.data;

import net.skin43d.skin3d.SkinType;

public interface ISkinPointer {
    int getSkinId();

    SkinType getSkinType();

    ISkinDye getSkinDye();
}
