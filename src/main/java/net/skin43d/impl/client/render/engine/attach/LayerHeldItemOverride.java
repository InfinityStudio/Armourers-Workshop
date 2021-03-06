package net.skin43d.impl.client.render.engine.attach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHandSide;
import net.skin43d.impl.Skin43D;
import net.skin43d.impl.skin.Skin;

/**
 * @author ci010
 */
public class LayerHeldItemOverride implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;
    private RenderEngineAttach attach;

    public LayerHeldItemOverride(RenderLivingBase<?> livingEntityRendererIn, RenderEngineAttach engineAttach) {
        this.livingEntityRenderer = livingEntityRendererIn;
        this.attach = engineAttach;
    }

    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = entity.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack offHandItem = flag ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
        ItemStack mainHandItem = flag ? entity.getHeldItemMainhand() : entity.getHeldItemOffhand();

        if (offHandItem != null || mainHandItem != null) {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild) {
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderHeldItem(entity, mainHandItem, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT
                    , limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            this.renderHeldItem(entity, offHandItem, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT
                    , limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.popMatrix();
        }
    }

    private void renderHeldItem(EntityLivingBase entity, ItemStack itemStack, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (itemStack != null) {
            GlStateManager.pushMatrix();

            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            ((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            if (itemStack.getItem() instanceof ItemSword) {
                Skin skin = Skin43D.instance().getSkinProvider().getSkinInfoForEntity(entity,
                        Skin43D.instance().getSkinRegistry().getSkinSword());
                if (skin != null) {
                    attach.sword.npcEquipmentData = skin;
                    GlStateManager.rotate(180, 1, 0, 1F);
                    GlStateManager.rotate(90, 0, 1, 0);
                    attach.sword.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                } else
                    Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, p_188358_3_, flag);
            } else if (itemStack.getItem() instanceof ItemBow) {
                Skin skin = Skin43D.instance().getSkinProvider().getSkinInfoForEntity(entity,
                        Skin43D.instance().getSkinRegistry().getSkinBow());
                if (skin != null) {
                    attach.bow.npcEquipmentData = skin;
                    GlStateManager.rotate(180, 1, 0, 1F);
                    GlStateManager.rotate(90, 0, 1, 0);
                    attach.bow.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                } else
                    Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, p_188358_3_, flag);
            } else if (itemStack.getItem() instanceof ItemShield) {
                Skin skin = Skin43D.instance().getSkinProvider().getSkinInfoForEntity(entity,
                        Skin43D.instance().getSkinRegistry().getSkinShield());
                if (skin != null) {
                    attach.sword.npcEquipmentData = skin;
                    GlStateManager.rotate(180, 1, 0, 1F);
                    GlStateManager.rotate(90, 0, 1, 0);
                    attach.sword.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                } else
                    Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, p_188358_3_, flag);
            } else
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, p_188358_3_, flag);

            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
