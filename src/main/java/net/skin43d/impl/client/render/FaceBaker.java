package net.skin43d.impl.client.render;

import net.skin43d.impl.Context;
import net.skin43d.utils.Rectangle3D;
import riskyken.armourersWorkshop.client.ClientProxy;
import riskyken.armourersWorkshop.client.skin.ClientSkinPartData;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.cubes.ICube;
import riskyken.armourersWorkshop.common.skin.data.SkinCubeData;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ci010
 */
public class FaceBaker {
    static void buildPartDisplayListArray(SkinPart partData, int[][] dyeColour, int[] dyeUseCount, int[][][] cubeArray) {
        boolean multipassSkinRendering = ClientProxy.useMultipassSkinRendering();


        int lodLevels = ConfigHandlerClient.maxLodLevels;

        /* LOD Indexs
         *
         * with multipass;
         * 0 = normal
         * 1 = glowing
         * 2 = glass
         * 3 = glass glowing
         *
         * without multipass
         * 0 = normal
         * 1 = glowing
         */

        List<BakedFace>[] renderLists = new ArrayList[ClientProxy.getNumberOfRenderLayers() * (lodLevels + 1)];
        for (int i = 0; i < renderLists.length; i++)
            renderLists[i] = new ArrayList<BakedFace>();

        float scale = 0.0625F;

        SkinCubeData cubeData = partData.getCubeData();
        Rectangle3D bound = partData.getPartBounds();
        ClientSkinPartData baked = partData.getClientSkinPartData();

        for (int ix = 0; ix < bound.getWidth(); ix++) {
            for (int iy = 0; iy < bound.getHeight(); iy++) {
                for (int iz = 0; iz < bound.getDepth(); iz++) {
                    int locIdx = FaceCuller.getIndexForLocation(ix, iy, iz, bound, cubeArray) - 1;
                    if (locIdx != -1) {
                        byte[] loc = cubeData.getCubeLocation(locIdx);
                        byte[] paintType = cubeData.getCubePaintType(locIdx);
                        ICube cube = cubeData.getCube(locIdx);

                        byte a = (byte) 255;
                        if (cube.needsPostRender()) a = (byte) 127;

                        byte[] r = cubeData.getCubeColourR(locIdx);
                        byte[] g = cubeData.getCubeColourG(locIdx);
                        byte[] b = cubeData.getCubeColourB(locIdx);

                        for (int j = 0; j < 6; j++) {
                            int paint = paintType[j] & 0xFF;
                            if (paint >= 1 && paint <= 8 && cubeData.getFaceFlags(locIdx).get(j)) {
                                dyeUseCount[paint - 1]++;
                                dyeColour[0][paint - 1] += r[j] & 0xFF;
                                dyeColour[1][paint - 1] += g[j] & 0xFF;
                                dyeColour[2][paint - 1] += b[j] & 0xFF;
                            }
                            if (paint == 253) {
                                dyeUseCount[8]++;
                                dyeColour[0][8] += r[j] & 0xFF;
                                dyeColour[1][8] += g[j] & 0xFF;
                                dyeColour[2][8] += b[j] & 0xFF;
                            }
                            if (paint == 254) {
                                dyeUseCount[9]++;
                                dyeColour[0][9] += r[j] & 0xFF;
                                dyeColour[1][9] += g[j] & 0xFF;
                                dyeColour[2][9] += b[j] & 0xFF;
                            }
                        }

                        int listIndex = 0;
                        if (multipassSkinRendering) {
                            if (cube.isGlowing() && !cube.needsPostRender()) {
                                listIndex = 1;
                            }
                            if (cube.needsPostRender() && !cube.isGlowing()) {
                                listIndex = 2;
                            }
                            if (cube.isGlowing() && cube.needsPostRender()) {
                                listIndex = 3;
                            }
                        } else {
                            if (cube.isGlowing()) {
                                listIndex = 1;
                            }
                        }

                        for (int j = 0; j < 6; j++)
                            if (cubeData.getFaceFlags(locIdx).get(j))
                                renderLists[listIndex].add(new BakedFace(
                                        loc[0], loc[1], loc[2],
                                        r[j], g[j], b[j],
                                        a, paintType[j], (byte) j, (byte) (1)));
                    }

                    //Create model LODs
                    for (int lod = 1; lod < lodLevels + 1; lod++) {
                        byte lodLevel = (byte) Math.pow(2, lod);
                        if ((ix) % lodLevel == 0 & (iy) % lodLevel == 0 & (iz) % lodLevel == 0) {

                            for (int j = 0; j < 6; j++) {
                                boolean showFace = getAverageFaceFlags(ix, iy, iz, lodLevel, cubeArray, cubeData, bound, j);
                                if (showFace) {
                                    byte[] avegC = getAverageRGBAT(ix, iy, iz, lodLevel, cubeArray, cubeData, bound, j);

                                    ICube cube = Context.instance().getCubeRegistry().getCubeFormId(avegC[5]);

                                    int listIndex = 0;
                                    if (multipassSkinRendering) {
                                        if (cube.isGlowing() && !cube.needsPostRender()) {
                                            listIndex = 1;
                                        }
                                        if (cube.needsPostRender() && !cube.isGlowing()) {
                                            listIndex = 2;
                                        }
                                        if (cube.isGlowing() && cube.needsPostRender()) {
                                            listIndex = 3;
                                        }
                                    } else {
                                        if (cube.isGlowing()) {
                                            listIndex = 1;
                                        }
                                    }
                                    int lodIndex = ((lod) * ClientProxy.getNumberOfRenderLayers()) + listIndex;

                                    BakedFace ver = new BakedFace(
                                            (byte) (ix + bound.getX()), (byte) (iy + bound.getY()), (byte) (iz + bound.getZ()),
                                            avegC[0], avegC[1], avegC[2],
                                            avegC[3], avegC[4], (byte) j, lodLevel);
                                    renderLists[lodIndex].add(ver);
                                }
                            }
                        }
                    }

                }
            }
        }
        baked.setVertexLists(renderLists);
    }

