package net.cijhn;

import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;

/**
 * @author ci010
 */
public interface SkinRequester {
    void requestSkinFromServer(ISkinPointer skinPointer);

    void requestSkinFromServer(int skinId);
}
