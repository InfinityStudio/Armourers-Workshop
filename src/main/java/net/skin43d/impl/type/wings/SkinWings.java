package net.skin43d.impl.type.wings;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

import java.util.ArrayList;
import java.util.List;

public class SkinWings extends AbstractSkinTypeBase {
    
    private ArrayList<SkinPartType> skinParts;
    
    public SkinWings() {
        skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinWingsPartLeftWing(this));
        skinParts.add(new SkinWingsPartRightWing(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:wings";
    }
    
    @Override
    public String getName() {
        return "wings";
    }
    
}
