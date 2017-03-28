package net.skin43d.impl.client;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.skin43d.SkinProvider;
import net.skin43d.impl.Context;
import net.skin43d.impl.skin.Skin;
import net.skin43d.skin3d.ISkinDye;
import net.skin43d.skin3d.SkinTypeRegistry;
import riskyken.EquipmentWardrobeData;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

/**
 * Handles replacing the players texture with the painted version.
 *
 * @author RiskyKen
 */
@SideOnly(Side.CLIENT)
public class PlayerTextureHandler {
    private Map<UUID, EntityTextureInfo> playerTextureMap = Maps.newHashMap();
    private final Profiler profiler;
    private boolean disableTexturePainting;
    private Field playerTextures;

    public PlayerTextureHandler() {
        profiler = Minecraft.getMinecraft().mcProfiler;
        try {
            playerTextures = NetworkPlayerInfo.class.getDeclaredField("playerTextures");
            playerTextures.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static EquipmentWardrobeData DEFAULT = new EquipmentWardrobeData();

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent event) {
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRender(RenderPlayerEvent.Pre event) {
        disableTexturePainting = Context.instance().disableTexturePainting();
        if (disableTexturePainting)
            return;
        if (!(event.getEntityLiving() instanceof AbstractClientPlayer))
            return;
        AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityLiving();
        if (player.getGameProfile() == null) return;
        UUID uuid = player.getUniqueID();
//        EquipmentWardrobeData ewd = Context.instance().getEquipmentWardrobeProvider().getEquipmentWardrobeData(player);
//        if (ewd == null) return;

        profiler.startSection("textureBuild");
        if (playerTextureMap.containsKey(uuid)) {
            EntityTextureInfo textureInfo = playerTextureMap.get(uuid);

            textureInfo.updateHairColour(DEFAULT.hairColour);
            textureInfo.updateSkinColour(DEFAULT.skinColour);

            textureInfo.updateTexture(player.getLocationSkin());
            Skin[] skins = new Skin[4 * 5];
            SkinProvider skinProvider = Context.instance().getSkinProvider();
            SkinTypeRegistry reg = Context.instance().getSkinRegistry();
            for (int skinIndex = 0; skinIndex < 5; skinIndex++) {
                skins[skinIndex * 4] = skinProvider.getSkinInfoForEntity(player, reg.getSkinHead());
                skins[1 + skinIndex * 4] = skinProvider.getSkinInfoForEntity(player, reg.getSkinChest());
                skins[2 + skinIndex * 4] = skinProvider.getSkinInfoForEntity(player, reg.getSkinLegs());
                skins[3 + skinIndex * 4] = skinProvider.getSkinInfoForEntity(player, reg.getSkinFeet());
            }
//            ISkinDye[] dyes = new ISkinDye[4 * 5];
//            for (int skinIndex = 0; skinIndex < 5; skinIndex++) {
//                dyes[skinIndex * 4] = skinProvider.getPlayerDyeData(player, reg.getSkinHead());
//                dyes[1 + skinIndex * 4] = skinProvider.getPlayerDyeData(player, reg.getSkinChest());
//                dyes[2 + skinIndex * 4] = skinProvider.getPlayerDyeData(player, reg.getSkinLegs());
//                dyes[3 + skinIndex * 4] = skinProvider.getPlayerDyeData(player, reg.getSkinFeet());
//            }

            textureInfo.updateSkins(skins);
//            textureInfo.updateDyes(dyes);

            ResourceLocation nTexture = textureInfo.getReplacedTexture();
            Map<MinecraftProfileTexture.Type, ResourceLocation> mp = textureInfo.getTextureMap();
            if (mp == null)
                try {
                    mp = (Map<MinecraftProfileTexture.Type, ResourceLocation>) playerTextures.get(Minecraft.getMinecraft().getConnection()
                            .getPlayerInfo(player.getUniqueID()));
                    textureInfo.setTextureMap(mp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (mp != null) mp.put(MinecraftProfileTexture.Type.SKIN, nTexture);
        }
        profiler.endSection();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRender(RenderPlayerEvent.Post event) {
        if (disableTexturePainting)
            return;
        if (!(event.getEntityPlayer() instanceof AbstractClientPlayer))
            return;
        AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityLiving();
        if (player.getGameProfile() == null) return;
        UUID playerPointer = player.getUniqueID();
//        EquipmentWardrobeData ewd = Context.instance().getEquipmentWardrobeProvider().getEquipmentWardrobeData(player);
//        if (ewd == null) return;

        profiler.startSection("textureReset");
        if (playerTextureMap.containsKey(playerPointer)) {
            EntityTextureInfo textureInfo = playerTextureMap.get(playerPointer);
            ResourceLocation resourceLocation = textureInfo.postRender();
            Map<MinecraftProfileTexture.Type, ResourceLocation> mp = textureInfo.getTextureMap();
            if (mp == null)
                try {
                    mp = (Map<MinecraftProfileTexture.Type, ResourceLocation>) playerTextures.get(Minecraft.getMinecraft().getConnection()
                            .getPlayerInfo(player.getUniqueID()));
                    textureInfo.setTextureMap(mp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (mp != null) mp.put(MinecraftProfileTexture.Type.SKIN, resourceLocation);
        } else
            playerTextureMap.put(playerPointer, new EntityTextureInfo());
        profiler.endSection();
    }
}
