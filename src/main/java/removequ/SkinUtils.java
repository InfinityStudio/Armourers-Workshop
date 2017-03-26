package removequ;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.armourersWorkshop.common.skin.data.Skin;

public final class SkinUtils {
    
    private SkinUtils() {
    }
    
//    public static Skin getSkinDetectSide(ItemStack stack, boolean serverSoftLoad, boolean clientRequestSkin) {
//        SkinPointer skinPointer = SkinNBTHelper.getSkinPointerFromStack(stack);
//        if (skinPointer != null) {
//            return getSkinDetectSide(skinPointer, serverSoftLoad, clientRequestSkin);
//        }
//        return null;
//    }
//
//    public static Skin getSkinDetectSide(SkinPointer skinPointer, boolean serverSoftLoad, boolean clientRequestSkin) {
//        if (ArmourersWorkshopMod.isDedicated()) {
//            return getSkinForSide(skinPointer, Side.SERVER, serverSoftLoad, clientRequestSkin);
//        } else {
//            Side side = FMLCommonHandler.instance().getEffectiveSide();
//            return getSkinForSide(skinPointer, side, serverSoftLoad, clientRequestSkin);
//        }
//    }
//
//    public static Skin getSkinForSide(SkinPointer skinPointer, Side side, boolean softLoad, boolean requestSkin) {
//        if (side == Side.CLIENT) {
//            return getSkinOnClient(skinPointer, requestSkin);
//        } else {
//            return getSkinOnServer(skinPointer, softLoad);
//        }
//    }
//
//    private static Skin getSkinOnServer(SkinPointer skinPointer, boolean softLoad) {
//        if (softLoad) {
//            return CommonSkinCache.INSTANCE.softGetSkin(skinPointer.getSkinId());
//        } else {
//            return CommonSkinCache.INSTANCE.getEquipmentData(skinPointer.getSkinId());
//        }
//    }
//
//    @SideOnly(Side.CLIENT)
//    private static Skin getSkinOnClient(SkinPointer skinPointer, boolean requestSkin) {
//        return ClientSkinCache.INSTANCE.getSkin(skinPointer, requestSkin);
//    }
}
