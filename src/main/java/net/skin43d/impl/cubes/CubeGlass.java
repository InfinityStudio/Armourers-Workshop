package net.skin43d.impl.cubes;

public class CubeGlass extends Cube {
    public CubeGlass(byte id) {
        super(id);
    }

    public CubeGlass() {
    }

    @Override
    public boolean needsPostRender() {
        return true;
    }
}
