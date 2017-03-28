package net.skin43d.skin3d;

import java.util.List;

/**
 * Skin type registry is used to register new SkinType's
 * and get register SkinType's and Part's.
 *
 * @author RiskyKen
 */
public interface SkinTypeRegistry {

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

    SkinType getSkinShield();

    SkinType getSkinArrow();

    SkinType getSkinBlock();
}
