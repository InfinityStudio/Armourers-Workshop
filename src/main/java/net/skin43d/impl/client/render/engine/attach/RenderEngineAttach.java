package net.skin43d.impl.client.render.engine.attach;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.skin43d.utils.ForgeDirection;
import net.skin43d.SkinProvider;
import net.skin43d.impl.Context;
import net.skin43d.impl.client.render.nbake.PartTranslator;
import net.skin43d.impl.client.render.engine.special.ModelSkinWings;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.utils.Point3D;
import org.lwjgl.opengl.GL11;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinDye;
import riskyken.armourersWorkshop.api.common.skin.type.SkinTypeRegistry;
import net.skin43d.impl.client.render.engine.RenderEngine;
import net.skin43d.utils.ModLogger;
import riskyken.armourersWorkshop.client.render.core.SkinPartRenderer;
import riskyken.armourersWorkshop.common.config.ConfigHandlerClient;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;

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
        EntityPlayer player = targetPlayer = event.getEntityPlayer();
        if (player.getGameProfile() == null)
            return;
        attachModelsToBiped(event.getRenderer().getMainModel(), event.getRenderer());
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
        SkinTypeRegistry reg = Context.instance().getSkinRegistry();
        attachedBipedSet.add(modelBiped);
        modelBiped.bipedHead.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:head.base")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:chest.base")));
        modelBiped.bipedLeftArm.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:chest.leftArm")));
        modelBiped.bipedRightArm.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:chest.rightArm")));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:legs.leftLeg")));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:legs.rightLeg")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:legs.skirt")));
        modelBiped.bipedLeftLeg.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:feet.leftFoot")));
        modelBiped.bipedRightLeg.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:feet.rightFoot")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:wings.leftWing")));
        modelBiped.bipedBody.addChild(new ModelRendererAttachment(modelBiped, reg.getSkinPartTypeFromName("armourers:wings.rightWing")));
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

    /**
     * A ModelRenderer that is attached to each ModelRenderer on the
     * players ModelBiped as a sub part.
     *
     * @author RiskyKen
     */
    @SideOnly(Side.CLIENT)
    public class ModelRendererAttachment extends ModelRenderer {
        private final SkinPartType skinPart;
        private final Minecraft mc;
        private PartTranslator partTranslator;

        public ModelRendererAttachment(ModelBiped modelBase, SkinPartType skinPart) {
            super(modelBase);
            this.mc = Minecraft.getMinecraft();
            this.skinPart = skinPart;
            addBox(0, 0, 0, 0, 0, 0);
        }

        private void handleWing(SkinPart partData, float scale, double angle) {
            GL11.glTranslated(0, 0, scale * 2);
            Point3D point = new Point3D(0, 0, 0);
            ForgeDirection axis = ForgeDirection.DOWN;

            if (partData.getMarkerCount() > 0) {
                point = partData.getMarker(0);
                axis = partData.getMarkerSide(0);
            }

            GL11.glTranslated(scale * 0.5F, scale * 0.5F, scale * 0.5F);
            GL11.glTranslated(scale * point.getX(), scale * point.getY(), scale * point.getZ());
            if (skinPart.getRegistryName().equals("armourers:wings.leftWing")) {
                switch (axis) {
                    case UP:
                    case DOWN:
                        GL11.glRotated(angle, 0, 1, 0);
                        break;
                    case NORTH:
                    case EAST:
                        GL11.glRotated(angle, 1, 0, 0);
                        break;
                    case SOUTH:
                    case WEST:
                        GL11.glRotated(angle, 0, 0, 1);
                        break;
                    case UNKNOWN:
                        break;
                }
            } else {
                switch (axis) {
                    case UP:
                        GL11.glRotated(-angle, 0, 1, 0);
                        break;
                    case DOWN:
                        GL11.glRotated(-angle, 0, 1, 0);
                        break;
                    case NORTH:
                        GL11.glRotated(-angle, 1, 0, 0);
                        break;
                    case EAST:
                        GL11.glRotated(-angle, 1, 0, 0);
                        break;
                    case SOUTH:
                        GL11.glRotated(-angle, 0, 0, 1);
                        break;
                    case WEST:
                        GL11.glRotated(angle, 1, 0, 0);
                        break;
                    case UNKNOWN:
                        break;
                }
            }
            GL11.glTranslated(scale * -point.getX(), scale * -point.getY(), scale * -point.getZ());
            GL11.glTranslated(scale * -0.5F, scale * -0.5F, scale * -0.5F);
        }

        @Override
        public void render(float scale) {
            mc.mcProfiler.startSection("armourers player render");
            EntityPlayer player = targetPlayer;
            if (player == null) {
                mc.mcProfiler.endSection();
                return;
            }

            double distance;
            if (Minecraft.getMinecraft().thePlayer == player)
                distance = 0;
            else
                distance = Minecraft.getMinecraft().thePlayer.getDistance(player.posX, player.posY, player.posZ);
            if (distance > ConfigHandlerClient.maxSkinRenderDistance) return;

            //TODO not really sure what EquipmentWardrobeData will handle(except color). Since it has the relationship with slot
            // which will be removed, I comment this out first.
            byte[] extraColours = null;
            //extra color: [skinRed, skinG, skinB, hairR, hairG, hairB]
            //        EquipmentWardrobeData ewd = ClientProxy.equipmentWardrobeHandler.getEquipmentWardrobeData(new PlayerPointer(player));
            //        if (ewd != null) {
            //            Color skinColour = new Color(ewd.skinColour);
            //            Color hairColour = new Color(ewd.hairColour);
            //            extraColours = new byte[]{
            //                    (byte) skinColour.getRed(), (byte) skinColour.getGreen(), (byte) skinColour.getBlue(),
            //                    (byte) hairColour.getRed(), (byte) hairColour.getGreen(), (byte) hairColour.getBlue()};
            //        }
            SkinProvider provider = Context.instance().getSkinProvider();
            SkinTypeRegistry reg = Context.instance().getSkinRegistry();

//            BakeSkinPart bakedModel = provider.getBakedModel(player, skinPart.getBaseType(), skinPart);
            Skin data = provider.getSkinInfoForEntity(player, skinPart.getBaseType());
            SkinPart partData = null;
            if (data != null) partData = data.getSkinPartFromType(skinPart);

            if (partData != null) {
                GL11.glPushMatrix();
                //            partTranslator.translate();
                if (skinPart.getBaseType() == reg.getSkinLegs() && skinPart.getRegistryName().equals("armourers:legs.skirt")) {
                    GL11.glTranslatef(0, 12 * scale, 0);
                    if (player.isSneaking()) {
                        GL11.glRotatef(-30, 1, 0, 0);
                        GL11.glTranslatef(0, -1.25F * scale, -2F * scale);
                    }
                    if (player.isRiding()) GL11.glRotated(-70, 1F, 0F, 0F);
                }
                if (skinPart.getBaseType() == reg.getSkinWings()) {
                    double angle = ModelSkinWings.getFlapAngleForWings(player, data);
                    handleWing(partData, scale, angle);
                }
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                ISkinDye skinDye = provider.getPlayerDyeData(player, skinPart.getBaseType());
                SkinPartRenderer.INSTANCE.renderPart(partData, scale, skinDye, extraColours, (int) distance, true);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glPopMatrix();
            }
            if (true) {
                if (player instanceof AbstractClientPlayer) {
                    AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
                    Minecraft.getMinecraft().renderEngine.bindTexture(clientPlayer.getLocationSkin());
                }
            }
            mc.mcProfiler.endSection();
        }
    }
}
