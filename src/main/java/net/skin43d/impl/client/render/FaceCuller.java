package net.skin43d.impl.client.render;

import net.minecraftforge.common.util.ForgeDirection;
import net.skin43d.utils.Rectangle3D;
import riskyken.armourersWorkshop.common.skin.cubes.CubeRegistry;
import riskyken.armourersWorkshop.common.skin.data.SkinCubeData;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;

import java.util.*;

/**
 * @author ci010
 */
public class FaceCuller {

    /**
     * @return indexSpace
     */
    static int[][][] cullFacesPre(SkinPart skinPart) {
        SkinCubeData cubeData = skinPart.getCubeData();
        Rectangle3D bounds = skinPart.getPartBounds();

        skinPart.getClientSkinPartData().totalCubesInPart = new int[CubeRegistry.INSTANCE.getTotalCubes()];

        int[][][] cubeSpace3D = new int[bounds.getWidth()][bounds.getHeight()][bounds.getDepth()];

        for (int i = 0; i < cubeData.getCubeCount(); i++) {
            int cubeId = cubeData.getCubeId(i);
            byte[] cubeLoc = cubeData.getCubeLocation(i);
            skinPart.getClientSkinPartData().totalCubesInPart[cubeId] += 1;
            int x = (int) cubeLoc[0] - bounds.getX();
            int y = (int) cubeLoc[1] - bounds.getY();
            int z = (int) cubeLoc[2] - bounds.getZ();
            cubeSpace3D[x][y][z] = i + 1;
        }
        return cubeSpace3D;
    }

    static void cullFace(SkinPart part, int[][][] cubeSpace3D) {
        SkinCubeData cubeData = part.getCubeData();
        Rectangle3D bounds = part.getPartBounds();
        cubeData.setupFaceFlags();

        Stack<CubeLocation> unknownLocations = new Stack<CubeLocation>();
        Set<Integer> visited = new HashSet<Integer>();

        CubeLocation startCube = new CubeLocation(-1, -1, -1);
        unknownLocations.add(startCube);
        visited.add(startCube.hashCode());

        while (unknownLocations.size() > 0) {
            CubeLocation location = unknownLocations.pop();
            List<CubeLocation> foundLocations = checkCubesAroundLocation(cubeData, location, bounds, cubeSpace3D);
            for (CubeLocation foundLocation : foundLocations) {
                if (!visited.contains(foundLocation.hashCode())) {
                    visited.add(foundLocation.hashCode());
                    if (isCubeInSearchArea(foundLocation, bounds))
                        unknownLocations.push(foundLocation);
                }
            }
        }
    }

    private static List<CubeLocation> checkCubesAroundLocation(SkinCubeData cubeData, CubeLocation cubeLocation,
                                                               Rectangle3D partBounds, int[][][] cubeArray) {
        List<CubeLocation> openList = new ArrayList<CubeLocation>();
        ForgeDirection[] dirs = {ForgeDirection.DOWN, ForgeDirection.UP,
                ForgeDirection.SOUTH, ForgeDirection.NORTH,
                ForgeDirection.WEST, ForgeDirection.EAST};

        int index = getIndexForLocation(cubeLocation.x, cubeLocation.y, cubeLocation.z, partBounds, cubeArray);

        boolean needPostRender = false;
        if (index > 0) needPostRender = cubeData.getCube(index - 1).needsPostRender();

        for (int i = 0; i < dirs.length; i++) {
            ForgeDirection dir = dirs[i];
            int x = cubeLocation.x + dir.offsetX;
            int y = cubeLocation.y + dir.offsetY;
            int z = cubeLocation.z + dir.offsetZ;
            int tarIndex = getIndexForLocation(x, y, z, partBounds, cubeArray);

            //Add new cubes to the open list.
            if (tarIndex < 1)
                openList.add(new CubeLocation(x, y, z));
            else if (cubeData.getCube(tarIndex - 1).needsPostRender())
                openList.add(new CubeLocation(x, y, z));

            //Update the face flags if there is a block at this location.
            if (tarIndex > 0)
                flagCubeFace(x, y, z, i, partBounds, cubeArray, cubeData, needPostRender);
        }
        return openList;
    }

    static int getIndexForLocation(int x, int y, int z, Rectangle3D partBounds, int[][][] cubeArray) {
        if (x >= 0 & x < partBounds.getWidth())
            if (y >= 0 & y < partBounds.getHeight())
                if (z >= 0 & z < partBounds.getDepth())
                    return cubeArray[x][y][z];
        return 0;
    }

    private static void flagCubeFace(int x, int y, int z, int face, Rectangle3D partBounds, int[][][] cubeArray,
                                     SkinCubeData cubeData, boolean needPostRender) {
        int checkIndex = getIndexForLocation(x, y, z, partBounds, cubeArray);
        if (!needPostRender)
            cubeData.getFaceFlags(checkIndex - 1).set(face, true);
        else
            cubeData.getFaceFlags(checkIndex - 1).set(face,
                    cubeData.getCube(checkIndex - 1).needsPostRender() != needPostRender);
    }

    private static boolean isCubeInSearchArea(CubeLocation cubeLocation, Rectangle3D partBounds) {
        if (cubeLocation.x > -2 & cubeLocation.x < partBounds.getWidth() + 1) {
            if (cubeLocation.y > -2 & cubeLocation.y < partBounds.getHeight() + 1) {
                if (cubeLocation.z > -2 & cubeLocation.z < partBounds.getDepth() + 1) {
                    return true;
                }
            }
        }
        return false;
    }


    private static class CubeLocation {
        public final int x;
        public final int y;
        public final int z;

        public CubeLocation(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CubeLocation other = (CubeLocation) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            if (z != other.z)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "CubeLocation [x=" + x + ", y=" + y + ", z=" + z + "]";
        }
    }
}
