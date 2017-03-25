package riskyken.armourersWorkshop.api.common.skin.data;

import net.minecraftforge.common.util.ForgeDirection;
import riskyken.armourersWorkshop.api.common.skin.Point3D;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinPartType;

public interface ISkinPart {

    ISkinPartType getPartType();
    
    int getMarkerCount();
    
    Point3D getMarker(int index);
    
    ForgeDirection getMarkerSide(int index);
}
