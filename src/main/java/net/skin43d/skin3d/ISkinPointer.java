package net.skin43d.skin3d;

import net.skin43d.skin3d.ISkinDye;
import net.skin43d.skin3d.SkinType;

public interface ISkinPointer {
    int getSkinId();

    SkinType getSkinType();

    ISkinDye getSkinDye();
}
