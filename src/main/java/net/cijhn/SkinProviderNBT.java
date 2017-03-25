package net.cijhn;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import riskyken.armourersWorkshop.api.common.skin.IEntityEquipment;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPointer;
import riskyken.armourersWorkshop.utils.SkinNBTHelper;

/**
 * @author ci010
 */
public class SkinProviderNBT implements SkinProvider {
    private PlayerEquipmentDataProvider dataProvider;

    public SkinProviderNBT(PlayerEquipmentDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public Skin getSkin(ISkinPointer skinPointer) {
        return ClientSkinCache.INSTANCE.getSkin(skinPointer);
    }

    @Override
    public Skin getSkin(Entity entity, ISkinType skinType, int slotIndex) {
        if (!(entity instanceof AbstractClientPlayer))
            return null;
        AbstractClientPlayer player = (AbstractClientPlayer) entity;
        IEntityEquipment entityEquipment = dataProvider.getPlayerCustomEquipmentData(player);

        //Look for skinned armourer.
        if (skinType.getVanillaArmourSlotId() >= 0 && skinType.getVanillaArmourSlotId() < 4 && slotIndex == 0) {
            int slot = 3 - skinType.getVanillaArmourSlotId();
            ItemStack armourStack = player.getCurrentArmor(slot);
            if (SkinNBTHelper.stackHasSkinData(armourStack)) {
                SkinPointer sp = SkinNBTHelper.getSkinPointerFromStack(armourStack);
                return getSkin(sp);
            }
        }
        //No skinned armour found checking the equipment wardrobe.
        if (entityEquipment == null) return null;
        if (!entityEquipment.haveEquipment(skinType, slotIndex)) return null;
        return getSkin(entityEquipment.getSkinPointer(skinType, slotIndex));
    }

    @Override
    public ISkinDye getPlayerDyeData(Entity entity, ISkinType skinType, int slotIndex) {
        if (!(entity instanceof AbstractClientPlayer)) {
            return null;
        }
        AbstractClientPlayer player = (AbstractClientPlayer) entity;

        IEntityEquipment equipmentData = dataProvider.getPlayerCustomEquipmentData(player);

        //Look for skinned armourer.
        if (skinType.getVanillaArmourSlotId() >= 0 && skinType.getVanillaArmourSlotId() < 4 && slotIndex == 0) {
            int slot = 3 - skinType.getVanillaArmourSlotId();
            ItemStack armourStack = player.getCurrentArmor(slot);
            if (SkinNBTHelper.stackHasSkinData(armourStack)) {
                SkinPointer sp = SkinNBTHelper.getSkinPointerFromStack(armourStack);
                if (sp != null) return sp.getSkinDye();
            }
        }

        //No skinned armour found checking the equipment wardrobe.
        if (equipmentData == null)
            return null;


        if (!equipmentData.haveEquipment(skinType, slotIndex))
            return null;

        return equipmentData.getSkinDye(skinType, slotIndex);
    }

    @Override
    public byte[] getPlayerExtraColours(Entity entity) {
        return new byte[0];
    }
}
