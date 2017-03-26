package riskyken.armourersWorkshop.api.common.skin.type;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;

import java.util.List;

/**
 * Skin type registry is used to register new SkinType's
 * and get register SkinType's and ISkinPart's.
 *
 * @author RiskyKen
 */
public interface ISkinTypeRegistry {

    /**
     * Register a new skin type.
     *
     * @param skinType
     */
    boolean registerSkin(SkinType skinType);

    SkinType getSkinTypeFromLegacyId(int id);

    SkinType getSkinTypeFromRegistryName(String registryName);

    SkinPartType getSkinPartTypeFromId(int id);

    SkinPartType getSkinPartTypeFromName(String regName);

    List<SkinType> getRegisteredSkinTypes();

    SkinType getSkinTypeHead();

    SkinType getSkinTypeChest();

    SkinType getSkinTypeLegs();

    @Deprecated
    SkinType getSkinTypeSkirt();

    SkinType getSkinTypeFeet();
}
