package net.skin43d.impl.type.head;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinHead extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinHead() {
        this.skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinHeadPartBase(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }
    
    @Override
    public String getRegistryName() {
        return "armourers:head";
    }
    
    @Override
    public String getName() {
        return "Head";
    }

    @Override
    public boolean showSkinOverlayCheckbox() {
        return true;
    }

    @Override
    public int getVanillaArmourSlotId() {
        return 0;
    }
}
