package net.cijhn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.skin.data.Skin;


/**
 * @author ci010
 */
public abstract class AbstractSkinProvider implements SkinProvider {
    private SkinRepository skinStorage;

    public AbstractSkinProvider(SkinRepository skinStorage) {
        this.skinStorage = skinStorage;
    }

    @Override
    public Skin getSkin(SkinIdentity identity) {
        return (Skin) skinStorage.getSkin(identity);
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {
        return getSkin(new SkinIdentity(entity.getUniqueID(), skinType.getRegistryName(), slotIndex));
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
