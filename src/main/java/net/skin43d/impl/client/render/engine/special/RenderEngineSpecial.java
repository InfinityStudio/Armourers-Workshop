package net.skin43d.impl.client.render.engine.special;

import com.google.common.collect.ImmutableMap;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.model.ModelBiped;
import net.skin43d.SkinProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.skin43d.impl.Context;
import net.skin43d.skin3d.SkinType;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.SkinTypeRegistry;
import net.skin43d.impl.client.render.engine.RenderEngine;
import riskyken.armourersWorkshop.client.render.core.skin.AbstractModelSkin;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import removequ.EquipmentWardrobeData;
import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.awt.*;

/**
 * @author ci010
 */
public class RenderEngineSpecial implements RenderEngine {
    public RenderEngineSpecial() {
        buildMap();
    }

    @SubscribeEvent
    public void onRenderSpecialsPost(RenderPlayerEvent.Specials.Post event) {
        EntityPlayer player = event.entityPlayer;
        RenderPlayer render = event.renderer;
        if (player.getGameProfile() == null)
            return;

        double distance = Minecraft.getMinecraft().thePlayer.getDistance(player.posX, player.posY, player.posZ);
        if (distance > ConfigHandlerClient.maxSkinRenderDistance) return;

        SkinProvider skinProvider = Context.instance().getSkinProvider();
        SkinTypeRegistry skinRegistry = Context.instance().getSkinRegistry();
        EquipmentWardrobeData ewd = Context.instance().getEquipmentWardrobeProvider().getEquipmentWardrobeData(new PlayerPointer(player));
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

        for (SkinType type : skinRegistry.getAllSkinTypes())
            render(skinProvider, type, getModelForEquipmentType(type), player, distance, extraColours, render.modelBipedMain);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    private void render(SkinProvider skinProvider, SkinType type, AbstractModelSkin modelBipedMain,
                        EntityPlayer player, double distance, byte[] extraColours, ModelBiped parent) {
        Skin data = skinProvider.getSkinInfoForEntity(player, type);
        ISkinDye dye = skinProvider.getPlayerDyeData(player, type);
        if (data != null)
            modelBipedMain.render(player, parent, data, false, dye, extraColours, false, distance, true);
    }

    @Override
    public void deploy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void dispose() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    private void buildMap() {
        SkinTypeRegistry registry = Context.instance().getSkinRegistry();
        skinImmutableMap = ImmutableMap.<String, AbstractModelSkin>builder()
                .put(registry.getSkinChest().getRegistryName(), new ModelSkinChest())
                .put(registry.getSkinHead().getRegistryName(), new ModelSkinHead())
                .put(registry.getSkinLegs().getRegistryName(), new ModelSkinLegs())
                .put(registry.getSkinSkirt().getRegistryName(), new ModelSkinSkirt())
                .put(registry.getSkinFeet().getRegistryName(), new ModelSkinFeet())
                .put(registry.getSkinSword().getRegistryName(), new ModelSkinSword())
                .put(registry.getSkinBow().getRegistryName(), new ModelSkinBow())
                .put(registry.getSkinWings().getRegistryName(), new ModelSkinWings())
                .build();
    }

    private ImmutableMap<String, AbstractModelSkin> skinImmutableMap;

    public AbstractModelSkin getModelForEquipmentType(SkinType skinType) {
        return skinImmutableMap.get(skinType);
    }

//    public boolean renderEquipmentPartFromStack(Entity entity, ItemStack stack, ModelBiped modelBiped, byte[] extraColours, double distance, boolean doLodLoading) {
//        SkinPointer skinPointer = SkinNBTHelper.getSkinPointerFromStack(stack);
//        if (skinPointer == null) {
//            return false;
//        }
//        Skin data = getSkinInfoForEntity(skinPointer);
//        return renderEquipmentPart(entity, modelBiped, data, skinPointer.getSkinDye(), extraColours, distance, doLodLoading);
//    }
//
//    public boolean renderEquipmentPartFromStack(ItemStack stack, ModelBiped modelBiped, byte[] extraColours, double distance, boolean doLodLoading) {
//        SkinPointer skinPointer = SkinNBTHelper.getSkinPointerFromStack(stack);
//        if (skinPointer == null) {
//            return false;
//        }
//        Skin data = getSkinInfoForEntity(skinPointer);
//        return renderEquipmentPart(null, modelBiped, data, skinPointer.getSkinDye(), extraColours, distance, doLodLoading);
//    }
//
//    public boolean renderEquipmentPartFromSkinPointer(ISkinPointer skinPointer, float limb1, float limb2, float limb3, float headY, float headX) {
//        Skin data = getSkinInfoForEntity(skinPointer);
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
