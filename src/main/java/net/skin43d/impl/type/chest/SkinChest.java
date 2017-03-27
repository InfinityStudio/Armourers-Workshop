package net.skin43d.impl.type.chest;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinChest extends AbstractSkinTypeBase {
    
    private ArrayList<SkinPartType> skinParts;
    
    public SkinChest() {
        skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinChestPartBase(this));
        skinParts.add(new SkinChestPartLeftArm(this));
        skinParts.add(new SkinChestPartRightArm(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:chest";
    }
    
    @Override
    public String getName() {
        return "Chest";
    }
    

    @Override
    public int getVanillaArmourSlotId() {
        return 1;
    }
}