    private static boolean getAverageFaceFlags(int x, int y, int z, byte lodLevel, int[][][] cubeArray, SkinCubeData cubeData, Rectangle3D partBounds, int face) {
        for (int ix = 0; ix < lodLevel; ix++) {
            for (int iy = 0; iy < lodLevel; iy++) {
                for (int iz = 0; iz < lodLevel; iz++) {
                    int index = FaceCuller.getIndexForLocation(ix + x, iy + y, iz + z, partBounds, cubeArray) - 1;
                    if (index != -1) {
                        if (cubeData.getFaceFlags(index).get(face)) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    private static byte[] getAverageRGBAT(int x, int y, int z, byte lodLevel, int[][][] cubeArray,
                                          SkinCubeData cubeData, Rectangle3D partBounds, int face) {
        int count = 0;
        int r = 0, g = 0, b = 0, a = 0;
        int[] paintTypes = new int[256];
        int[] cubeTypes = new int[256];
        for (int ix = 0; ix < lodLevel; ix++) {
            for (int iy = 0; iy < lodLevel; iy++) {
                for (int iz = 0; iz < lodLevel; iz++) {
                    int index = FaceCuller.getIndexForLocation(ix + x, iy + y, iz + z, partBounds, cubeArray) - 1;
                    if (index != -1) {
                        if (cubeData.getFaceFlags(index).get(face)) {//if this face is visible
                            count += 1;
                            r += (cubeData.getCubeColourR(index)[face] & 0xFF);
                            g += (cubeData.getCubeColourG(index)[face] & 0xFF);
                            b += (cubeData.getCubeColourB(index)[face] & 0xFF);
                            if (cubeData.getCube(index).needsPostRender()) {
                                a += 127;
                            } else {
                                a += 255;
                            }
                            paintTypes[cubeData.getCubePaintType(index)[face] & 0xFF] += 1;
                            cubeTypes[cubeData.getCubeId(index) & 0xFF] += 1;
                        }
                    }
                }
            }
        }
        byte[] rgbat = new byte[6];
        if (count != 0) {
            rgbat[0] = (byte) (r / count);
            rgbat[1] = (byte) (g / count);
            rgbat[2] = (byte) (b / count);
            rgbat[3] = (byte) (a / count);

            int commonPaintTypeIndex = 0;
            int mostPaintTypes = 0;
            for (int i = 0; i < paintTypes.length; i++) {
                if (paintTypes[i] > mostPaintTypes) {
                    mostPaintTypes = paintTypes[i];
                    commonPaintTypeIndex = i;
                }
            }
            rgbat[4] = (byte) commonPaintTypeIndex;

            int commonCubeTypesIndex = 0;
            int mostCubeTypes = 0;
            for (int i = 0; i < cubeTypes.length; i++) {
                if (cubeTypes[i] > mostCubeTypes) {
                    mostCubeTypes = cubeTypes[i];
                    commonCubeTypesIndex = i;
                }
            }
            rgbat[5] = (byte) commonCubeTypesIndex;

        }
        return rgbat;
    }
}
