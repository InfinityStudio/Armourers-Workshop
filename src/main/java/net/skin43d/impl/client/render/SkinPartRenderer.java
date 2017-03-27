package net.skin43d.impl.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.bakery.BakedFace;
import org.lwjgl.opengl.GL11;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.impl.client.render.bakery.BakedPart;
import net.skin43d.impl.skin.SkinPart;

import java.util.List;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;

@SideOnly(Side.CLIENT)
public class SkinPartRenderer extends ModelBase {
    //    private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armour/cube.png");
    public static final SkinPartRenderer INSTANCE = new SkinPartRenderer();

    public SkinPartRenderer() {

    }

    public void renderPart(SkinPart skinPart, float scale, ISkinDye skinDye, byte[] extraColour, double distance, boolean doLodLoading) {
        int lod = MathHelper.floor_double(distance / Context.instance().getLodDistance());
        lod = MathHelper.clamp_int(lod, 0, Context.instance().getMaxLodLevel());
        renderPart(skinPart.getBakedPart(), scale, skinDye, extraColour, lod, doLodLoading);
    }

    private void renderPart(BakedPart cspd, float scale, ISkinDye skinDye, byte[] extraColour, int lod, boolean doLodLoading) {
        if (cspd == null) return;
        BakedCubes skinModel = cspd.getModelForDye(skinDye, extraColour);
        cspd.getVertexes();
        boolean multipassSkinRendering = true;//ClientProxy.useMultipassSkinRendering();

        for (int i = 0; i < skinModel.displayList.length; i++) {
            if (skinModel.haveList[i]) {
                if (!skinModel.displayList[i].isCompiled()) {
                    skinModel.displayList[i].begin();
                    renderVertexList(cspd.vertexLists[i], scale, skinDye, extraColour, cspd);
                    skinModel.displayList[i].end();
                    skinModel.setLoaded();
                }
            }
        }

//        if (Context.instance().useSafeTexture())
//            mc.renderEngine.bindTexture(texture);
//        else
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        int startIndex = 0;
        int endIndex;

        int loadingLod = skinModel.getLoadingLod();
        if (!doLodLoading)
            loadingLod = 0;
        if (loadingLod > lod)
            lod = loadingLod;

        if (lod != 0)
            if (multipassSkinRendering)
                startIndex = lod * 4;
            else
                startIndex = lod * 2;

        if (multipassSkinRendering)
            endIndex = startIndex + 4;
        else
            endIndex = startIndex + 2;

        int listCount = skinModel.displayList.length;
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= startIndex & i < endIndex) {
                boolean glowing = false;
                if (i % 2 == 1) {
                    glowing = true;
                }
                if (i >= 0 & i < skinModel.displayList.length) {
                    if (skinModel.haveList[i]) {
                        if (skinModel.displayList[i].isCompiled()) {
                            if (glowing) {
                                GL11.glDisable(GL11.GL_LIGHTING);
                                ModRenderHelper.disableLighting();
                            }
                            if (Context.instance().wireframeRender()) {
                                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                            }
                            skinModel.displayList[i].render();
                            if (Context.instance().wireframeRender()) {
                                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                            }
                            if (glowing) {
                                ModRenderHelper.enableLighting();
                                GL11.glEnable(GL11.GL_LIGHTING);
                            }
                        }
                    }
                }
            }
        }

        if (!Context.instance().useSafeTexture()) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glColor3f(1F, 1F, 1F);
        //mc.mcProfiler.endSection();
    }

    static final VertexFormat POS_COLOR_NORMAL = new VertexFormat();

    static {
        POS_COLOR_NORMAL.addElement(POSITION_3F);
        POS_COLOR_NORMAL.addElement(COLOR_4UB);
        POS_COLOR_NORMAL.addElement(NORMAL_3B);
        POS_COLOR_NORMAL.addElement(PADDING_1B);
    }

    private void renderVertexList(List<BakedFace> vertexList, float scale, ISkinDye skinDye, byte[] extraColour, BakedPart cspd) {
        if (Context.instance().useSafeTexture())
            Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        else
            Tessellator.getInstance().getBuffer().begin(7, POS_COLOR_NORMAL);
        for (int i = 0; i < vertexList.size(); i++)
            vertexList.get(i).render(skinDye, extraColour, cspd, Context.instance().useSafeTexture());
        Tessellator.getInstance().draw();
    }
}
