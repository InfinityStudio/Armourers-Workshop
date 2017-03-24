package riskyken.armourersWorkshop.common.skin.cubes;

import net.minecraft.block.Block;

public class CubeGlass extends Cube {

    @Override
    public boolean needsPostRender() {
        return true;
    }
    
//    @Override
//    public Block getMinecraftBlock() {
//        return ModBlocks.colourableGlass;
//    }
}
