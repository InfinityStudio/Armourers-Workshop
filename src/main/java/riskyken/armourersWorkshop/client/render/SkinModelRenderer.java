package riskyken.armourersWorkshop.client.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cijhn.SkinIdentity;
import net.cijhn.SkinProvider;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import riskyken.armourersWorkshop.api.common.skin.IEntityEquipment;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.ClientProxy;
import riskyken.armourersWorkshop.client.render.model.ModelRendererAttachment;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.data.PlayerPointer;
import riskyken.armourersWorkshop.common.skin.EntityEquipmentData;
import riskyken.armourersWorkshop.common.skin.EquipmentWardrobeData;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPointer;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;
import riskyken.armourersWorkshop.utils.ModLogger;
import riskyken.armourersWorkshop.utils.SkinNBTHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Helps render custom equipment on the player and other entities.
 * <p>
 * TODO Clean up this class it's a mess >:|
 * <p>
 * Seems like this is the major render logic handler/dispatcher
 *
 * @author RiskyKen
 */
@SideOnly(Side.CLIENT)
public final class SkinModelRenderer {

    public static SkinModelRenderer INSTANCE;

    public static void init() {
        INSTANCE = new SkinModelRenderer();
    }

    private final Set<ModelBiped> attachedBipedSet;

    //render for custom case... seems like not working in normal case
//    public final ModelSkinChest customChest = new ModelSkinChest();
//    public final ModelSkinHead customHead = new ModelSkinHead();
//    public final ModelSkinLegs customLegs = new ModelSkinLegs();
//    public final ModelSkinSkirt customSkirt = new ModelSkinSkirt();
//    public final ModelSkinFeet customFeet = new ModelSkinFeet();
//    public final ModelSkinSword customSword = new ModelSkinSword();
//    public final ModelSkinBow customBow = new ModelSkinBow();
//    public final ModelSkinWings customWings = new ModelSkinWings();

    public EntityPlayer targetPlayer = null;

    private SkinModelRenderer() {
        MinecraftForge.EVENT_BUS.register(this);
        attachedBipedSet = Collections.newSetFromMap(new WeakHashMap<ModelBiped, Boolean>());
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        EntityPlayer player = targetPlayer = event.entityPlayer;
        if (player.getGameProfile() == null)
            return;
        attachModelsToBiped(event.renderer.modelBipedMain, event.renderer);
//        PlayerPointer playerPointer = new PlayerPointer(player);
        //Limit the players limbs if they have a skirt equipped.
        //A proper lady should not swing her legs around!
//        if (isPlayerWearingSkirt(playerPointer)) {
//            EquipmentWardrobeData ewd = ClientProxy.equipmentWardrobeHandler.getEquipmentWardrobeData(playerPointer);
//            if (ewd != null && ewd.limitLimbs) {
//                if (player.limbSwingAmount > 0.25F) {
//                    player.limbSwingAmount = 0.25F;
//                    player.prevLimbSwingAmount = 0.25F;
//                }
//            }
//        }
    }

    private void attachModelsToBiped(ModelBiped modelBiped, RenderPlayer renderPlayer) {
        if (attachedBipedSet.contains(modelBiped))
            return;
        attachedBipedSet.add(modelBiped);
        modelBiped.bipedHead.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinHead, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:head.base")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:chest.base")));
        modelBiped.bipedLeftArm.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:chest.leftArm")));
        modelBiped.bipedRightArm.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:chest.rightArm")));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:legs.leftLeg")));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:legs.rightLeg")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:legs.skirt")));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinFeet, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:feet.leftFoot")));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinFeet, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:feet.rightFoot")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinWings, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:wings.leftWing")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinWings, SkinTypeRegistry.INSTANCE.getSkinPartFromRegistryName("armourers:wings.rightWing")));
        ModLogger.log(String.format("Added model render attachment to %s", modelBiped.toString()));
        ModLogger.log(String.format("Using player renderer %s", renderPlayer.toString()));
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Post event) {
        targetPlayer = null;
    }

