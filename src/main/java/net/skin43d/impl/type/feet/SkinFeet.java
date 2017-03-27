package net.skin43d.impl.type.feet;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinFeet extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinFeet() {
        skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinFeetPartLeftFoot(this));
        skinParts.add(new SkinFeetPartRightFoot(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:feet";
    }
    
    @Override
    public String getName() {
        return "Feet";
    }
    

    @Override
    public int getVanillaArmourSlotId() {
        return 3;
    }
}
