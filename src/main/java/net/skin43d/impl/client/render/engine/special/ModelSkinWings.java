package net.skin43d.impl.client.render.engine.special;

import java.util.List;

import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.skin43d.utils.ForgeDirection;
import net.skin43d.utils.Point3D;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;

public class ModelSkinWings extends AbstractSkinModel {
    @Override
    public void render(Entity entity, Skin skin, boolean showSkinPaint, ISkinDye skinDye, byte[] extraColour, boolean itemRender, double distance, boolean doLodLoading) {
        if (skin == null)
            return;

        List<SkinPart> parts = skin.getParts();

        if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            this.isSneak = player.isSneaking();
            this.isRiding = player.isRiding();
            this.rightArmPose = ArmPose.EMPTY;
            if (player.getHeldItem(EnumHand.MAIN_HAND) != null) {
                this.rightArmPose = ArmPose.ITEM;
            }
        }

//        if (ClientProxy.isJrbaClientLoaded())
//            this.isChild = false;

        for (SkinPart part : parts) {
            GL11.glPushMatrix();
            GL11.glTranslated(0, 0, SCALE * 2);
            if (isChild) {
                float f6 = 2.0F;
                GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
                GL11.glTranslatef(0.0F, 24.0F * SCALE, 0.0F);
            }
            double angle = getFlapAngleForWings(entity, skin);
            if (isSneak) GL11.glRotated(28F, 1F, 0, 0);
            if (part.getPartType().getPartName().equals("leftWing"))
                renderLeftWing(part, SCALE, skinDye, extraColour, distance, angle, doLodLoading);
            if (part.getPartType().getPartName().equals("rightWing"))
                renderRightWing(part, SCALE, skinDye, extraColour, distance, -angle, doLodLoading);
            GL11.glPopMatrix();
        }
        GL11.glColor3f(1F, 1F, 1F);
    }

    private void renderLeftWing(SkinPart part, float scale, ISkinDye skinDye, byte[] extraColour, double distance, double angle, boolean doLodLoading) {
        GL11.glPushMatrix();

        Point3D point = new Point3D(0, 0, 0);
        ForgeDirection axis = ForgeDirection.DOWN;

        if (part.getMarkerCount() > 0) {
            point = part.getMarker(0);
            axis = part.getMarkerSide(0);
        }
        GL11.glRotatef((float) Math.toDegrees(this.bipedBody.rotateAngleZ), 0, 0, 1);
        GL11.glRotatef((float) Math.toDegrees(this.bipedBody.rotateAngleY), 0, 1, 0);
        //GL11.glRotatef((float) RadiansToDegrees(this.bipedBody.rotateAngleX), 1, 0, 0);

        GL11.glTranslated(SCALE * 0.5F, SCALE * 0.5F, SCALE * 0.5F);
        GL11.glTranslated(SCALE * point.getX(), SCALE * point.getY(), SCALE * point.getZ());

        switch (axis) {
            case UP:
                GL11.glRotated(angle, 0, 1, 0);
                break;
            case DOWN:
                GL11.glRotated(angle, 0, 1, 0);
                break;
            case NORTH:
                GL11.glRotated(angle, 1, 0, 0);
                break;
            case EAST:
                GL11.glRotated(angle, 1, 0, 0);
                break;
            case SOUTH:
                GL11.glRotated(angle, 0, 0, 1);
                break;
            case WEST:
                GL11.glRotated(angle, 0, 0, 1);
                break;
            case UNKNOWN:
                break;
        }
        GL11.glTranslated(SCALE * -point.getX(), SCALE * -point.getY(), SCALE * -point.getZ());
        GL11.glTranslated(SCALE * -0.5F, SCALE * -0.5F, SCALE * -0.5F);

        renderPart(part, scale, skinDye, extraColour, distance, doLodLoading);
        GL11.glPopMatrix();
    }

    private void renderRightWing(SkinPart part, float scale, ISkinDye skinDye, byte[] extraColour, double distance, double angle, boolean doLodLoading) {
        GL11.glPushMatrix();
        Point3D point = new Point3D(0, 0, 0);
        ForgeDirection axis = ForgeDirection.DOWN;

        if (part.getMarkerCount() > 0) {
            point = part.getMarker(0);
            axis = part.getMarkerSide(0);
        }
        GL11.glRotatef((float) Math.toDegrees(this.bipedBody.rotateAngleZ), 0, 0, 1);
        GL11.glRotatef((float) Math.toDegrees(this.bipedBody.rotateAngleY), 0, 1, 0);
        //GL11.glRotatef((float) RadiansToDegrees(this.bipedBody.rotateAngleX), 1, 0, 0);

        GL11.glTranslated(SCALE * 0.5F, SCALE * 0.5F, SCALE * 0.5F);
        GL11.glTranslated(SCALE * point.getX(), SCALE * point.getY(), SCALE * point.getZ());
        switch (axis) {
            case UP:
                GL11.glRotated(angle, 0, 1, 0);
                break;
            case DOWN:
                GL11.glRotated(angle, 0, 1, 0);
                break;
            case NORTH:
                GL11.glRotated(angle, 1, 0, 0);
                break;
            case EAST:
                GL11.glRotated(angle, 1, 0, 0);
                break;
            case SOUTH:
                GL11.glRotated(angle, 0, 0, 1);
                break;
            case WEST:
                GL11.glRotated(-angle, 1, 0, 0);
                break;
            case UNKNOWN:
                break;
        }
        GL11.glTranslated(SCALE * -point.getX(), SCALE * -point.getY(), SCALE * -point.getZ());
        GL11.glTranslated(SCALE * -0.5F, SCALE * -0.5F, SCALE * -0.5F);

        renderPart(part, scale, skinDye, extraColour, distance, doLodLoading);
        GL11.glPopMatrix();
    }

    public static double getFlapAngleForWings(Entity entity, Skin skin) {

        double maxAngle = skin.getProperties().getPropertyDouble(Skin.KEY_WINGS_MAX_ANGLE, 75D);
        double minAngle = skin.getProperties().getPropertyDouble(Skin.KEY_WINGS_MIN_ANGLE, 0D);
        double idleSpeed = skin.getProperties().getPropertyDouble(Skin.KEY_WINGS_IDLE_SPEED, 6000D);
        double flyingSpeed = skin.getProperties().getPropertyDouble(Skin.KEY_WINGS_FLYING_SPEED, 350D);

        double angle = 0;
        double flapTime = idleSpeed;

        if (entity != null) {
            if (entity.isAirBorne) {
                if (entity instanceof EntityPlayer) {
                    if (((EntityPlayer) entity).capabilities.isFlying) {
                        flapTime = flyingSpeed;
                    }
                } else {
                    flapTime = flyingSpeed;
                }
            }
            angle = (((double) System.currentTimeMillis() + entity.getEntityId()) % flapTime);
            angle = Math.sin(angle / flapTime * Math.PI * 2);
        }

        double fullAngle = maxAngle - minAngle;
        return -minAngle - fullAngle * ((angle + 1D) / 2);
    }
}
