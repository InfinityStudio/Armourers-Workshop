package riskyken.armourersWorkshop.api.common.skin.data;

import java.util.List;

import net.minecraftforge.common.util.ForgeDirection;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.Point3D;

public interface Skin3D {
    SkinType getSkinType();

    List<Part> getSubParts();

    interface Part {
        SkinPartType getPartType();

        int getMarkerCount();

        Point3D getMarker(int index);

        ForgeDirection getMarkerSide(int index);
    }
}
