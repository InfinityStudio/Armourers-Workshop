package net.skin43d.impl.client.render.bakery;

import net.skin43d.impl.Skin43D;
import net.skin43d.utils.BitwiseUtils;
import net.skin43d.impl.client.SkinModelTexture;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;

import java.util.concurrent.Callable;

/**
 * @author ci010
 */
public class LegacyBakeSkinTask implements Callable<Skin> {

    private final Skin skin;

    public LegacyBakeSkinTask(Skin skin) {
        this.skin = skin;
    }

    @Override
    public Skin call() throws Exception {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        skin.lightHash();

        int[][] dyeColour = new int[3][10];
        int[] dyeUseCount = new int[10];

        for (int i = 0; i < skin.getParts().size(); i++) {
            SkinPart partData = skin.getParts().get(i);
            partData.setBakedPart(new BakedPart());
            int[][][] cubeSpace = SkinBaker.cullFacesOnEquipmentPart(partData);
            SkinBaker.buildPartDisplayListArray(partData, dyeColour, dyeUseCount, cubeSpace);
            partData.clearCubeData();
        }
        Skin43D skin43D = Skin43D.instance();
        if (skin.hasPaintData()) {
            skin.skinModelTexture = new SkinModelTexture();
            for (int ix = 0; ix < skin43D.getContext().getTextureWidth(); ix++) {
                for (int iy = 0; iy < skin43D.getContext().getTextureHeight(); iy++) {
                    int paintColour = skin.getPaintData()[ix + (iy * skin43D.getContext().getTextureWidth())];
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

        int[] averageR = new int[10];
        int[] averageG = new int[10];
        int[] averageB = new int[10];

        for (int i = 0; i < 10; i++) {
            averageR[i] = (int) ((double) dyeColour[0][i] / (double) dyeUseCount[i]);
            averageG[i] = (int) ((double) dyeColour[1][i] / (double) dyeUseCount[i]);
            averageB[i] = (int) ((double) dyeColour[2][i] / (double) dyeUseCount[i]);
        }

        for (int i = 0; i < skin.getParts().size(); i++)
            skin.getParts().get(i).getBakedPart().setAverageDyeValues(averageR, averageG, averageB);

        skin.setAverageDyeValues(averageR, averageG, averageB);
        if (skin.hasPaintData())
            skin.skinModelTexture.createTextureForColours(skin, null);
        return skin;
    }
}
