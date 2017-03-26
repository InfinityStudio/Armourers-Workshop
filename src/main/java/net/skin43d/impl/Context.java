package net.skin43d.impl;

import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinInfoProvider;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;

import java.util.Map;

/**
 * @author ci010
 */
public abstract class Context {
    public static Context instance() {
        return ModSkin43D.getProxy();
    }

    public abstract int getTextureWidth();

    public abstract int getTextureHeight();

    public abstract int getTextureSize();

    public abstract int getFileVersion();

    public abstract ISkinTypeRegistry getSkinRegistry();

    public abstract EquipmentWardrobeProvider getEquipmentWardrobeProvider();

    public abstract SkinInfoProvider getSkinProvider();
}
