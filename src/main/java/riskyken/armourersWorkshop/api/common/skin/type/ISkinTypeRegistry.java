package riskyken.armourersWorkshop.api.common.skin.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Skin type registry is used to register new ISkinType's
 * and get register ISkinType's and ISkinPart's.
 *
 * @author RiskyKen
 */
public interface ISkinTypeRegistry {

    /**
     * Register a new skin type.
     *
     * @param skinType
     */
    boolean registerSkin(ISkinType skinType);

    ISkinType getSkinTypeFromLegacyId(int id);

    ISkinType getSkinTypeFromRegistryName(String registryName);

    ISkinPartType getSkinPartTypeFromId(int id);

    ISkinPartType getSkinPartTypeFromName(String regName);

    List<ISkinType> getRegisteredSkinTypes();

    ISkinType getSkinTypeHead();

    ISkinType getSkinTypeChest();

    ISkinType getSkinTypeLegs();

    @Deprecated
    ISkinType getSkinTypeSkirt();

    ISkinType getSkinTypeFeet();
}
