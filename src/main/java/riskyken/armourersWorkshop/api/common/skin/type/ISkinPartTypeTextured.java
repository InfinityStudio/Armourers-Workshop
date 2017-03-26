package riskyken.armourersWorkshop.api.common.skin.type;

import java.awt.Point;

import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;

public interface ISkinPartTypeTextured extends SkinPartType {
    Point getTextureLocation();
    
    boolean isTextureMirrored();
    
    Point3D getTextureModelSize();
}
