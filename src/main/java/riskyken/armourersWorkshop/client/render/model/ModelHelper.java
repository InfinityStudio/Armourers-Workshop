package riskyken.armourersWorkshop.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.ArmourersWorkshop;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.EquipmentWardrobeData;
import riskyken.armourersWorkshop.client.ClientProxy;

import java.awt.*;

public final class ModelHelper {

    private static final float CHILD_SCALE = 2.0F;

    public static void enableChildModelScale(boolean headScale, float scale) {
        GL11.glPushMatrix();
        if (headScale) {
            GL11.glScalef(1.5F / CHILD_SCALE, 1.5F / CHILD_SCALE, 1.5F / CHILD_SCALE);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
        } else {
            GL11.glScalef(1.0F / CHILD_SCALE, 1.0F / CHILD_SCALE, 1.0F / CHILD_SCALE);
            GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F);
        }
    }

    public static void disableChildModelScale() {
        GL11.glPopMatrix();
    }

    @SideOnly(Side.CLIENT)
    public static int getLocalPlayersSkinColour() {
        PlayerPointer playerPointer = new PlayerPointer(Minecraft.getMinecraft().thePlayer);
        EquipmentWardrobeData ewd = ArmourersWorkshop.proxy.getEquipmentWardrobeProvider().getEquipmentWardrobeData(playerPointer);
        if (ewd != null) {
            return ewd.skinColour;
        }
        return Color.decode("#F9DFD2").getRGB();
    }

    @SideOnly(Side.CLIENT)
    public static int getLocalPlayersHairColour() {
        PlayerPointer playerPointer = new PlayerPointer(Minecraft.getMinecraft().thePlayer);
        EquipmentWardrobeData ewd = ArmourersWorkshop.proxy.getEquipmentWardrobeProvider().getEquipmentWardrobeData(playerPointer);
        if (ewd != null) {
            return ewd.hairColour;
        }
        return Color.decode("#804020").getRGB();
    }

    @SideOnly(Side.CLIENT)
    public static byte[] getLocalPlayerExtraColours() {
        int skin = getLocalPlayersSkinColour();
        int hair = getLocalPlayersHairColour();
        byte[] ec = new byte[6];

        ec[0] = (byte) (skin >>> 16 & 0xFF);
        ec[1] = (byte) (skin >>> 8 & 0xFF);
        ec[2] = (byte) (skin & 0xFF);
        ec[3] = (byte) (hair >>> 16 & 0xFF);
        ec[4] = (byte) (hair >>> 8 & 0xFF);
        ec[5] = (byte) (hair & 0xFF);
        return ec;
    }
}
