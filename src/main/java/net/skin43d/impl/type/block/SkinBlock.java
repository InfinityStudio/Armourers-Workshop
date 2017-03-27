package net.skin43d.impl.type.block;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinBlock extends AbstractSkinTypeBase {

    public final SkinPartType partBase;
    private ArrayList<SkinPartType> skinParts;
    
    public SkinBlock() {
        this.skinParts = new ArrayList<SkinPartType>();
        this.partBase = new SkinBlockPartBase(this);
        this.skinParts.add(this.partBase);
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:block";
    }

    @Override
    public String getName() {
        return "block";
    }

}
