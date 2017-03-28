package net.skin43d.impl.cubes;

import net.minecraft.block.Block;
import net.skin43d.impl.Skin43D;

public class Cube implements ICube {

    protected final byte id;

    public Cube(byte id) {
        this.id = id;
    }

    public Cube() {
        id = Skin43D.instance().getCubeRegistry().getTotalCubes();
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean needsPostRender() {
        return false;
    }

    @Override
    public byte getId() {
        return id;
    }

    @Override
    public Block getMinecraftBlock() {
        return null;
    }

//    @Override
//    public Block getMinecraftBlock() {
//        return ModBlocks.colourable;
//    }
}
