package riskyken.armourersWorkshop.common.skin.cubes;

import net.minecraft.block.Block;

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
    
//    @Override
//    public Block getMinecraftBlock() {
//        return ModBlocks.colourableGlassGlowing;
//    }
}
