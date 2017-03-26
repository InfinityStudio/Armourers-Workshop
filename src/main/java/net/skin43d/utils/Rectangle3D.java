package net.skin43d.utils;

public class Rectangle3D {

    private final int x;
    private final int y;
    private final int z;
    private final int width;
    private final int height;
    private final int depth;

    public Rectangle3D(int x, int y, int z, int width, int height, int depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDepth() {
        return this.depth;
    }

    @Override
    public String toString() {
        return "Rectangle3D [x=" + x + ", y=" + y + ", z=" + z + ", width="
                + width + ", height=" + height + ", depth=" + depth + "]";
    }
}
