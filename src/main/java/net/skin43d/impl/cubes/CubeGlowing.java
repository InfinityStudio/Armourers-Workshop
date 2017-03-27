package net.skin43d.impl.cubes;

public class CubeGlowing extends Cube {
    public CubeGlowing(byte id) {
        super(id);
    }

    public CubeGlowing() {
    }

    @Override
    public boolean isGlowing() {
        return true;
    }
    
//    @Override
//    public Block getMinecraftBlock() {
//        return ModBlocks.colourableGlowing;
//    }
}
