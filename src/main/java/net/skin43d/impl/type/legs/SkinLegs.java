package net.skin43d.impl.type.legs;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

import java.util.ArrayList;
import java.util.List;

public class SkinLegs extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinLegs() {
        skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinLegsPartLeftLeg(this));
        skinParts.add(new SkinLegsPartRightLeg(this));
        skinParts.add(new SkinLegsPartSkirt(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:legs";
    }
    
    @Override
    public String getName() {
        return "Legs";
    }
    
    @Override
    public int getVanillaArmourSlotId() {
        return 2;
    }
}
