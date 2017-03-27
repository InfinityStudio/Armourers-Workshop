package riskyken.armourersWorkshop.client.render.core;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.world.World;

public final class ModRenderHelper {
    private static float lightX;
    private static float lightY;

    @SideOnly(Side.CLIENT)
    public static void disableLighting() {
        lightX = OpenGlHelper.lastBrightnessX;
        lightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    }

    @SideOnly(Side.CLIENT)
    public static void enableLighting() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightX, lightY);
    }

    @SideOnly(Side.CLIENT)
    public static void setLightingForBlock(World world, int x, int y, int z) {
//        int i = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
//        int j = i % 65536;
//        int k = i / 65536;
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
    }

    public static void enableAlphaBlend() {
        enableAlphaBlend(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void enableAlphaBlend(int sfactor, int dfactor) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(sfactor, dfactor);
    }

    public static void disableAlphaBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

//    public static void renderItemStack(ItemStack stack) {
//        IIcon icon = stack.getItem().getIcon(stack, 0);
//        ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
//    }
}
