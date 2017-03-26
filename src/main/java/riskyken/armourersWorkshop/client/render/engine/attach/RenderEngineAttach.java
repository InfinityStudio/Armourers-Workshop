package riskyken.armourersWorkshop.client.render.engine.attach;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import riskyken.armourersWorkshop.ArmourersWorkshop;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;
import riskyken.armourersWorkshop.client.render.RenderEngine;
import riskyken.armourersWorkshop.common.skin.type.SkinTypeRegistry;
import riskyken.armourersWorkshop.utils.ModLogger;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author ci010
 */
public class RenderEngineAttach implements RenderEngine {
    private final Set<ModelBiped> attachedBipedSet = Collections.newSetFromMap(new WeakHashMap<ModelBiped, Boolean>());

    protected EntityPlayer targetPlayer;

    public RenderEngineAttach() {
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Post event) {
        targetPlayer = null;
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
        ISkinTypeRegistry reg = ArmourersWorkshop.instance().getSkinRegistry();
        attachedBipedSet.add(modelBiped);
        modelBiped.bipedHead.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinHead, reg.getSkinPartTypeFromName("armourers:head.base"), this));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, reg.getSkinPartTypeFromName("armourers:chest.base"), this));
        modelBiped.bipedLeftArm.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, reg.getSkinPartTypeFromName("armourers:chest.leftArm"), this));
        modelBiped.bipedRightArm.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinChest, reg.getSkinPartTypeFromName("armourers:chest.rightArm"), this));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, reg.getSkinPartTypeFromName("armourers:legs.leftLeg"), this));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, reg.getSkinPartTypeFromName("armourers:legs.rightLeg"), this));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinLegs, reg.getSkinPartTypeFromName("armourers:legs.skirt"), this));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinFeet, reg.getSkinPartTypeFromName("armourers:feet.leftFoot"), this));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinFeet, reg.getSkinPartTypeFromName("armourers:feet.rightFoot"), this));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinWings, reg.getSkinPartTypeFromName("armourers:wings.leftWing"), this));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, SkinTypeRegistry.skinWings, reg.getSkinPartTypeFromName("armourers:wings.rightWing"), this));
        ModLogger.log(String.format("Added model render attachment to %s", modelBiped.toString()));
        ModLogger.log(String.format("Using player renderer %s", renderPlayer.toString()));
    }

    @Override
    public void deploy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void dispose() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
