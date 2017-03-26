package riskyken.armourersWorkshop.common.skin.type.bow;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.client.lib.LibItemResources;
import riskyken.armourersWorkshop.common.skin.type.AbstractSkinTypeBase;

public class SkinBow extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinBow() {
        this.skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinBowPartBase(this));
        skinParts.add(new SkinBowPartFrame1(this));
        skinParts.add(new SkinBowPartFrame2(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }

    @Override
    public String getRegistryName() {
        return "armourers:bow";
    }

    @Override
    public String getName() {
        return "bow";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcon(IIconRegister register) {
        this.icon = register.registerIcon(LibItemResources.TEMPLATE_BOW);
        this.emptySlotIcon = register.registerIcon(LibItemResources.SLOT_SKIN_BOW);
    }
}
