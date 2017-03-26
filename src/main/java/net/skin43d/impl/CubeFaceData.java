package net.skin43d.impl;

import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.cubes.ICube;

/**
 * The byte array returned by these function are all representing the data of 6 face/side of cube
 */
public class CubeFaceData {
    private byte[] cubeId;
    private byte[] cubeLocX;
    private byte[] cubeLocY;
    private byte[] cubeLocZ;
    private byte[][] cubeColourR;
    private byte[][] cubeColourG;
    private byte[][] cubeColourB;
    private byte[][] cubePaintType;

    public int getCubeCount() {
        return cubeId.length;
    }

    public void setCubeId(int index, byte id) {
        cubeId[index] = id;
    }

    public byte getCubeId(int index) {
        return cubeId[index];
    }

    public ICube getCube(int index) {
        return CubeRegistry.INSTANCE.getCubeFormId(cubeId[index]);
    }

    public void setCubeColour(int index, int side, byte r, byte g, byte b) {
        cubeColourR[index][side] = r;
        cubeColourG[index][side] = g;
        cubeColourB[index][side] = b;
    }

    public byte[] getCubeColour(int index, int side) {
        return new byte[]{cubeColourR[index][side], cubeColourG[index][side], cubeColourB[index][side]};
    }

    public byte[] getCubeColourR(int index) {
        return new byte[]{
                cubeColourR[index][0],
                cubeColourR[index][1],
                cubeColourR[index][2],
                cubeColourR[index][3],
                cubeColourR[index][4],
                cubeColourR[index][5],
        };
    }

    public byte[] getCubeColourG(int index) {
        return new byte[]{
                cubeColourG[index][0],
                cubeColourG[index][1],
                cubeColourG[index][2],
                cubeColourG[index][3],
                cubeColourG[index][4],
                cubeColourG[index][5],
        };
    }

    public byte[] getCubeColourB(int index) {
        return new byte[]{
                cubeColourB[index][0],
                cubeColourB[index][1],
                cubeColourB[index][2],
                cubeColourB[index][3],
                cubeColourB[index][4],
                cubeColourB[index][5],
        };
    }

    public void setCubeLocation(int index, byte x, byte y, byte z) {
        cubeLocX[index] = x;
        cubeLocY[index] = y;
        cubeLocZ[index] = z;
    }

    public byte[] getCubeLocation(int index) {
        return new byte[]{cubeLocX[index], cubeLocY[index], cubeLocZ[index]};
    }

    public byte[] getCubePaintType(int index) {
        return new byte[]{
                cubePaintType[index][0],
                cubePaintType[index][1],
                cubePaintType[index][2],
                cubePaintType[index][3],
                cubePaintType[index][4],
                cubePaintType[index][5],
        };
    }
}
