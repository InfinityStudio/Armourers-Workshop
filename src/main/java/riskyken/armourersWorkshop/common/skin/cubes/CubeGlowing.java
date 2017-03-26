package riskyken.armourersWorkshop.common.skin.cubes;

import net.minecraft.block.Block;

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
