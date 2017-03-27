package riskyken.armourersWorkshop.common.skin.type.head;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinTypeBase;

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
