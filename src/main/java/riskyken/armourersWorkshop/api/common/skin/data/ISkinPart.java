package riskyken.armourersWorkshop.api.common.skin.data;

import net.minecraftforge.common.util.ForgeDirection;
import riskyken.armourersWorkshop.api.common.skin.Point3D;
import net.skin43d.skin3d.SkinPartType;

public interface ISkinPart {

    SkinPartType getPartType();
    
    int getMarkerCount();
    
    Point3D getMarker(int index);
    
    ForgeDirection getMarkerSide(int index);
}
