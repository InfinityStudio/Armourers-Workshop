package net.skin43d.impl.type.arrow;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinArrow extends AbstractSkinTypeBase {
    
    public final SkinPartType partBase;
    private ArrayList<SkinPartType> skinParts;
    
    public SkinArrow() {
        this.skinParts = new ArrayList<SkinPartType>();
        this.partBase = new SkinArrowPartBase(this);
        this.skinParts.add(this.partBase);
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:arrow";
    }

    @Override
    public String getName() {
        return "arrow";
    }

    @Override
    public boolean showHelperCheckbox() {
        return true;
    }

}
