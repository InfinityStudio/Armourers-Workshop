package removequ;


import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import net.skin43d.skin3d.SkinType;
import net.skin43d.skin3d.SkinPartType;
import riskyken.armourersWorkshop.common.config.ConfigHandler;

import com.mojang.authlib.GameProfile;

public final class ApiRegistrar {
    
    public static final ApiRegistrar INSTANCE = new ApiRegistrar();
    
//    public LinkedHashMap<String, IArmourersCommonManager> equipmentDataManagers = new LinkedHashMap<String, IArmourersCommonManager>();
//    public LinkedHashMap<String, IArmourersClientManager> equipmentRenderManagers = new LinkedHashMap<String, IArmourersClientManager>();
    
    public void addApiRequest(String modName, String className) {
        if (!ConfigHandler.allowModsToRegisterWithAPI) {
            return;
        }
        try {
            Class<?> c = Class.forName(className);
            Object classObject = c.newInstance();
//            if (classObject instanceof IArmourersCommonManager) {
//                ModLogger.log(String.format("Loading %s API addon for %s", "data manager", modName));
//                equipmentDataManagers.put(modName, ((IArmourersCommonManager)classObject));
////                ((IArmourersCommonManager)classObject).onLoad(SkinDataHandler.INSTANCE,
////                        SkinTypeRegistryImpl.INSTANCE, EntitySkinHandler.INSTANCE);
//            }
//            if (classObject instanceof IArmourersClientManager) {
//                if (ArmourersWorkshopMod.isDedicated()) {
//                    ModLogger.log(Level.ERROR, String.format("Mod %s is registering a render manager on the server side."
//                            + " This is very bad!", modName));
//                }
//                ModLogger.log(String.format("Loading %s API addon for %s", "render manager", modName));
//                equipmentRenderManagers.put(modName, ((IArmourersClientManager)classObject)) ;
//                ((IArmourersClientManager)classObject).onLoad(EquipmentRenderHandler.INSTANCE);
//            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public void onRenderEquipment(Entity entity, SkinType skinType) {
    }
    
    public void onRenderEquipmentPart(Entity entity, SkinPartType skinPart) {
    }
    
    public void onRenderMannequin(TileEntity TileEntity, GameProfile gameProfile) {
    }
}
