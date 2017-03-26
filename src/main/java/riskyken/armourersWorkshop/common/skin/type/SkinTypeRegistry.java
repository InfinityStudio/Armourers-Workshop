package riskyken.armourersWorkshop.common.skin.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.skin43d.skin3d.SkinType;
import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinTypeRegistry;
import riskyken.armourersWorkshop.common.skin.type.arrow.SkinArrow;
import riskyken.armourersWorkshop.common.skin.type.block.SkinBlock;
import riskyken.armourersWorkshop.common.skin.type.bow.SkinBow;
import riskyken.armourersWorkshop.common.skin.type.chest.SkinChest;
import riskyken.armourersWorkshop.common.skin.type.feet.SkinFeet;
import riskyken.armourersWorkshop.common.skin.type.head.SkinHead;
import riskyken.armourersWorkshop.common.skin.type.legs.SkinLegs;
import riskyken.armourersWorkshop.common.skin.type.legs.SkinSkirt;
import riskyken.armourersWorkshop.common.skin.type.sword.SkinSword;
import riskyken.armourersWorkshop.common.skin.type.wings.SkinWings;
import net.skin43d.utils.ModLogger;

public final class SkinTypeRegistry implements ISkinTypeRegistry {
    public SkinType skinHead;
    public SkinType skinChest;
    public SkinType skinLegs;
    public SkinType skinSkirt;
    public SkinType skinFeet;
    public SkinType skinSword;
    public SkinType skinBow;
    public SkinType skinArrow;
    public SkinType skinBlock;
    public SkinType skinWings;

    private LinkedHashMap<String, SkinType> skinTypeMap;
    private HashMap<String, SkinPartType> skinPartMap;

    private List<SkinType> skinTypeList;

    public SkinTypeRegistry() {
        MinecraftForge.EVENT_BUS.register(this);
        skinTypeMap = new LinkedHashMap<String, SkinType>();
        skinPartMap = new HashMap<String, SkinPartType>();
        skinTypeList = new ArrayList<SkinType>();
        registerSkins();
    }

    private void registerSkins() {
        skinHead = new SkinHead();
        skinChest = new SkinChest();
        skinLegs = new SkinLegs();
        skinSkirt = new SkinSkirt();
        skinFeet = new SkinFeet();
        skinSword = new SkinSword();
        skinBow = new SkinBow();
        skinArrow = new SkinArrow();
        skinBlock = new SkinBlock();
        skinWings = new SkinWings();

        registerSkin(skinHead);
        registerSkin(skinChest);
        registerSkin(skinLegs);
        //cacheSkin(skinSkirt);
        registerSkin(skinFeet);
        registerSkin(skinSword);
        registerSkin(skinBow);
        registerSkin(skinArrow);
        registerSkin(skinBlock);
        registerSkin(skinWings);
    }

    @Override
    public boolean registerSkin(SkinType skinType) {
        if (skinType == null) {
            ModLogger.log(Level.WARN, "A impl tried to register a null skin type.");
            return false;
        }
        if (skinType.getRegistryName() == null || skinType.getRegistryName().trim().isEmpty()) {
            ModLogger.log(Level.WARN, "A impl tried to register a skin type with an invalid registry name.");
            return false;
        }
        if (skinPartMap.containsKey(skinType.getRegistryName())) {
            ModLogger.log(Level.WARN, "A impl tried to register a skin type with a registry name that is in use.");
            return false;
        }
        if (skinType.getSkinParts() == null || skinType.getSkinParts().size() == 0) {
            ModLogger.log(Level.WARN, "A impl tried to register a skin type no skin type parts.");
            return false;
        }

        ModLogger.log(String.format("Registering skin: %s", skinType.getRegistryName()));
        skinTypeMap.put(skinType.getRegistryName(), skinType);
        List<SkinPartType> skinParts = skinType.getSkinParts();
        for (int i = 0; i < skinParts.size(); i++) {
            SkinPartType skinPart = skinParts.get(i);
            skinPartMap.put(skinPart.getRegistryName(), skinPart);
        }
        skinTypeList.add(skinType);
        return true;
    }

    @Override
    public SkinType getSkinTypeFromRegistryName(String registryName) {
        if (registryName == null | registryName.trim().isEmpty()) {
            return null;
        }
        if (registryName.equals(skinSkirt.getRegistryName())) {
            return skinLegs;
        }
        SkinType skinType = skinTypeMap.get(registryName);
        return skinType;
    }