//    @SubscribeEvent
//    public void onRenderSpecialsPost(RenderPlayerEvent.Specials.Post event) {
//        if (ClientProxy.getSkinRenderType() != SkinRenderType.RENDER_EVENT) {
//            return;
//        }
//        EntityPlayer player = event.entityPlayer;
//        RenderPlayer render = event.renderer;
//        if (player.getGameProfile() == null) {
//            return;
//        }
//        PlayerPointer playerPointer = new PlayerPointer(player);
//        if (!playerEquipmentMap.containsKey(playerPointer)) {
//            return;
//        }
//
//        double distance = Minecraft.getMinecraft().thePlayer.getDistance(
//                player.posX,
//                player.posY,
//                player.posZ);
//
//        if (distance > ConfigHandlerClient.maxSkinRenderDistance) {
//            return;
//        }
//
//        EquipmentWardrobeData ewd = ClientProxy.equipmentWardrobeHandler.getEquipmentWardrobeData(new PlayerPointer(player));
//        byte[] extraColours = null;
//        if (ewd != null) {
//            Color skinColour = new Color(ewd.skinColour);
//            Color hairColour = new Color(ewd.hairColour);
//            extraColours = new byte[]{
//                    (byte) skinColour.getRed(), (byte) skinColour.getGreen(), (byte) skinColour.getBlue(),
//                    (byte) hairColour.getRed(), (byte) hairColour.getGreen(), (byte) hairColour.getBlue()};
//        }
//        GL11.glPushMatrix();
//        GL11.glEnable(GL11.GL_CULL_FACE);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_BLEND);
//        for (int slot = 0; slot < 4; slot++) {
//
//            for (int skinIndex = 0; skinIndex < 5; skinIndex++) {
//                if (slot == SkinTypeRegistry.skinHead.getVanillaArmourSlotId()) {
//                    Skin data = getSkin(player, SkinTypeRegistry.skinHead, skinIndex);
//                    ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinHead, skinIndex);
//                    if (data != null) {
//                        customHead.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//                    }
//                }
//                if (slot == SkinTypeRegistry.skinChest.getVanillaArmourSlotId()) {
//                    Skin data = getSkin(player, SkinTypeRegistry.skinChest, skinIndex);
//                    ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinChest, skinIndex);
//                    if (data != null) {
//                        customChest.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//                    }
//                }
//                if (slot == SkinTypeRegistry.skinLegs.getVanillaArmourSlotId()) {
//                    Skin data = getSkin(player, SkinTypeRegistry.skinLegs, skinIndex);
//                    ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinLegs, skinIndex);
//                    if (data != null) {
//                        customLegs.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//                    }
//                }
//                if (slot == SkinTypeRegistry.skinSkirt.getVanillaArmourSlotId()) {
//                    Skin data = getSkin(player, SkinTypeRegistry.skinSkirt, skinIndex);
//                    ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinSkirt, skinIndex);
//                    if (data != null) {
//                        customSkirt.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//                    }
//                }
//                if (slot == SkinTypeRegistry.skinFeet.getVanillaArmourSlotId()) {
//                    Skin data = getSkin(player, SkinTypeRegistry.skinFeet, skinIndex);
//                    ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinFeet, skinIndex);
//                    if (data != null) {
//                        customFeet.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//                    }
//                }
//            }
//        }
//        Skin data = getSkin(player, SkinTypeRegistry.skinWings, 0);
//        ISkinDye dye = getPlayerDyeData(player, SkinTypeRegistry.skinWings, 0);
//        if (data != null) {
//            customWings.render(player, render.modelBipedMain, data, false, dye, extraColours, false, distance, true);
//        }
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_CULL_FACE);
//        GL11.glPopMatrix();
//    }
//
//    public AbstractModelSkin getModelForEquipmentType(ISkinType skinType) {
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
