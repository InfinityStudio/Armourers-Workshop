package riskyken.armourersWorkshop;

import net.cijhn.EquipmentWardrobeProvider;
import net.cijhn.SkinInfoProvider;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;

/**
 * @author ci010
 */
public abstract class ArmourersWorkshop {
    public static ArmourersWorkshop instance() {
        return ArmourersWorkshopMod.instance;
    }

    public abstract int getTextureSize();

    public abstract int getFileVersion();

    public abstract ISkinTypeRegistry getSkinRegistry();

    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinInfoProvider getSkinProvider();
}
