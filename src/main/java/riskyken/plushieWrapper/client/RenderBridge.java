package riskyken.plushieWrapper.client;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBridge implements IRenderBuffer {

    public static IRenderBuffer INSTANCE;

    Tessellator tessellator;

    public static void init() {
        INSTANCE = new RenderBridge();
    }

    public RenderBridge() {
        tessellator = Tessellator.getInstance();
    }

    @Override
    public void draw() {
        tessellator.draw();
    }

    @Override
    public void startDrawingQuads() {
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
    }

    @Override
    public void setColourRGBA_B(byte r, byte g, byte b, byte a) {
        tessellator.getBuffer().color(r, g, b, a);
    }

    @Override
    public void setNormal(float x, float y, float z) {
        tessellator.getBuffer().normal(x, y, z);
    }

    @Override
    public void addVertex(double x, double y, double z) {
        tessellator.getBuffer().pos(x, y, z);
    }

    @Override
    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        tessellator.getBuffer().pos(x, y, z).tex(u, v);
    }
}
