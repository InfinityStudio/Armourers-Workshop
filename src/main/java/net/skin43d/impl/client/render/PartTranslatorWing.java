package net.skin43d.impl.client.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.skin43d.utils.Point3D;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
public abstract class PartTranslatorWing implements PartTranslator {
    public static final PartTranslatorWing LEFT_WINT = new PartTranslatorWing() {
        @Override
        protected void onTranslateLeftOrRight(ForgeDirection axis, double angle) {
            switch (axis) {
                case UP:
                case DOWN:
                    GL11.glRotated(angle, 0, 1, 0);
                    break;
                case NORTH:
                case EAST:
                    GL11.glRotated(angle, 1, 0, 0);
                    break;
                case SOUTH:
                case WEST:
                    GL11.glRotated(angle, 0, 0, 1);
                    break;
                case UNKNOWN:
                    break;
            }
        }
    }, RIGHT_WING = new PartTranslatorWing() {
        @Override
        protected void onTranslateLeftOrRight(ForgeDirection axis, double angle) {
            switch (axis) {
                case UP:
                    GL11.glRotated(-angle, 0, 1, 0);
                    break;
                case DOWN:
                    GL11.glRotated(-angle, 0, 1, 0);
                    break;
                case NORTH:
                    GL11.glRotated(-angle, 1, 0, 0);
                    break;
                case EAST:
                    GL11.glRotated(-angle, 1, 0, 0);
                    break;
                case SOUTH:
                    GL11.glRotated(-angle, 0, 0, 1);
                    break;
                case WEST:
                    GL11.glRotated(angle, 1, 0, 0);
                    break;
                case UNKNOWN:
                    break;
            }
        }
    };

    @Override
    public void translate(float scale, EntityPlayer player) {
        GL11.glTranslated(0, 0, scale * 2);
        Point3D point = new Point3D(0, 0, 0);
        ForgeDirection axis = ForgeDirection.DOWN;

//        if (partData.getMarkerCount() > 0) {
//            point = partData.getMarker(0);
//            axis = partData.getMarkerSide(0);
//        }

        GL11.glTranslated(scale * 0.5F, scale * 0.5F, scale * 0.5F);
        GL11.glTranslated(scale * point.getX(), scale * point.getY(), scale * point.getZ());

        GL11.glTranslated(scale * -point.getX(), scale * -point.getY(), scale * -point.getZ());
        GL11.glTranslated(scale * -0.5F, scale * -0.5F, scale * -0.5F);
    }

    protected abstract void onTranslateLeftOrRight(ForgeDirection axis, double angle);
}
