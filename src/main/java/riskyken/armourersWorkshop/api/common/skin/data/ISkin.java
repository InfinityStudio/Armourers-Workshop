package riskyken.armourersWorkshop.api.common.skin.data;

import java.util.ArrayList;

import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;

public interface ISkin {
    ISkinType getSkinType();

    ArrayList<ISkinPart> getSubParts();
}
