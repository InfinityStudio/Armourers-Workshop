package net.skin43d.impl.skin;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.bakery.BakedPart;
import net.skin43d.impl.cubes.ICube;
import net.skin43d.impl.cubes.LegacyCubeHelper;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.ForgeDirection;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import net.skin43d.skin3d.Skin3D;
import net.skin43d.impl.cubes.CubeMarkerData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class SkinPart implements Skin3D.Part {
    private SkinPartType skinPart;
    private Rectangle3D partBounds;

    private Data cubeData;
    private List<CubeMarkerData> markerBlocks;

    @SideOnly(Side.CLIENT)
    private BakedPart bakedPart;

    public SkinPart(Data cubeData, SkinPartType skinPart, List<CubeMarkerData> markerBlocks) {
        this.cubeData = cubeData;
        this.skinPart = skinPart;
        this.markerBlocks = markerBlocks;
        setupPartBounds();
    }

    @SideOnly(Side.CLIENT)
    public void setBakedPart(BakedPart bakedPart) {
        this.bakedPart = bakedPart;
    }

    @SideOnly(Side.CLIENT)
    public BakedPart getBakedPart() {
        return bakedPart;
    }

    @SideOnly(Side.CLIENT)
    public int getModelCount() {
        return bakedPart.getModelCount();
    }

    private void setupPartBounds() {
        int minX = 127;
        int maxX = -127;

        int minY = 127;
        int maxY = -127;

        int minZ = 127;
        int maxZ = -127;

        for (int i = 0; i < cubeData.getCubeCount(); i++) {
            byte[] loc = cubeData.getCubeLocation(i);
            int x = loc[0];
            int y = loc[1];
            int z = loc[2];

            minX = Math.min(x, minX);
            maxX = Math.max(x, maxX);
            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
            minZ = Math.min(z, minZ);
            maxZ = Math.max(z, maxZ);
        }
        partBounds = new Rectangle3D(minX, minY, minZ, maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
    }

    public Data getCubeData() {
        return cubeData;
    }

    public void clearCubeData() {
        cubeData = null;
    }

    public Rectangle3D getPartBounds() {
        return partBounds;
    }

    @Override
    public SkinPartType getPartType() {
        return this.skinPart;
    }

    public List<CubeMarkerData> getMarkerBlocks() {
        return markerBlocks;
    }

    @Override
    public int getMarkerCount() {
        return markerBlocks.size();
    }

    @Override
    public Point3D getMarker(int index) {
        if (index >= 0 & index < markerBlocks.size()) {
            CubeMarkerData cmd = markerBlocks.get(index);
            return new Point3D(cmd.x, cmd.y, cmd.z);
        }
        return null;
    }

    @Override
    public ForgeDirection getMarkerSide(int index) {
        if (index >= 0 & index < markerBlocks.size()) {
            CubeMarkerData cmd = markerBlocks.get(index);
            return ForgeDirection.getOrientation(cmd.meta - 1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SkinPart [cubeData=" + cubeData + ", markerBlocks=" + markerBlocks + ", skinPart=" + skinPart.getRegistryName() + "]";
    }

    public static class Data {
        private byte[] cubeId;
        private byte[] cubeLocX;
        private byte[] cubeLocY;
        private byte[] cubeLocZ;
        private byte[][] cubeColourR;
        private byte[][] cubeColourG;
        private byte[][] cubeColourB;
        private byte[][] cubePaintType;

        @SideOnly(Side.CLIENT)
        private BitSet[] faceFlags;

        @SideOnly(Side.CLIENT)
        public void setFaceFlags(int index, BitSet faceFlags) {
            this.faceFlags[index] = faceFlags;
        }

        @SideOnly(Side.CLIENT)
        public BitSet getFaceFlags(int index) {
            return faceFlags[index];
        }

        @SideOnly(Side.CLIENT)
        public void setupFaceFlags() {
            faceFlags = new BitSet[getCubeCount()];
            for (int i = 0; i < getCubeCount(); i++)
                faceFlags[i] = new BitSet(6);
        }

        public void setCubeCount(int count) {
            cubeId = new byte[count];
            cubeLocX = new byte[count];
            cubeLocY = new byte[count];
            cubeLocZ = new byte[count];
            cubeColourR = new byte[count][6];
            cubeColourG = new byte[count][6];
            cubeColourB = new byte[count][6];
            cubePaintType = new byte[count][6];
        }

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
            return Context.instance().getCubeRegistry().getCubeFormId(cubeId[index]);
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

        public void setCubePaintType(int index, int side, byte paintType) {
            cubePaintType[index][side] = paintType;
        }

        public byte getCubePaintType(int index, int side) {
            return cubePaintType[index][side];
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

        public void writeToStream(DataOutput stream) throws IOException {
            stream.writeInt(cubeId.length);
            for (int i = 0; i < getCubeCount(); i++) {
                stream.writeByte(cubeId[i]);
                stream.writeByte(cubeLocX[i]);
                stream.writeByte(cubeLocY[i]);
                stream.writeByte(cubeLocZ[i]);
                for (int side = 0; side < 6; side++) {
                    stream.writeByte(cubeColourR[i][side]);
                    stream.writeByte(cubeColourG[i][side]);
                    stream.writeByte(cubeColourB[i][side]);
                    stream.writeByte(cubePaintType[i][side]);
                }
            }
        }

        public void readFromStream(DataInput stream, int version, SkinPartType skinPart) throws IOException, InvalidCubeTypeException {
            int size = stream.readInt();
            setCubeCount(size);
            for (int i = 0; i < getCubeCount(); i++) {
                if (version < 10) {
                    LegacyCubeHelper.loadLegacyCubeData(this, i, stream, version, skinPart);
                    for (int side = 0; side < 6; side++) {
                        cubePaintType[i][side] = (byte) 255;
                    }
                } else {
                    cubeId[i] = stream.readByte();
                    cubeLocX[i] = stream.readByte();
                    cubeLocY[i] = stream.readByte();
                    cubeLocZ[i] = stream.readByte();
                    for (int side = 0; side < 6; side++) {
                        cubeColourR[i][side] = stream.readByte();
                        cubeColourG[i][side] = stream.readByte();
                        cubeColourB[i][side] = stream.readByte();
                        cubePaintType[i][side] = stream.readByte();
                    }
                }
                if (version < 11) {
                    for (int side = 0; side < 6; side++) {
                        cubePaintType[i][side] = (byte) 255;
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Data [cubeId=" + Arrays.toString(cubeId) + ", cubeLocX=" + Arrays.toString(cubeLocX)
                    + ", cubeLocY=" + Arrays.toString(cubeLocY) + ", cubeLocZ=" + Arrays.toString(cubeLocZ)
                    + ", cubeColourR=" + Arrays.deepToString(cubeColourR) + ", cubeColourG=" + Arrays.deepToString(cubeColourG)
                    + ", cubeColourB=" + Arrays.deepToString(cubeColourB) + ", cubePaintType=" + Arrays.deepToString(cubePaintType)
                    + "]";
        }
    }
}
