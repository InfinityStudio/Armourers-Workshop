package net.skin43d;

import net.minecraft.entity.Entity;
import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.skin.Skin;

import javax.annotation.Nullable;


/**
 * @author ci010
 */
public interface SkinProvider {
    @Nullable
    Skin getSkinInfoForEntity(Entity entity, SkinType skinType);

    @Nullable
    ISkinDye getPlayerDyeData(Entity entity, SkinType skinType);
}
