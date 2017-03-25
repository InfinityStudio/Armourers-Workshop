package net.cijhn;

import com.google.common.util.concurrent.ListenableFuture;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public interface SkinRequester {
    void requestSkinFromServer(ISkinPointer skinPointer);

    void requestSkinFromServer(int skinId);
}
