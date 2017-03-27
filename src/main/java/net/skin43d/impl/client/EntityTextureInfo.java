package net.skin43d.impl.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.bakery.BakedFace;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.skin3d.ISkinPartTypeTextured;
import net.skin43d.skin3d.SkinTypeRegistry;
import net.skin43d.utils.BitwiseUtils;
import net.skin43d.utils.ModLogger;
import net.skin43d.utils.PaintType;
import net.skin43d.utils.Point3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class EntityTextureInfo {
    private static final int TEXTURE_WIDTH = Context.instance().getTextureWidth();
    private static final int TEXTURE_HEIGHT = Context.instance().getTextureHeight();

    /**
     * The last texture entity had when the replacement texture was made.
     */
    private int lastEntityTextureHash;
    /**
     * The last skin hashs the entity had when the replacement texture was made.
     */
    private int[] lastSkinHashs;
    private int[] lastDyeHashs;
    /**
     * Skins that the entity has equipped.
     */
    private Skin[] skins;
    private ISkinDye[] dyes;
    /**
     * The entities normal texture.
     */
    private ResourceLocation normalTexture;
    private ResourceLocation replacementTexture;
    /**
     * The last skin colour the entity had when the replacement texture was made.
     */
    private int lastEntitySkinColour;
    private int lastEntityHairColour;
    /**
     * A buffered image of the entity texture.
     */
    private BufferedImage originalImage;
    private BufferedImage bakedTextureBuffer;
    /**
     * Does the texture need to be remade?
     */
    private boolean dirty;
    /**
     * Is this texture still loading?
     */
    private boolean loading;

    private Map<MinecraftProfileTexture.Type, ResourceLocation> textureMap;

    public Map<MinecraftProfileTexture.Type, ResourceLocation> getTextureMap() {
        return textureMap;
    }

    public EntityTextureInfo setTextureMap(Map<MinecraftProfileTexture.Type, ResourceLocation> textureMap) {
        this.textureMap = textureMap;
        return this;
    }

    public EntityTextureInfo() {
        lastEntityTextureHash = -1;
        lastSkinHashs = new int[4 * 5];
        lastDyeHashs = new int[4 * 5];
        normalTexture = null;
        replacementTexture = null;
        for (int i = 0; i < lastSkinHashs.length; i++)
            lastSkinHashs[i] = -1;
        for (int i = 0; i < lastDyeHashs.length; i++)
            lastDyeHashs[i] = -1;
        lastEntitySkinColour = -1;
        lastEntityHairColour = -1;
        bakedTextureBuffer = new BufferedImage(TEXTURE_WIDTH, TEXTURE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        dirty = true;
        loading = false;
    }

    public boolean getNeedsUpdate() {
        return dirty;
    }

    public void updateTexture(ResourceLocation resourceLocation) {
        if (lastEntityTextureHash != resourceLocation.hashCode()) {
            BufferedImage buff = SkinHelper.getBufferedImageSkin(resourceLocation);
            originalImage = null;
            if (buff != null) {
                loading = false;
                lastEntityTextureHash = resourceLocation.hashCode();
                normalTexture = resourceLocation;
                originalImage = buff;
                dirty = true;
            }
        }

//        if (originalImage == null) {
//            //Texture is most likely not downloaded yet.
//            lastEntityTextureHash = AbstractClientPlayer.locationStevePng.hashCode();
//            originalImage = SkinHelper.getBufferedImageSkin(AbstractClientPlayer.locationStevePng);
//            if (originalImage != null & !loading) {
//                loading = true;
//                dirty = true;
//            }
//        }
    }

    void updateSkinColour(int colour) {
        if (lastEntitySkinColour == colour) return;
        lastEntitySkinColour = colour;
        dirty = true;
    }

    void updateHairColour(int colour) {
        if (lastEntityHairColour == colour) return;
        lastEntityHairColour = colour;
        dirty = true;
    }

    void updateSkins(Skin[] skins) {
        this.skins = skins;
        for (int i = 0; i < skins.length; i++)
            if (skins[i] != null) {
                if (skins[i].lightHash() != lastSkinHashs[i]) {
                    lastSkinHashs[i] = skins[i].lightHash();
                    dirty = true;
                }
            } else if (lastSkinHashs[i] != -1) {
                lastSkinHashs[i] = -1;
                dirty = true;
            }
    }

    void updateDyes(ISkinDye[] dyes) {
        this.dyes = dyes;
        for (int i = 0; i < skins.length; i++)
            if (dyes[i] != null) {
                if (dyes[i].hashCode() != lastDyeHashs[i]) {
                    lastDyeHashs[i] = dyes[i].hashCode();
                    dirty = true;
                }
            } else if (lastDyeHashs[i] != -1) {
                lastDyeHashs[i] = -1;
                dirty = true;
            }
    }

    private void ensureBaked() {
        if (dirty) {
            ModLogger.log("rebuilding texture");
            applyPlayerToTexture();
            applySkinsToTexture();
            createReplacementTexture();
            dirty = false;
        }
    }

    //TODO check if the skins have a texture.
    @Override
    protected void finalize() throws Throwable {
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
        if (replacementTexture != null)
            renderEngine.deleteTexture(replacementTexture);
        super.finalize();
    }

    ResourceLocation getReplacedTexture() {
        ensureBaked();
        if (replacementTexture != null) return replacementTexture;
        else return normalTexture;
    }

    public ResourceLocation postRender() {
        return normalTexture;
    }

    private void applyPlayerToTexture() {
        if (this.originalImage == null) return;
        for (int ix = 0; ix < TEXTURE_WIDTH; ix++)
            for (int iy = 0; iy < TEXTURE_HEIGHT; iy++)
                this.bakedTextureBuffer.setRGB(ix, iy, this.originalImage.getRGB(ix, iy));
    }

    private void applySkinsToTexture() {
        for (int i = 0; i < this.skins.length; i++) {
            Skin skin = this.skins[i];
            if (skin == null || !skin.hasPaintData()) continue;
            for (int ix = 0; ix < TEXTURE_WIDTH; ix++)
                for (int iy = 0; iy < TEXTURE_HEIGHT; iy++) {
                    int paintColour = skin.getPaintData()[ix + (iy * TEXTURE_WIDTH)];
                    PaintType paintType = PaintType.getPaintTypeFromColour(paintColour);
                    if (paintType == PaintType.NORMAL)
                        this.bakedTextureBuffer.setRGB(ix, iy, BitwiseUtils.setUByteToInt(paintColour, 0, 255));
                    else if (paintType == PaintType.HAIR)
                        this.bakedTextureBuffer.setRGB(ix, iy, dyeColour(this.lastEntityHairColour, paintColour, 9, skin));
                    else if (paintType == PaintType.SKIN)
                        this.bakedTextureBuffer.setRGB(ix, iy, dyeColour(this.lastEntitySkinColour, paintColour, 8, skin));
                    else if (paintType.getKey() >= 1 && paintType.getKey() <= 8) {
                        int dyeNumber = paintType.getKey() - 1;
                        if (this.dyes != null && this.dyes[i] != null && this.dyes[i].haveDyeInSlot(dyeNumber))
                            this.bakedTextureBuffer.setRGB(ix, iy, dyeColour(dyes[i].getDyeColour(dyeNumber), paintColour, dyeNumber, skin));
                        else
                            this.bakedTextureBuffer.setRGB(ix, iy, BitwiseUtils.setUByteToInt(paintColour, 0, 255));
                    }
                }
        }

        for (Skin skin : this.skins)
            if (skin != null && skin.getProperties().getPropertyBoolean(Skin.KEY_ARMOUR_OVERRIDE, false))
                for (int j = 0; j < skin.getPartCount(); j++) {
                    SkinPart skinPart = skin.getParts().get(j);
                    if (skinPart.getPartType() instanceof ISkinPartTypeTextured) {
                        ISkinPartTypeTextured typeTextured = (ISkinPartTypeTextured) skinPart.getPartType();
                        SkinTypeRegistry reg = Context.instance().getSkinRegistry();
                        Point texLoc = typeTextured.getTextureLocation();
                        Point3D texSize = typeTextured.getTextureModelSize();
                        for (int ix = 0; ix < texSize.getZ() * 2 + texSize.getX() * 2; ix++)
                            for (int iy = 0; iy < texSize.getZ() + texSize.getY(); iy++) {
                                if (skin.getSkinType() == reg.getSkinLegs())
                                    if (iy >= 12) continue;
                                    else if (iy < 4 & ix > 7 & ix < 12) continue;
                                if (skin.getSkinType() == reg.getSkinFeet())
                                    if (iy < 12) if (!(iy < 4 & ix > 7 & ix < 12)) continue;
                                this.bakedTextureBuffer.setRGB((int) texLoc.getX() + ix, (int) texLoc.getY() + iy, 0x00FFFFFF);
                            }
                    }
                }
    }

    private int dyeColour(int dye, int colour, int dyeIndex, Skin skin) {
        byte[] dyeArray = new byte[3];
        dyeArray[0] = (byte) (dye >>> 16 & 0xFF);
        dyeArray[1] = (byte) (dye >>> 8 & 0xFF);
        dyeArray[2] = (byte) (dye & 0xFF);
        return dyeColour(dyeArray, colour, dyeIndex, skin);
    }

    private int dyeColour(byte[] dye, int colour, int dyeIndex, Skin skin) {
        byte r = (byte) (colour >>> 16 & 0xFF);
        byte g = (byte) (colour >>> 8 & 0xFF);
        byte b = (byte) (colour & 0xFF);
        int[] average = {127, 127, 127};
        if (skin != null)
            average = skin.getAverageDyeColour(dyeIndex);
        dye = BakedFace.dyeColour(r, g, b, dye, average);
        return (255 << 24) + ((dye[0] & 0xFF) << 16) + ((dye[1] & 0xFF) << 8) + (dye[2] & 0xFF);
    }

    private void createReplacementTexture() {
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
        if (this.replacementTexture != null) {
            renderEngine.deleteTexture(this.replacementTexture);
        }
        SkinTextureObject sto = new SkinTextureObject(this.bakedTextureBuffer);
        //TODO
        this.replacementTexture = new ResourceLocation("skin43d", String.valueOf(this.bakedTextureBuffer.hashCode()));
        renderEngine.loadTexture(this.replacementTexture, sto);
    }

    private static class SkinTextureObject extends AbstractTexture {

        private final BufferedImage texture;

        public SkinTextureObject(BufferedImage texture) {
            this.texture = texture;
        }

        @Override
        public void loadTexture(IResourceManager resourceManager) throws IOException {
            getGlTextureId();
            TextureUtil.uploadTextureImage(glTextureId, texture);
        }
    }
}
