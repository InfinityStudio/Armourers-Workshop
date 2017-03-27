package riskyken.plushieWrapper.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRenderBuffer {
    void draw();

    void startDrawingQuads();

    void setColourRGBA_B(byte r, byte g, byte b, byte a);

    void setNormal(float x, float y, float z);

    void addVertex(double x, double y, double z);

    void addVertexWithUV(double x, double y, double z, double u, double v);
}
