package net.skin43d.skin3d;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.skin43d.impl.skin.SkinProperties;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import net.skin43d.utils.ForgeDirection;
import net.skin43d.utils.Point3D;

public interface Skin3D {
    SkinType getSkinType();

    List<Part> getSubParts();

    String getAuthorName();

    SkinProperties getProperties();

    interface Part {
        SkinPartType getPartType();

        int getMarkerCount();

        Point3D getMarker(int index);

        ForgeDirection getMarkerSide(int index);
    }
}
