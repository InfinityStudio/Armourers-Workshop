package riskyken.armourersWorkshop.api.common.skin.data;

import java.util.List;

import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;

public interface ISkin {
    ISkinType getSkinType();

    List<ISkinPart> getSubParts();
}
