package net.skin43d.impl.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.math.MathHelper;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.DisplayList;
import net.skin43d.impl.client.render.bakery.BakedFace;

import java.util.List;

@SideOnly(Side.CLIENT)
public class BakedCubes {
    public final DisplayList[] displayList;
    public final boolean[] haveList;
    private long loadedTime;

    public BakedCubes(List<BakedFace>[] vertexLists) {
        displayList = new DisplayList[vertexLists.length];
        haveList = new boolean[vertexLists.length];
        for (int i = 0; i < displayList.length; i++) {
            if (vertexLists[i].size() > 0) {
                displayList[i] = new DisplayList();
                haveList[i] = true;
            } else {
                haveList[i] = false;
            }
        }
    }

    public int length() {
        return displayList.length;
    }

    public boolean hasList(int idx) {
        return haveList[idx];
    }

    public DisplayList getDisplayList(int idx) {
        return displayList[idx];
    }

    public void setLoaded() {
        loadedTime = System.currentTimeMillis();
    }

    public int getLoadingLod() {
        long time = System.currentTimeMillis();
        if (time < loadedTime + 500) {
            long timePassed = time - loadedTime;
            return MathHelper.clamp_int((Context.instance().getMaxLodLevel() + 1) - ((int) (timePassed / 125F) + 1), 0,
                    Context.instance().getMaxLodLevel() + 1);
        }
        return 0;
    }

    public void cleanUpDisplayLists() {
        for (int i = 0; i < displayList.length; i++) {
            if (haveList[i]) {
                displayList[i].cleanup();
            }
        }
    }
}
