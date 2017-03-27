package net.skin43d.impl.client.render;

import net.skin43d.impl.Context;
import net.skin43d.impl.client.model.IEquipmentModel;
import net.skin43d.impl.skin.Skin;
import net.skin43d.utils.Rectangle3D;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
public class FirstPersonRenderHelper {
    public static void render(Skin skin, IEquipmentModel model, boolean showSkinPaint, boolean doLodLoading) {
        GL11.glPushMatrix();
        GL11.glScalef(-1F, -1F, 1F);
        GL11.glRotatef(180, 0, 1, 0);
        GL11.glTranslatef(0.5F, -0.7F, -0.5F);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        ModRenderHelper.enableAlphaBlend();
        GL11.glEnable(GL11.GL_CULL_FACE);

        float blockScale = 16F;

        float mcScale = 1F / blockScale;
        float scale = 1;

        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        float scaleX = 1;
        float scaleY = 1;
        float scaleZ = 1;

        int width = 1;
        int height = 1;
        int depth = 1;

        Rectangle3D sb = skin.getSkinBounds();

        width = Math.max(width, sb.getWidth());
        height = Math.max(height, sb.getHeight());
        depth = Math.max(depth, sb.getDepth());

        scaleX = Math.min(scaleX, 1F / (float) width);
        scaleY = Math.min(scaleY, 1F / (float) height);
        scaleZ = Math.min(scaleZ, 1F / (float) depth);

        scale = Math.min(scale, scaleX);
        scale = Math.min(scale, scaleY);
        scale = Math.min(scale, scaleZ);

        offsetX = -sb.getX() - width / 2F;
        offsetY = -sb.getY() - height / 2F;
        offsetZ = -sb.getZ() - depth / 2F;

        GL11.glPushMatrix();

        GL11.glScalef(scale * blockScale, scale * blockScale, scale * blockScale);
        GL11.glTranslatef(offsetX * mcScale, 0, 0);
        GL11.glTranslatef(0, offsetY * mcScale, 0);
        GL11.glTranslatef(0, 0, offsetZ * mcScale);

        model.render(null, null, skin, showSkinPaint, null, null, true, 0, doLodLoading);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}
