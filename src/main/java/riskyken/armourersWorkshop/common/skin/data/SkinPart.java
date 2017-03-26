package riskyken.armourersWorkshop.common.skin.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;
import riskyken.armourersWorkshop.api.common.skin.Rectangle3D;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.client.skin.ClientSkinPartData;
import riskyken.armourersWorkshop.common.skin.cubes.CubeMarkerData;

import java.util.List;

public class SkinPart implements Skin3D.Part {
    private Rectangle3D partBounds;
    private SkinCubeData cubeData;
    private List<CubeMarkerData> markerBlocks;
    private SkinPartType skinPart;
    @SideOnly(Side.CLIENT)
    private ClientSkinPartData clientSkinPartData;

    public SkinPart(SkinCubeData cubeData, SkinPartType skinPart, List<CubeMarkerData> markerBlocks) {
        this.cubeData = cubeData;
        this.skinPart = skinPart;
        this.markerBlocks = markerBlocks;
        setupPartBounds();
    }

    @SideOnly(Side.CLIENT)
    public void setClientSkinPartData(ClientSkinPartData clientSkinPartData) {
        this.clientSkinPartData = clientSkinPartData;
    }

    @SideOnly(Side.CLIENT)
    public ClientSkinPartData getClientSkinPartData() {
        return clientSkinPartData;
    }

    @SideOnly(Side.CLIENT)
    public int getModelCount() {
        return clientSkinPartData.getModelCount();
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

            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }

            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }

            if (z < minZ) {
                minZ = z;
            }
            if (z > maxZ) {
                maxZ = z;
            }
        }

        int xSize = maxX - minX;
        int ySize = maxY - minY;
        int zSize = maxZ - minZ;

        int xOffset = minX;
        int yOffset = minY;
        int zOffset = minZ;

        partBounds = new Rectangle3D(xOffset, yOffset, zOffset, xSize + 1, ySize + 1, zSize + 1);
    }

    public SkinCubeData getCubeData() {
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
}
