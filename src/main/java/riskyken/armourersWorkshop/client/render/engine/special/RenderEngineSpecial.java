package riskyken.armourersWorkshop.client.render.engine.special;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.skin43d.SkinInfoProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.ArmourersWorkshop;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.client.render.RenderEngine;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import removequ.EquipmentWardrobeData;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;

import java.awt.*;

/**
 * @author ci010
 */
public class RenderEngineSpecial implements RenderEngine {

    //render for custom case... seems like not working in normal case
    private final ModelSkinChest customChest = new ModelSkinChest();
    private final ModelSkinHead customHead = new ModelSkinHead();
    private final ModelSkinLegs customLegs = new ModelSkinLegs();
    private final ModelSkinSkirt customSkirt = new ModelSkinSkirt();
    private final ModelSkinFeet customFeet = new ModelSkinFeet();
    public final ModelSkinSword customSword = new ModelSkinSword();
    public final ModelSkinBow customBow = new ModelSkinBow();
    private final ModelSkinWings customWings = new ModelSkinWings();

    public RenderEngineSpecial() {
    }

    @SubscribeEvent
    public void onRenderSpecialsPost(RenderPlayerEvent.Specials.Post event) {
//        if (ClientProxy.getSkinRenderType() != ClientProxy.SkinRenderType.RENDER_EVENT) {
//            return;
//        }
        EntityPlayer player = event.entityPlayer;
        RenderPlayer render = event.renderer;
        if (player.getGameProfile() == null) {
            return;
        }
        PlayerPointer playerPointer = new PlayerPointer(player);
//        if (!playerEquipmentMap.containsKey(playerPointer)) {
//            return;
//        }

        double distance = Minecraft.getMinecraft().thePlayer.getDistance(
                player.posX,
                player.posY,
                player.posZ);

        if (distance > ConfigHandlerClient.maxSkinRenderDistance) {
            return;
        }

        SkinInfoProvider skinProvider = ArmourersWorkshop.instance().getSkinProvider();
        EquipmentWardrobeData ewd = ArmourersWorkshop.instance().getEquipmentWardrobeProvider().getEquipmentWardrobeData(new PlayerPointer(player));
        byte[] extraColours = null;
        if (ewd != null) {
            Color skinColour = new Color(ewd.skinColour);
            Color hairColour = new Color(ewd.hairColour);
            extraColours = new byte[]{
                    (byte) skinColour.getRed(), (byte) skinColour.getGreen(), (byte) skinColour.getBlue(),
                    (byte) hairColour.getRed(), (byte) hairColour.getGreen(), (byte) hairColour.getBlue()};
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        for (int slot = 0; slot < 4; slot++) {
            for (int skinIndex = 0; skinIndex < 5; skinIndex++) {
                if (slot == SkinTypeRegistry.skinHead.getVanillaArmourSlotId()) {
                    Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinHead, skinIndex);
                    ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinHead, skinIndex);
                    if (data != null) {
                        customHead.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
                    }
                }
                if (slot == SkinTypeRegistry.skinChest.getVanillaArmourSlotId()) {
                    Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinChest, skinIndex);
                    ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinChest, skinIndex);
                    if (data != null) {
                        customChest.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
                    }
                }
                if (slot == SkinTypeRegistry.skinLegs.getVanillaArmourSlotId()) {
                    Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinLegs, skinIndex);
                    ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinLegs, skinIndex);
                    if (data != null) {
                        customLegs.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
                    }
                }
                if (slot == SkinTypeRegistry.skinSkirt.getVanillaArmourSlotId()) {
                    Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinSkirt, skinIndex);
                    ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinSkirt, skinIndex);
                    if (data != null) {
                        customSkirt.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
                    }
                }
                if (slot == SkinTypeRegistry.skinFeet.getVanillaArmourSlotId()) {
                    Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinFeet, skinIndex);
                    ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinFeet, skinIndex);
                    if (data != null) {
                        customFeet.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
                    }
                }
            }
        }
        Skin data = skinProvider.getSkin(player, SkinTypeRegistry.skinWings, 0);
        ISkinDye dye = skinProvider.getPlayerDyeData(player, SkinTypeRegistry.skinWings, 0);
        if (data != null) {
            customWings.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    @Override
    public void deploy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void dispose() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

//    public AbstractModelSkin getModelForEquipmentType(SkinType skinType) {
//        if (skinType == SkinTypeRegistry.skinHead) {
//            return customHead;
//        } else if (skinType == SkinTypeRegistry.skinChest) {
//            return customChest;
//        } else if (skinType == SkinTypeRegistry.skinLegs) {
//            return customLegs;
//        } else if (skinType == SkinTypeRegistry.skinSkirt) {
//            return customSkirt;
//        } else if (skinType == SkinTypeRegistry.skinFeet) {
//            return customFeet;
//        } else if (skinType == SkinTypeRegistry.skinSword) {
//            return customSword;
//        } else if (skinType == SkinTypeRegistry.skinBow) {
//            return customBow;
//        } else if (skinType == SkinTypeRegistry.skinWings) {
//            return customWings;
//        }
//        return null;
//    }

//    public boolean renderEquipmentPartFromStack(Entity entity, ItemStack stack, ModelBiped modelBiped, byte[] extraColours, double distance, boolean doLodLoading) {
//        SkinPointer skinPointer = SkinNBTHelper.getSkinPointerFromStack(stack);
//        if (skinPointer == null) {
//            return false;
//        }
//        Skin data = getSkin(skinPointer);
//        return renderEquipmentPart(entity, modelBiped, data, skinPointer.getSkinDye(), extraColours, distance, doLodLoading);
//    }
//
//    public boolean renderEquipmentPartFromStack(ItemStack stack, ModelBiped modelBiped, byte[] extraColours, double distance, boolean doLodLoading) {
//        SkinPointer skinPointer = SkinNBTHelper.getSkinPointerFromStack(stack);
//        if (skinPointer == null) {
//            return false;
//        }
//        Skin data = getSkin(skinPointer);
//        return renderEquipmentPart(null, modelBiped, data, skinPointer.getSkinDye(), extraColours, distance, doLodLoading);
//    }
//
//    public boolean renderEquipmentPartFromSkinPointer(ISkinPointer skinPointer, float limb1, float limb2, float limb3, float headY, float headX) {
//        Skin data = getSkin(skinPointer);
//        return renderEquipmentPartRotated(null, data, limb1, limb2, limb3, headY, headX);
//    }
//
//    public boolean renderEquipmentPart(Entity entity, ModelBiped modelBiped, Skin data, ISkinDye skinDye, byte[] extraColours, double distance, boolean doLodLoading) {
//        if (data == null) {
//            return false;
//        }
//        IEquipmentModel model = getModelForEquipmentType(data.getSkinType());
//        if (model == null) {
//            return false;
//        }
//
//        GL11.glEnable(GL11.GL_CULL_FACE);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_BLEND);
//        model.render(entity, modelBiped, data, false, skinDye, extraColours, false, distance, doLodLoading);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_CULL_FACE);
//        return true;
//    }
//
//    private boolean renderEquipmentPartRotated(Entity entity, Skin data, float limb1, float limb2, float limb3, float headY, float headX) {
//        if (data == null) {
//            return false;
//        }
//        IEquipmentModel model = getModelForEquipmentType(data.getSkinType());
//        if (model == null) {
//            return false;
//        }
//        model.render(entity, data, limb1, limb2, limb3, headY, headX);
//        return true;
//    }
}
