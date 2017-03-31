package net.skin43d.impl.cubes;

public class CubeGlassGlowing extends CubeGlass {
    public CubeGlassGlowing(byte id) {
        super(id);
    }

    public CubeGlassGlowing() {
    }

    @Override
    public boolean isGlowing() {
        return true;
    }
}
