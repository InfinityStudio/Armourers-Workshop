package net.skin43d.impl.client.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.skin.Skin;

@SideOnly(Side.CLIENT)
public interface IEquipmentModel {
    
    void render(Entity entity, Skin armourData, float limb1, float limb2, float limb3, float headY, float headX);
    
    void render(Entity entity, ModelBiped modelBiped, Skin armourData, boolean showSkinPaint, ISkinDye skinDye, byte[] extraColour, boolean itemRender, double distance, boolean doLodLoading);
}
