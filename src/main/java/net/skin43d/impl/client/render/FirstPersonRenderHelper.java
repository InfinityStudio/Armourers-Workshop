package net.skin43d.impl.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.skin43d.impl.client.model.IEquipmentModel;
import net.skin43d.impl.skin.Skin;

/**
 * @author ci010
 */
public class FirstPersonRenderHelper {
    private static void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_) {
        int i = hand == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.translate((float) i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
    }

    private static void transformEatFirstPerson(float p_187454_1_, EnumHandSide p_187454_2_, ItemStack p_187454_3_) {
        float f = (float) Minecraft.getMinecraft().thePlayer.getItemInUseCount() - p_187454_1_ + 1.0F;
        float f1 = f / (float) p_187454_3_.getMaxItemUseDuration();

        if (f1 < 0.8F) {
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);
            GlStateManager.translate(0.0F, f2, 0.0F);
        }

        float f3 = 1.0F - (float) Math.pow((double) f1, 27.0D);
        int i = p_187454_2_ == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.translate(f3 * 0.6F * (float) i, f3 * -0.5F, f3 * 0.0F);
        GlStateManager.rotate((float) i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    private static void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_) {
        int i = p_187453_1_ == EnumHandSide.RIGHT ? 1 : -1;
        float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float) Math.PI);
        GlStateManager.rotate((float) i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(p_187453_2_) * (float) Math.PI);
        GlStateManager.rotate((float) i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) i * -45.0F, 0.0F, 1.0F, 0.0F);
    }

    public static void render(Skin skin, IEquipmentModel model, boolean showSkinPaint, boolean doLodLoading,
                              RenderSpecificHandEvent event) {
        GlStateManager.pushMatrix();
        boolean flag = event.getHand() == EnumHand.MAIN_HAND;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
        float partialTicks = event.getPartialTicks();
        float swingProgress = event.getSwingProgress();
        float equipProgress = event.getEquipProgress();
        ItemStack itemStack = event.getItemStack();
        if (itemStack == null) return;
        boolean flag1 = enumhandside == EnumHandSide.RIGHT;
        if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == event.getHand()) {
            int j = flag1 ? 1 : -1;
            switch (itemStack.getItemUseAction()) {
                case NONE:
                    transformSideFirstPerson(enumhandside, equipProgress);
                    break;
                case EAT:
                case DRINK:
                    transformEatFirstPerson(partialTicks, enumhandside, itemStack);
                    transformSideFirstPerson(enumhandside, equipProgress);
                    break;
                case BLOCK:
                    transformSideFirstPerson(enumhandside, equipProgress);
                    break;
                case BOW:
                    transformSideFirstPerson(enumhandside, equipProgress);
                    GlStateManager.translate((float) j * -0.2785682F, 0.18344387F, 0.15731531F);
                    GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate((float) j * 35.3F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate((float) j * -9.785F, 0.0F, 0.0F, 1.0F);
                    float f5 = (float) itemStack.getMaxItemUseDuration() - ((float) player.getItemInUseCount() - partialTicks + 1.0F);
                    float f6 = f5 / 20.0F;
                    f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;

                    if (f6 > 1.0F) {
                        f6 = 1.0F;
                    }

                    if (f6 > 0.1F) {
                        float f7 = MathHelper.sin((f5 - 0.1F) * 1.3F);
                        float f3 = f6 - 0.1F;
                        float f4 = f7 * f3;
                        GlStateManager.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                    }

                    GlStateManager.translate(f6 * 0.0F, f6 * 0.0F, f6 * 0.04F);
                    GlStateManager.scale(1.0F, 1.0F, 1.0F + f6 * 0.2F);
                    GlStateManager.rotate((float) j * 45.0F, 0.0F, -1.0F, 0.0F);
            }
        } else {
            float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
            float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * ((float) Math.PI * 2F));
            float f2 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
            int i = flag1 ? 1 : -1;
            GlStateManager.translate((float) i * f, f1, f2);
            transformSideFirstPerson(enumhandside, equipProgress);
            transformFirstPerson(enumhandside, swingProgress);
        }

        GlStateManager.rotate(180, 1, 0, 1F);
        GlStateManager.rotate(90, 0, 1, 0);

////        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
//        ModRenderHelper.enableAlphaBlend();
//        GL11.glEnable(GL11.GL_CULL_FACE);
        model.render(null, null, skin, showSkinPaint, null, null, true, 0, doLodLoading);
//        GL11.glPopAttrib();
//        GL11.glPopMatrix();
        GlStateManager.popMatrix();
    }
}
