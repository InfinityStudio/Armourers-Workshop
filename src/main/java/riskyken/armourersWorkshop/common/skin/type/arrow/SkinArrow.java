package riskyken.armourersWorkshop.common.skin.type.arrow;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinTypeBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
