package riskyken.armourersWorkshop.common.skin.type.arrow;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinTypeBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcon(IIconRegister register) {
        this.icon = register.registerIcon(LibItemResources.TEMPLATE_ARROW);
        this.emptySlotIcon = register.registerIcon(LibItemResources.SLOT_SKIN_ARROW);
    }
}
