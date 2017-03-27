package net.skin43d.skin3d;

import java.util.List;


/**
 * @author RiskyKen
 */
public interface SkinType {

    List<SkinPartType> getSkinParts();

    /**
     * Gets the name this skin will be registered with.
     * Armourer's Workshop uses the format armourers:skinName.
     * Example armourers:head is the registry name of
     * Armourer's Workshop head armour skin.
     *
     * @return registryName
     */
    String getRegistryName();

    /**
     * This only exists for backwards compatibility with old world saves.
     * Just return getRegistryName().
     *
     * @return name
     */
    String getName();


//    @SideOnly(Side.CLIENT)
//    void registerIcon(IIconRegister register);

//    @SideOnly(Side.CLIENT)
//    IIcon getIcon();
//
//    @SideOnly(Side.CLIENT)
//    IIcon getEmptySlotIcon();

    /**
     * Should the show skin overlay check box be shown in the armourer and mini armourer.
     *
     * @return
     */
    boolean showSkinOverlayCheckbox();

    /**
     * Should the helper check box be shown in the armourer and mini armourer.
     *
     * @return
     */
    boolean showHelperCheckbox();

    /**
     * If this skin is for vanilla armour return the slot id here, otherwise return -1.
     *
     * @return slotId
     */
    int getVanillaArmourSlotId();

    /**
     * Should this skin be hidden from the user?
     *
     * @return Is hidden?
     */
    boolean isHidden();

    /**
     * Is this skin enabled?
     *
     * @return Is enabled?
     */
    boolean enabled();
}
