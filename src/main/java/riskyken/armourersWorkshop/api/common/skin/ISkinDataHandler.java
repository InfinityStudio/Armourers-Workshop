package riskyken.armourersWorkshop.api.common.skin;

import java.io.InputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.skin43d.skin3d.SkinType;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;

public interface ISkinDataHandler {
    
    public boolean setSkinOnPlayer(EntityPlayer player, ItemStack stack);
    
    public ItemStack getSkinFormPlayer(EntityPlayer player, SkinType skinType);
    
    public void removeSkinFromPlayer(EntityPlayer player, SkinType skinType);
    
    public boolean isValidEquipmentSkin(ItemStack stack);
    
    public boolean stackHasSkinPointer(ItemStack stack);
    
    public ISkinPointer getSkinPointerFromStack(ItemStack stack);
    
    public void saveSkinPointerOnStack(ISkinPointer skinPointer, ItemStack stack);
    
    public boolean compoundHasSkinPointer(NBTTagCompound compound);
    
    public ISkinPointer readSkinPointerFromCompound(NBTTagCompound compound);
    
    public void writeSkinPointerToCompound(ISkinPointer skinPointer, NBTTagCompound compound);
    
    public ISkinPointer addSkinToCache(InputStream inputStream);
    
    /**
     * Checks if the armour render has been overridden for this slot.
     * @param player
     * @param slotId
     * @return
     */
    public boolean isArmourRenderOverridden(EntityPlayer player, int slotId);
    
    public void setItemAsSkinnable(Item item);
}
