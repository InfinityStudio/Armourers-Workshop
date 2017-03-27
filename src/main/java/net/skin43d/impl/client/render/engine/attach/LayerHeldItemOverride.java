package net.skin43d.impl.client.render.engine.attach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.engine.core.ModelSkinSword;
import net.skin43d.impl.skin.Skin;

/**
 * @author ci010
 */
public class LayerHeldItemOverride implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;
    private RenderEngineAttach attach;
    private ModelSkinSword sword = new ModelSkinSword();

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
                float f = 0.5F;
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

    private void renderHeldItem(EntityLivingBase entity, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide
            , float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (p_188358_2_ != null) {
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
            Skin skin = Context.instance().getSkinProvider().getSkinInfoForEntity(entity,
                    Context.instance().getSkinRegistry().getSkinSword());
            if (skin != null) {
                sword.npcEquipmentData = skin;
                sword.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            } else {
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, p_188358_2_, p_188358_3_, flag);
            }
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
