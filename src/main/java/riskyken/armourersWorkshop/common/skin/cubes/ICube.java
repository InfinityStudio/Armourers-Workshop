package riskyken.armourersWorkshop.common.skin.cubes;

import net.minecraft.block.Block;

public interface ICube {
    /**
     * Will this cube glow in the dark?
     */
    boolean isGlowing();

    /**
     * Should this cube be rendered after the world?
     */
    boolean needsPostRender();

    /**
     * Get the cubes ID
     */
    byte getId();

    Block getMinecraftBlock();
}
