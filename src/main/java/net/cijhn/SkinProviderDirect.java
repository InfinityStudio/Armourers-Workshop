package net.cijhn;

import net.minecraft.entity.Entity;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.skin.data.Skin;

/**
 * @author ci010
 */
public class SkinProviderDirect implements SkinProvider {
    @Override
    public Skin getSkin(ISkinPointer skinPointer) {
        return ClientSkinCache.INSTANCE.getSkin(skinPointer);
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {

        return null;
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        return null;
    }

    @Override
    public byte[] getPlayerExtraColours(Entity entity) {
        return new byte[0];
    }
}
