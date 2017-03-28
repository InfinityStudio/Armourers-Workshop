package riskyken;

import com.google.common.collect.Lists;
import net.skin43d.EquipmentWardrobeProvider;
import net.skin43d.SkinProvider;
import net.skin43d.impl.Context;
import net.skin43d.impl.SkinTypeRegistryImpl;
import net.skin43d.impl.client.render.bakery.BakedFace;
import net.skin43d.impl.client.render.bakery.BlockedModelBakery;
import net.skin43d.impl.client.render.bakery.SkinBakery;
import net.skin43d.impl.cubes.CubeRegistry;
import net.skin43d.impl.skin.Skin;
import net.skin43d.skin3d.SkinTypeRegistry;
import net.skin43d.utils.BitwiseUtils;
import net.skin43d.utils.PaintType;
import net.skin43d.utils.SkinIOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ci010
 */
public class TestSkin extends Context {
    private static String dir = "D:\\Storage\\Desktop\\testSkin";
    private static String steve = "D:\\Storage\\Desktop\\testSkin\\steve1.7.10.png", nsteve = "D:\\Storage\\Desktop\\testSkin\\Steve_skin.png";

    public static void main(String[] args) throws IOException {
        File file = new File(dir, "d");
        TestSkin testSkin = new TestSkin();
        Context.setInstance(testSkin);
        BlockedModelBakery bakery = new BlockedModelBakery();
        BufferedImage bakedTextureBuffer = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        BufferedImage originalImage = ImageIO.read(new File(nsteve));

        ImageIO.write(bakedTextureBuffer, "png", new File("D:\\Storage\\Desktop\\testSkin\\apply.png"));

        List<Skin> skins = Lists.newArrayList();
        for (File skF : file.listFiles()) {
            Skin skin = SkinIOUtils.loadSkinFromFileByBuffer(skF, testSkin);
//            System.out.println("LOADING " + skin);
            bakery.bake(skin);
            skins.add(skin);
        }

        for (int ix = 0; ix < testSkin.getTextureWidth(); ix++)
            for (int iy = 0; iy < testSkin.getTextureHeight(); iy++)
                bakedTextureBuffer.setRGB(ix, iy, originalImage.getRGB(ix, iy));
        ImageIO.write(bakedTextureBuffer, "png", new File("D:\\Storage\\Desktop\\testSkin\\pre.png"));

        for (int i = 0; i < skins.size(); i++) {
            Skin skin = skins.get(i);
            if (skin == null || !skin.hasPaintData()) continue;
            System.out.println(skin);
            for (int ix = 0; ix < testSkin.getTextureWidth(); ix++)
                for (int iy = 0; iy < testSkin.getTextureHeight(); iy++) {
                    int paintColour = skin.getPaintData()[ix + (iy * 64)];
                    PaintType paintType = PaintType.getPaintTypeFromColour(paintColour);
                    if (paintType == PaintType.NORMAL)
                        bakedTextureBuffer.setRGB(ix, iy, BitwiseUtils.setUByteToInt(paintColour, 0, 255));
                    else if (paintType == PaintType.HAIR)
                        bakedTextureBuffer.setRGB(ix, iy, dyeColour(-1, paintColour, 9, skin));
                    else if (paintType == PaintType.SKIN)
                        bakedTextureBuffer.setRGB(ix, iy, dyeColour(-1, paintColour, 8, skin));
                    else if (paintType.getKey() >= 1 && paintType.getKey() <= 8) {
                        int dyeNumber = paintType.getKey() - 1;
//                        if (this.dyes != null && this.dyes[i] != null && this.dyes[i].haveDyeInSlot(dyeNumber))
//                            this.bakedTextureBuffer.setRGB(ix, iy, dyeColour(dyes[i].getDyeColour(dyeNumber), paintColour, dyeNumber, skin));
//                        else
                        bakedTextureBuffer.setRGB(ix, iy, BitwiseUtils.setUByteToInt(paintColour, 0, 255));
                    }
                }
        }
        ImageIO.write(bakedTextureBuffer, "png", new File("D:\\Storage\\Desktop\\testSkin\\apply.png"));
    }

    private static int dyeColour(int dye, int colour, int dyeIndex, Skin skin) {
        byte[] dyeArray = new byte[3];
        dyeArray[0] = (byte) (dye >>> 16 & 0xFF);
        dyeArray[1] = (byte) (dye >>> 8 & 0xFF);
        dyeArray[2] = (byte) (dye & 0xFF);
        return dyeColour(dyeArray, colour, dyeIndex, skin);
    }

    private static int dyeColour(byte[] dye, int colour, int dyeIndex, Skin skin) {
        byte r = (byte) (colour >>> 16 & 0xFF);
        byte g = (byte) (colour >>> 8 & 0xFF);
        byte b = (byte) (colour & 0xFF);
        int[] average = {127, 127, 127};
        if (skin != null)
            average = skin.getAverageDyeColour(dyeIndex);
        dye = BakedFace.dyeColour(r, g, b, dye, average);
        return (255 << 24) + ((dye[0] & 0xFF) << 16) + ((dye[1] & 0xFF) << 8) + (dye[2] & 0xFF);
    }

    SkinTypeRegistry impl = new SkinTypeRegistryImpl();
    CubeRegistry cubeRegistry = new CubeRegistry();

    @Override
    public SkinTypeRegistry getSkinRegistry() {
        return impl;
    }

    @Override
    public EquipmentWardrobeProvider getEquipmentWardrobeProvider() {
        return null;
    }

    @Override
    public SkinProvider getSkinProvider() {
        return null;
    }

    @Override
    public SkinBakery getSkinBakery() {
        return null;
    }

    @Override
    public CubeRegistry getCubeRegistry() {
        return cubeRegistry;
    }

    @Override
    public int getTextureWidth() {
        return 64;
    }

    @Override
    public int getTextureHeight() {
        return 64;
    }

    @Override
    public int getTextureSize() {
        return getTextureHeight() * getTextureWidth();
    }

    @Override
    public int getFileVersion() {
        return 12;
    }

    @Override
    public boolean useSafeTexture() {
        return true;
    }

    @Override
    public boolean useMultipassSkinRendering() {
        return true;
    }

    private int maxLodLevels = 4;
    private double lodDistance = 32F;
    private int maxSkinRenderDistance = 128;
    private boolean useMultipassSkinRendering = true, useSafeTexture = false, wireframeRender = false, disableTexturePainting = false;


    @Override
    public int getNumberOfRenderLayers() {
        if (useMultipassSkinRendering())
            return 4;
        else
            return 2;
    }

    @Override
    public double getLodDistance() {
        return lodDistance;
    }

    @Override
    public int getMaxLodLevel() {
        return maxLodLevels;
    }

    @Override
    public int getRenderDistance() {
        return maxSkinRenderDistance;
    }

    @Override
    public boolean wireframeRender() {
        return wireframeRender;
    }

    @Override
    public boolean disableTexturePainting() {
        return disableTexturePainting;
    }
}