    public SkinType getSkinTypeFromLegacyId(int legacyId) {
        switch (legacyId) {
            case 0:
                return getSkinTypeFromRegistryName("armourers:head");
            case 1:
                return getSkinTypeFromRegistryName("armourers:chest");
            case 2:
                return getSkinTypeFromRegistryName("armourers:legs");
            case 3:
                return getSkinTypeFromRegistryName("armourers:legs");
            //return getSkinTypeFromRegistryName("armourers:skirt");
            case 4:
                return getSkinTypeFromRegistryName("armourers:feet");
            case 5:
                return getSkinTypeFromRegistryName("armourers:sword");
            case 6:
                return getSkinTypeFromRegistryName("armourers:bow");
            case 7:
                return getSkinTypeFromRegistryName("armourers:arrow");
            default:
                return null;
        }
    }


    @Override
    public SkinType getSkinHead() {
        return skinHead;
    }

    @Override
    public SkinType getSkinChest() {
        return skinChest;
    }

    @Override
    public SkinType getSkinLegs() {
        return skinLegs;
    }

    @Override
    public SkinType getSkinSkirt() {
        return skinSkirt;
    }

    @Override
    public SkinType getSkinFeet() {
        return skinFeet;
    }

    @Override
    public SkinType getSkinSword() {
        return skinSword;
    }

    @Override
    public SkinType getSkinBow() {
        return skinBow;
    }

    @Override
    public SkinType getSkinArrow() {
        return skinArrow;
    }

    @Override
    public SkinType getSkinBlock() {
        return skinBlock;
    }

    @Override
    public SkinType getSkinWings() {
        return skinWings;
    }

    @Override
    public SkinPartType getSkinPartTypeFromId(int id) {
        return getSkinPartFromLegacyId(id);
    }

    @Override
    public SkinPartType getSkinPartTypeFromName(String regName) {
        if (regName == null | regName.trim().isEmpty()) {
            return null;
        }
        return skinPartMap.get(regName);
    }

    public SkinPartType getSkinPartFromLegacyId(int legacyId) {
        switch (legacyId) {
            case 0:
                return getSkinPartTypeFromName("armourers:head.base");
            case 1:
                return getSkinPartTypeFromName("armourers:chest.base");
            case 2:
                return getSkinPartTypeFromName("armourers:chest.leftArm");
            case 3:
                return getSkinPartTypeFromName("armourers:chest.rightArm");
            case 4:
                return getSkinPartTypeFromName("armourers:legs.leftLeg");
            case 5:
                return getSkinPartTypeFromName("armourers:legs.rightLeg");
            case 6:
                //return getSkinPartFromRegistryName("armourers:skirt.base");
                return getSkinPartTypeFromName("armourers:legs.skirt");
            case 7:
                return getSkinPartTypeFromName("armourers:feet.leftFoot");
            case 8:
                return getSkinPartTypeFromName("armourers:feet.rightFoot");
            case 9:
                return getSkinPartTypeFromName("armourers:sword.base");
            case 10:
                return getSkinPartTypeFromName("armourers:bow.base");
            default:
                return null;
        }
    }

    @Override
    public List<SkinType> getAllSkinTypes() {
        return skinTypeList;
    }

    public int getNumberOfSkinRegistered() {
        return skinTypeMap.size();
    }

    @SideOnly(Side.CLIENT)
    public String getLocalizedSkinTypeName(SkinType skinType) {
        String localizedName = "skinType." + skinType.getRegistryName() + ".name";
        return StatCollector.translateToLocal(localizedName);
    }

    @SideOnly(Side.CLIENT)
    public String getLocalizedSkinPartTypeName(SkinPartType skinPartType) {
        String localizedName = "skinPartType." + skinPartType.getRegistryName() + ".name";
        return StatCollector.translateToLocal(localizedName);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 1) {
            for (int i = 0; i < skinTypeMap.size(); i++) {
                String registryName = (String) skinTypeMap.keySet().toArray()[i];
                SkinType skinType = getSkinTypeFromRegistryName(registryName);
                if (skinType != null) {
                    skinType.registerIcon(event.map);
                }
            }
        }
    }

    @Override
    public SkinType getSkinTypeSkirt() {
        return skinSkirt;
    }

}
