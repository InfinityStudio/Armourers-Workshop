package net.skin43d.impl.type.sword;

import java.util.ArrayList;
import java.util.List;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.impl.type.AbstractSkinTypeBase;

public class SkinSword extends AbstractSkinTypeBase {

    private ArrayList<SkinPartType> skinParts;
    
    public SkinSword() {
        this.skinParts = new ArrayList<SkinPartType>();
        skinParts.add(new SkinSwordPartBase(this));
    }
    
    @Override
    public List<SkinPartType> getSkinParts() {
        return this.skinParts;
    }
    
    @Override
    public String getRegistryName() {
        return "armourers:sword";
    }
    
    @Override
    public String getName() {
        return "Sword";
    }
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void registerIcon(IIconRegister register) {
//        this.icon = register.registerIcon(LibItemResources.TEMPLATE_WEAPON);
//        this.emptySlotIcon = register.registerIcon(LibItemResources.SLOT_SKIN_SWORD);
//    }
}
