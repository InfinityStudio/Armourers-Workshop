package net.skin43d.impl.skin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import net.minecraft.client.resources.DefaultPlayerSkin;
import net.skin43d.impl.Skin43D;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.skin43d.impl.client.SkinHelper;
import net.skin43d.utils.PaintType;
import net.skin43d.utils.BitwiseUtils;

/**
 * @author RiskyKen
 */
public class SkinTexture {
    private final Minecraft mc;
    private BufferedImage bufferedPlayerImage;
    private BufferedImage bufferedSkinImage;
    private int lastProfileHash;
    private boolean needsUpdate;
    private int textureId;
    private int[] paintData;

    public SkinTexture() {
        mc = Minecraft.getMinecraft();
        bufferedSkinImage = new BufferedImage(Skin43D.instance().getContext().getTextureWidth(), Skin43D.instance().getContext().getTextureHeight(), BufferedImage.TYPE_INT_ARGB);
        lastProfileHash = -1;
        needsUpdate = true;
        textureId = -1;
        paintData = new int[Skin43D.instance().getContext().getTextureSize()];
    }

    @Override
    protected void finalize() throws Throwable {
        deleteTexture();
        super.finalize();
    }

    public void updateGameProfile(GameProfile gameProfile) {
        //TODO Look at RealmsScreen code.
        ResourceLocation rl = DefaultPlayerSkin.getDefaultSkin(gameProfile.getId());
        if (gameProfile != null) {
            rl = AbstractClientPlayer.getLocationSkin(gameProfile.getName());
            AbstractClientPlayer.getDownloadImageSkin(rl, gameProfile.getName());
        }
        updateForResourceLocation(rl);

        if (bufferedPlayerImage == null) {
            updateForResourceLocation(DefaultPlayerSkin.getDefaultSkin(gameProfile.getId()));
        }
    }

    public void updatePaintData(int[] paintData) {
        if (!Arrays.equals(paintData, this.paintData)) {
            this.paintData = paintData.clone();
            needsUpdate = true;
        }
    }

    public void updateForResourceLocation(ResourceLocation resourceLocation) {
        if (lastProfileHash == resourceLocation.hashCode() & bufferedPlayerImage != null) {
            return;
        }

        BufferedImage bi = null;
        InputStream inputStream = null;
        try {
            ITextureObject skintex = mc.getTextureManager().getTexture(resourceLocation);
            if (skintex instanceof ThreadDownloadImageData) {
                ThreadDownloadImageData imageData = (ThreadDownloadImageData) skintex;
                bi = ObfuscationReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, imageData, "bufferedImage", "field_110560_d", "bpr.h");
            } else {
                inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
                bi = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        if (bi == null) {
            return;
        }

        bufferedPlayerImage = SkinHelper.deepCopyBufferedImage(bi);
        lastProfileHash = resourceLocation.hashCode();
        needsUpdate = true;
    }

    private void updateTexture() {
        applyPlayerToTexture();
        applyPaintToTexture();
        uploadTexture();
        needsUpdate = false;
    }

    private void applyPlayerToTexture() {
        for (int ix = 0; ix < Skin43D.instance().getContext().getTextureWidth(); ix++) {
            for (int iy = 0; iy < Skin43D.instance().getContext().getTextureHeight(); iy++) {
                if (bufferedPlayerImage == null) {
                    //ModLogger.log("null player image");
                    break;
                }
                bufferedSkinImage.setRGB(ix, iy, bufferedPlayerImage.getRGB(ix, iy));
            }
        }
    }

    private void applyPaintToTexture() {
        for (int ix = 0; ix < Skin43D.instance().getContext().getTextureWidth(); ix++) {
            for (int iy = 0; iy < Skin43D.instance().getContext().getTextureHeight(); iy++) {
                int paintColour = paintData[ix + (iy * Skin43D.instance().getContext().getTextureWidth())];
                PaintType paintType = PaintType.getPaintTypeFromColour(paintColour);
                if (paintType != PaintType.NONE) {
                    bufferedSkinImage.setRGB(ix, iy, BitwiseUtils.setUByteToInt(paintColour, 0, 255));
                }
            }
        }
    }

    private void deleteTexture() {
        if (textureId != -1) {
            TextureUtil.deleteTexture(textureId);
            textureId = -1;
        }
    }

    private void uploadTexture() {
        deleteTexture();
        textureId = TextureUtil.glGenTextures();
        TextureUtil.uploadTextureImage(textureId, bufferedSkinImage);
    }

    public void bindTexture() {
        if (needsUpdate) {
            updateTexture();
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }
}
