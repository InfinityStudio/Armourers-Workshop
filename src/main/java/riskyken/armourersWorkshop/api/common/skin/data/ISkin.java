package riskyken.armourersWorkshop.api.common.skin.data;

import java.util.List;

import net.skin43d.skin3d.SkinType;

public interface ISkin {
    SkinType getSkinType();

    List<ISkinPart> getSubParts();
}
