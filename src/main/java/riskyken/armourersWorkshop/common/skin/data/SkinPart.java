package riskyken.armourersWorkshop.common.skin.data;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;
import net.skin43d.utils.Rectangle3D;
import riskyken.armourersWorkshop.api.common.skin.data.Skin3D;
import riskyken.armourersWorkshop.client.skin.ClientSkinPartData;
import riskyken.armourersWorkshop.common.skin.cubes.CubeMarkerData;

import java.util.List;

public class SkinPart implements Skin3D.Part {
    private SkinCubeData cubeData;
    private List<CubeMarkerData> markerBlocks;
    private SkinPartType skinPart;
    private Rectangle3D partBounds;

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

            minX = Math.min(x, minX);
            maxX = Math.max(x, maxX);
            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
            minZ = Math.min(z, minZ);
            maxZ = Math.max(z, maxZ);
        }
        partBounds = new Rectangle3D(minX, minY, minZ, maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
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
    public EnumFacing getMarkerSide(int index) {
        if (index >= 0 & index < markerBlocks.size()) {
            CubeMarkerData cmd = markerBlocks.get(index);
            return EnumFacing.getOrientation(cmd.meta - 1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SkinPart [cubeData=" + cubeData + ", markerBlocks=" + markerBlocks + ", skinPart=" + skinPart.getRegistryName() + "]";
    }
}
