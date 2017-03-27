package riskyken.armourersWorkshop.common.skin.type.legs;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinTypeBase;

public class SkinSkirt extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinSkirt() {
        skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinSkirtPartBase(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:skirt";
    }
    
    @Override
    public String getName() {
        return "Skirt";
    }
    

    @Override
    public int getVanillaArmourSlotId() {
        return 2;
    }
}
