package riskyken.armourersWorkshop.common.skin.cubes;

import net.skin43d.utils.ModLogger;

import java.util.ArrayList;
import java.util.List;


public class CubeRegistry {
    private List<ICube> cubeList;

    public CubeRegistry() {
        cubeList = new ArrayList<ICube>();
        registerCube(new Cube((byte) 0));
        registerCube(new CubeGlowing((byte) 1));
        registerCube(new CubeGlass((byte) 2));
        registerCube(new CubeGlassGlowing((byte) 3));
    }

    public ICube getCubeFormId(byte id) {
        if (id >= 0 && id < cubeList.size())
            return cubeList.get(id);
        return null;
    }

    public byte getTotalCubes() {
        return (byte) cubeList.size();
    }

    private void registerCube(ICube cube) {
        cubeList.add(cube);
        ModLogger.log("Registering equipment cube - id:" + cube.getId() + " name:" + cube.getClass().getSimpleName());
    }
}
