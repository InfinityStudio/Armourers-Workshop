package net.skin43d.impl.client.render.engine.attach;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.SkinProvider;
import net.skin43d.impl.Skin43D;
import net.skin43d.impl.client.render.FirstPersonRenderHelper;
import net.skin43d.impl.client.render.SkinPartRenderer;
import net.skin43d.impl.client.render.engine.RenderEngine;
import net.skin43d.impl.client.render.engine.core.ModelSkinSword;
import net.skin43d.impl.client.render.engine.core.ModelSkinWings;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.SkinPart;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.skin3d.SkinPartType;
import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.SkinTypeRegistry;
import net.skin43d.utils.ForgeDirection;
import net.skin43d.utils.ModLogger;
import net.skin43d.utils.Point3D;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ci010
 */
public class RenderEngineAttach implements RenderEngine {
    private final Set<ModelBiped> attachedBipedSet = Collections.newSetFromMap(new WeakHashMap<ModelBiped, Boolean>());
    ModelSkinSword sword = new ModelSkinSword();

    private EntityPlayer targetPlayer;
    private boolean[] renderedSkin = new boolean[7];
    private Map<SkinType, EntityEquipmentSlot> mapping = Maps.newHashMap();

    public RenderEngineAttach() {
        mapping.put(Skin43D.instance().getSkinRegistry().getSkinChest(), EntityEquipmentSlot.CHEST);
        mapping.put(Skin43D.instance().getSkinRegistry().getSkinHead(), EntityEquipmentSlot.HEAD);
        mapping.put(Skin43D.instance().getSkinRegistry().getSkinFeet(), EntityEquipmentSlot.FEET);
        mapping.put(Skin43D.instance().getSkinRegistry().getSkinLegs(), EntityEquipmentSlot.LEGS);
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Post event) {
        targetPlayer = null;
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        for (int i = 0; i < renderedSkin.length; i++)
            renderedSkin[i] = false;
        EntityPlayer player = targetPlayer = event.getEntityPlayer();
        if (player.getGameProfile() == null) return;
        attachModelsToBiped(event.getRenderer().getMainModel(), event.getRenderer());
    }

    @SubscribeEvent
    public void renderFirstPersonRight(RenderSpecificHandEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getItem() instanceof ItemSword) {
            Skin sword = Skin43D.instance().getSkinProvider().getSkinInfoForEntity(player, Skin43D.instance().getSkinRegistry().getSkinSword());
            if (sword != null) {
                event.setCanceled(true);
                FirstPersonRenderHelper.render(sword, this.sword, Skin43D.instance().getContext().disableTexturePainting(),
                        Skin43D.instance().getContext().useMultipassSkinRendering(), event);
            }
        }
    }

    boolean rendered(EntityEquipmentSlot slot) {
        return renderedSkin[slot.ordinal()];
    }

    boolean renderedWings() {
        return renderedSkin[6];
    }

    private void hackModel(RenderPlayer renderPlayer) {
        try {
            Field layerRenderers = RenderLivingBase.class.getDeclaredField("layerRenderers");
            layerRenderers.setAccessible(true);
            List<LayerRenderer> o = (List<LayerRenderer>) layerRenderers.get(renderPlayer);
            o.set(0, new LayerArmorOverride(renderPlayer, this));
            o.set(1, new LayerHeldItemOverride(renderPlayer, this));
            o.set(4, new LayerCapeOverride(renderPlayer, this));
            o.set(6, new LayerElytraOverride(renderPlayer, this));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void attachModelsToBiped(ModelBiped modelBiped, RenderPlayer renderPlayer) {
        if (attachedBipedSet.contains(modelBiped))
            return;
        hackModel(renderPlayer);
        SkinTypeRegistry reg = Skin43D.instance().getSkinRegistry();
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
        private final int renderIdx;
        private final Minecraft mc;

        public ModelRendererAttachment(ModelBiped modelBase, SkinPartType skinPart) {
            super(modelBase);
            this.mc = Minecraft.getMinecraft();
            this.skinPart = skinPart;
            EntityEquipmentSlot slot = mapping.get(skinPart.getBaseType());
            if (slot != null) renderIdx = slot.ordinal();
            else renderIdx = 6;
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
            if (distance > Skin43D.instance().getContext().getRenderDistance()) return;

            //TODO not really sure what EquipmentWardrobeData will handle(except color). Since it has the relationship with slot
            // which will be removed, I comment this out first.
            //extra color: [skinRed, skinG, skinB, hairR, hairG, hairB]
            //        EquipmentWardrobeData ewd = ClientProxy.equipmentWardrobeHandler.getEquipmentWardrobeData(new PlayerPointer(player));
            //        if (ewd != null) {
            //            Color skinColour = new Color(ewd.skinColour);
            //            Color hairColour = new Color(ewd.hairColour);
            //            extraColours = new byte[]{
            //                    (byte) skinColour.getRed(), (byte) skinColour.getGreen(), (byte) skinColour.getBlue(),
            //                    (byte) hairColour.getRed(), (byte) hairColour.getGreen(), (byte) hairColour.getBlue()};
            //        }
            byte[] extraColours = null;
            SkinProvider provider = Skin43D.instance().getSkinProvider();
            SkinTypeRegistry reg = Skin43D.instance().getSkinRegistry();

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
            if (Skin43D.instance().getContext().useSafeTexture())
                if (player instanceof AbstractClientPlayer) {
                    AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
                    Minecraft.getMinecraft().renderEngine.bindTexture(clientPlayer.getLocationSkin());
                }
            renderedSkin[renderIdx] = true;
            mc.mcProfiler.endSection();
        }
    }
}
