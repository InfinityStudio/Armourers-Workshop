package riskyken.armourersWorkshop.api.common.skin.data;

import java.util.List;

import net.minecraft.util.EnumFacing;
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

        EnumFacing getMarkerSide(int index);
    }
}
