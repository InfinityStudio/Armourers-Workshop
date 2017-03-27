package net.skin43d.impl.client.render.bakery;

import net.minecraft.client.renderer.Tessellator;

public class BakedFaceRenderer {

    private static final float SCALE = 0.0625F;

    //0 = west
    //1 = east
    //2 = up
    //3 = down
    //4 = north
    //5 = south

    //Bottom face   (0.0, 1.0, 0.0)
    //Top face      (0.0, -1.0, 0.0)
    //North face    (0.0, 0.0, -1.0)
    //South face    (0.0, 0.0, 1.0)
    //West face     (1.0, 0.0, 0.0)
    //East face     (-1.0, 0.0, 0.0)

    public static void renderFace(double x, double y, double z, byte r, byte g, byte b, byte a, byte face, boolean textured, byte lodLevel) {
        if (face == 0)
            renderNegYFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
        else if (face == 1)
            renderPosYFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
        else if (face == 2)
            renderNegZFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
        else if (face == 3)
            renderPosZFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
        else if (face == 4)
            renderNegXFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
        else if (face == 5)
            renderPosXFace(x, y, z, r, g, b, a, textured, SCALE * lodLevel);
    }

    public static void renderPosXFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE, y * SCALE, z * SCALE + scale, 0, 0, -1, 0, 0, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE + scale, 0, 1, -1, 0, 0, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE, 1, 1, -1, 0, 0, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE, z * SCALE, 1, 0, -1, 0, 0, r, g, b, a, textured);
    }

    public static void renderNegXFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE, 0, 0, 1F, 0F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE, 0, 1, 1F, 0F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE + scale, 1, 1, 1F, 0F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE + scale, 1, 0, 1F, 0F, 0F, r, g, b, a, textured);
    }

    public static void renderPosYFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE, y * SCALE, z * SCALE + scale, 0, 0, 0F, -1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE, z * SCALE, 0, 1, 0F, -1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE, 1, 1, 0F, -1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE + scale, 1, 0, 0F, -1F, 0F, r, g, b, a, textured);
    }

    public static void renderNegYFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE + scale, 1, 1, 0F, 1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE, 1, 0, 0F, 1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE, 0, 0, 0F, 1F, 0F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE + scale, 0, 1, 0F, 1F, 0F, r, g, b, a, textured);
    }

    public static void renderPosZFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE + scale, 0, 0, 0F, 0F, 1F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE + scale, 0, 1, 0F, 0F, 1F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE + scale, 1, 1, 0F, 0F, 1F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE, z * SCALE + scale, 1, 0, 0F, 0F, 1F, r, g, b, a, textured);
    }

    public static void renderNegZFace(double x, double y, double z, byte r, byte g, byte b, byte a, boolean textured, float scale) {
        addVertex(x * SCALE, y * SCALE, z * SCALE, 0, 0, 0F, 0F, -1F, r, g, b, a, textured);
        addVertex(x * SCALE, y * SCALE + scale, z * SCALE, 0, 1, 0F, 0F, -1F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE + scale, z * SCALE, 1, 1, 0F, 0F, -1F, r, g, b, a, textured);
        addVertex(x * SCALE + scale, y * SCALE, z * SCALE, 1, 0, 0F, 0F, -1F, r, g, b, a, textured);
    }

    private static void addVertex(double x, double y, double z, double u, double v,
                                  float nx, float ny, float nz, int r, int g, int b, int a, boolean texture) {
        if (texture)
            Tessellator.getInstance().getBuffer().pos(x, y, z).tex(u, v).color(r, g, b, a).normal(nx, ny, nz).endVertex();
        else Tessellator.getInstance().getBuffer().pos(x, y, z).color(r, g, b, a).normal(nx, ny, nz).endVertex();
    }
}
