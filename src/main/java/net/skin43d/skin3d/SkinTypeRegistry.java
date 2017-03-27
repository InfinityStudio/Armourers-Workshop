package net.skin43d.skin3d;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;

import java.util.List;

/**
 * Skin type registry is used to register new SkinType's
 * and get register SkinType's and Part's.
 *
 * @author RiskyKen
 */
public interface SkinTypeRegistry {

//    /**
//     * Register a new skin type.
//     *
//     * @param skinType
//     */
//    boolean registerSkin(SkinType skinType);

    SkinType getSkinTypeFromLegacyId(int id);

    SkinType getSkinTypeFromRegistryName(String registryName);

    SkinPartType getSkinPartTypeFromId(int id);

    SkinPartType getSkinPartTypeFromName(String regName);

    List<SkinType> getAllSkinTypes();

    SkinType getSkinWings();

    @Deprecated
    SkinType getSkinTypeSkirt();

    SkinType getSkinHead();

    SkinType getSkinChest();

    SkinType getSkinLegs();

    SkinType getSkinSkirt();

    SkinType getSkinFeet();

    SkinType getSkinSword();

    SkinType getSkinBow();

    SkinType getSkinArrow();

    SkinType getSkinBlock();
}