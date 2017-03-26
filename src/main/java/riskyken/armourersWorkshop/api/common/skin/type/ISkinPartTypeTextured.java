package riskyken.armourersWorkshop.api.common.skin.type;

import java.awt.Point;

import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.api.common.IPoint3D;

public interface ISkinPartTypeTextured extends SkinPartType {

    public Point getTextureLocation();
    
    public boolean isTextureMirrored();
    
    public IPoint3D getTextureModelSize();
}
