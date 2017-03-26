package riskyken.armourersWorkshop.common.skin.cubes;

import net.minecraft.block.Block;
import net.skin43d.impl.Context;

public class Cube implements ICube {

    protected final byte id;

    public Cube() {
        id = Context.instance().getCubeRegistry().getTotalCubes();
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
