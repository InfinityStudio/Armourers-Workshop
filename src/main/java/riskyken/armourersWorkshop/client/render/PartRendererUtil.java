package riskyken.armourersWorkshop.client.render;

import net.skin43d.impl.client.render.nbake.BakeSkinPart;
import net.skin43d.impl.client.render.BakedFace;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.client.ClientProxy;
import riskyken.armourersWorkshop.client.render.core.BakedCubes;
import riskyken.armourersWorkshop.client.render.core.ModRenderHelper;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.plushieWrapper.client.IRenderBuffer;
import riskyken.plushieWrapper.client.RenderBridge;

import java.util.List;

/**
 * @author ci010
 */
public class PartRendererUtil {
//    private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armour/cube.png");

    //TODO handle the safe texture
    public static void renderPart(BakeSkinPart part, ISkinDye skinDye, byte[] extraColour, int lod, boolean doLodLoading) {
        if (part == null) return;
        BakedCubes skinModel = part.getModelForDye(skinDye, extraColour);
        boolean multiPass = true;//ClientProxy.useMultipassSkinRendering();

        for (int i = 0; i < skinModel.length(); i++) {
            if (skinModel.hasList(i)) {
                if (!skinModel.getDisplayList(i).isCompiled()) {
                    List<BakedFace>[] lists = part.getColouredFaces();
                    skinModel.getDisplayList(i).begin();
                    renderVertexList(lists[i], skinDye, extraColour, part);
                    skinModel.getDisplayList(i).end();
                    skinModel.setLoaded();
                }
            }
        }

//        if (ClientProxy.useSafeTextureRender())
//            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
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
            if (multiPass) startIndex = lod * 4;
            else startIndex = lod * 2;

        if (multiPass) endIndex = startIndex + 4;
        else endIndex = startIndex + 2;

        for (int i = startIndex; i < endIndex; i++) {
            if (i >= startIndex & i < endIndex) {
                boolean glowing = false;
                if (i % 2 == 1)
                    glowing = true;
                if (i >= 0 & i < skinModel.displayList.length) {
                    if (skinModel.haveList[i]) {
                        if (skinModel.displayList[i].isCompiled()) {
                            if (glowing) {
                                GL11.glDisable(GL11.GL_LIGHTING);
                                ModRenderHelper.disableLighting();
                            }
                            if (ConfigHandlerClient.wireframeRender) {
                                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                            }
                            skinModel.displayList[i].render();
                            if (ConfigHandlerClient.wireframeRender) {
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

        if (!ClientProxy.useSafeTextureRender()) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glColor3f(1F, 1F, 1F);
        //mc.mcProfiler.endSection();
    }

    private static void renderVertexList(List<BakedFace> vertexList, ISkinDye skinDye, byte[] extraColour,
                                         BakeSkinPart data) {
        IRenderBuffer renderBuffer = RenderBridge.INSTANCE;
        renderBuffer.startDrawingQuads();
        for (int i = 0; i < vertexList.size(); i++)
            vertexList.get(i).render(skinDye, extraColour, data, ClientProxy.useSafeTextureRender());
        renderBuffer.draw();
    }
}
