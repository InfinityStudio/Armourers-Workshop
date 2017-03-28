package net.skin43d.impl.client.render.nbake;

import com.google.common.collect.Lists;
import net.skin43d.impl.Skin43D;
import net.skin43d.impl.client.render.bakery.BakedFace;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.BitwiseUtils;
import net.skin43d.impl.client.SkinModelTexture;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class BakeTask implements Callable<BakedSkin> {

    private final Skin skin;

    public BakeTask(Skin skin) {
        this.skin = skin;
    }

    @Override
    public BakedSkin call() throws Exception {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        SkinModelTexture modelTexture = null;
        List<BakeSkinPart> parts = Lists.newArrayList();
        int[][] dyeColour;
        int[] dyeUseCount;

        dyeColour = new int[3][10];
        dyeUseCount = new int[10];

        int[] averageR = new int[10];
        int[] averageG = new int[10];
        int[] averageB = new int[10];

        List<SkinPartType> part = skin.getSkinType().getSkinParts();
//        BakeSkinPart[] bakeSkinParts = new BakeSkinPart[part.size()];
        for (int i = 0; i < skin.getParts().size(); i++) {
            SkinPart partData = skin.getParts().get(i);
//            partData.setBakedPart(new BakedPart());
            int[] totalCubesInPart = new int[Skin43D.instance().getCubeRegistry().getTotalCubes()];
            int[][][] dim = FaceCuller.cullFacesPre(partData, totalCubesInPart);
            FaceCuller.cullFace(partData, dim);
            List<BakedFace>[] lists = FaceBaker.buildPartDisplayListArray(partData, dyeColour, dyeUseCount, dim);
            parts.add(new BakeSkinPart(averageR, averageG, averageB, lists, totalCubesInPart));
            partData.clearCubeData();
        }
        Skin43D skin43D = Skin43D.instance();
        if (skin.hasPaintData()) {
            modelTexture = new SkinModelTexture();
            for (int ix = 0; ix < skin43D.getTextureWidth(); ix++) {
                for (int iy = 0; iy < skin43D.getTextureHeight(); iy++) {
                    int paintColour = skin.getPaintData()[ix + (iy * skin43D.getTextureWidth())];
                    int paintType = BitwiseUtils.getUByteFromInt(paintColour, 0);

                    byte r = (byte) (paintColour >>> 16 & 0xFF);
                    byte g = (byte) (paintColour >>> 8 & 0xFF);
                    byte b = (byte) (paintColour & 0xFF);

                    if (paintType >= 1 && paintType <= 8) {
                        dyeUseCount[paintType - 1]++;
                        dyeColour[0][paintType - 1] += r & 0xFF;
                        dyeColour[1][paintType - 1] += g & 0xFF;
                        dyeColour[2][paintType - 1] += b & 0xFF;
                    }
                    if (paintType == 253) {
                        dyeUseCount[8]++;
                        dyeColour[0][8] += r & 0xFF;
                        dyeColour[1][8] += g & 0xFF;
                        dyeColour[2][8] += b & 0xFF;
                    }
                    if (paintType == 254) {
                        dyeUseCount[9]++;
                        dyeColour[0][9] += r & 0xFF;
                        dyeColour[1][9] += g & 0xFF;
                        dyeColour[2][9] += b & 0xFF;
                    }
                }
            }
        }


        for (int i = 0; i < 10; i++) {
            averageR[i] = (int) ((double) dyeColour[0][i] / (double) dyeUseCount[i]);
            averageG[i] = (int) ((double) dyeColour[1][i] / (double) dyeUseCount[i]);
            averageB[i] = (int) ((double) dyeColour[2][i] / (double) dyeUseCount[i]);
        }

        if (skin.hasPaintData() && modelTexture != null)
            modelTexture.createTextureForColours(skin, null);
        return new BakedSkin(modelTexture, averageR, averageG, averageB,
                parts.toArray(new BakeSkinPart[parts.size()]));
    }
}
