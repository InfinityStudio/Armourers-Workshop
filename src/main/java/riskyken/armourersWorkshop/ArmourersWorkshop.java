package riskyken.armourersWorkshop;

import net.cijhn.EquipmentWardrobeProvider;
import net.cijhn.SkinInfoProvider;

/**
 * @author ci010
 */
public abstract class ArmourersWorkshop {
    public static ArmourersWorkshop instance() {
        return ArmourersWorkshopMod.instance;
    }

    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinInfoProvider getSkinProvider();
}
