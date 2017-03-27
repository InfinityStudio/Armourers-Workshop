package net.skin43d.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.util.text.TextComponentString;
import net.skin43d.impl.Context;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.DimensionManager;
import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.exception.NewerFileVersionException;
import riskyken.armourersWorkshop.common.lib.LibModInfo;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.serialize.SkinReader;

public final class SkinIOUtils {

    public static boolean saveSkinFromFileName(String fileName, Skin skin) {
        File file = new File(getSkinLibraryDirectory(), fileName);
        return saveSkinToFile(file, skin);
    }

    public static boolean saveSkinFromFileName(String fileName, Skin skin, EntityPlayerMP player) {
        File file = new File(getSkinLibraryDirectory(), "private");
        file = new File(file, player.getUniqueID().toString());
        file = new File(file, fileName);
        return saveSkinToFile(file, skin);
    }

    public static boolean saveSkinToFile(File file, Skin skin) {
        File dir = file.getParentFile();
        if (!dir.exists())
            dir.mkdirs();
        DataOutputStream stream = null;

        try {
            stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
//            skin.writeToStream(stream);
            stream.flush();
        } catch (FileNotFoundException e) {
            ModLogger.log(Level.WARN, "Skin file not found.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            ModLogger.log(Level.ERROR, "Skin file save failed.");
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.closeQuietly(stream);
        }

        return true;
    }

    public static Skin loadSkinFromFileName(String fileName, EntityPlayerMP player) {
        File file = new File(getSkinLibraryDirectory(), "private");
        file = new File(file, player.getUniqueID().toString());
        file = new File(file, fileName);
        return loadSkinFromFile(file);
    }

    public static Skin loadSkinFromFileName(String fileName) {
        File file = new File(getSkinLibraryDirectory(), fileName);
        return loadSkinFromFile(file);
    }

    public static Skin loadSkinFromFile(File file) {
        try {
            return loadSkinFromStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            ModLogger.log(Level.WARN, "Skin file not found.");
            ModLogger.log(Level.WARN, file);
        }
        return null;
    }


    public static Skin loadSkinFromStream(InputStream inputStream) {
        DataInputStream stream = null;
        Skin skin = null;

        try {
            stream = new DataInputStream(new BufferedInputStream(inputStream));
            skin = new SkinReader().readSkin(stream, Context.instance());
        } catch (FileNotFoundException e) {
            ModLogger.log(Level.WARN, "Skin file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            ModLogger.log(Level.ERROR, "Skin file load failed.");
            e.printStackTrace();
        } catch (NewerFileVersionException e) {
            ModLogger.log(Level.ERROR, "Can not load skin file it was saved in newer version.");
            e.printStackTrace();
        } catch (InvalidCubeTypeException e) {
            ModLogger.log(Level.ERROR, "Unable to load skin. Unknown cube types found.");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
            IOUtils.closeQuietly(inputStream);
        }
        return skin;
    }

    public static void makeDatabaseDirectory() {
        File directory = getSkinDatabaseDirectory();
        ModLogger.log("Loading skin database at: " + directory.getAbsolutePath());
        copyGlobalDatabase();
        if (!directory.exists())
            if (directory.mkdir())
                copyOldDatabase();
    }


    public static void copyOldDatabase() {
        ModLogger.log("Moving skin database to a new location.");

        File dirNewDatabase = getSkinDatabaseDirectory();
        File dirOldDatabase = getOldSkinDatabaseDirectory();
        if (!dirOldDatabase.exists()) {
            ModLogger.log("Old database not found.");
            return;
        }

        File[] oldFiles = dirOldDatabase.listFiles();
        for (int i = 0; i < oldFiles.length; i++) {
            File oldFile = oldFiles[i];
            ModLogger.log("Copying file: " + oldFile.getName());
            File newFile = new File(dirNewDatabase, oldFile.getName());
            try {
                FileUtils.copyFile(oldFile, newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyGlobalDatabase() {
        File dirGlobalDatabase = getGlobalSkinDatabaseDirectory();
        if (dirGlobalDatabase.exists()) {
            File dirWorldDatabase = getSkinDatabaseDirectory();
            File[] globalFiles = dirGlobalDatabase.listFiles();
            for (int i = 0; i < globalFiles.length; i++) {
                File globalFile = globalFiles[i];
                File worldFile = new File(dirWorldDatabase, globalFile.getName());
                if (!globalFile.getName().equals("readme.txt") & !worldFile.exists()) {
                    try {
                        FileUtils.copyFile(globalFile, worldFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        createGlobalDatabaseReadme();
    }

    private static void createGlobalDatabaseReadme() {
        File globalDatabaseReadme = new File(getGlobalSkinDatabaseDirectory(), "readme.txt");
        if (!getGlobalSkinDatabaseDirectory().exists()) {
            getGlobalSkinDatabaseDirectory().mkdirs();
        }
        if (!globalDatabaseReadme.exists()) {
            DataOutputStream outputStream = null;
            try {
                String crlf = "\r\n";
                outputStream = new DataOutputStream(new FileOutputStream(globalDatabaseReadme));
                outputStream.writeBytes("Any files placed in this directory will be copied into the skin-database folder of any worlds that are loaded." + crlf);
                outputStream.writeBytes("Please read Info for Map & Mod Pack Makers on the main forum post if you want to know how to use this." + crlf);
                outputStream.writeBytes("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2309193-wip-alpha-armourers-workshop-weapon-armour-skins");
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(outputStream);
            }
        }
    }

    public static File getSkinDatabaseDirectory() {
        return new File(DimensionManager.getCurrentSaveRootDirectory(), "skin-database");
    }

    public static File getOldSkinDatabaseDirectory() {
        return new File(System.getProperty("user.dir"), "equipment-database");
    }

    public static File getGlobalSkinDatabaseDirectory() {
        return new File(System.getProperty("user.dir"), "global-skin-database");
    }

    public static File getSkinLibraryDirectory() {
        return new File(System.getProperty("user.dir"), LibModInfo.ID);
    }

    public static boolean createDirectory(File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public static void recoverSkins(EntityPlayer player) {
        player.addChatComponentMessage(new TextComponentString("Starting skin recovery."));
        File skinDir = getSkinDatabaseDirectory();
        if (skinDir.exists() & skinDir.isDirectory()) {
            File recoverDir = new File(System.getProperty("user.dir"), "recovered-skins");
            if (!recoverDir.exists()) {
                recoverDir.mkdirs();
            }
            File[] skinFiles = skinDir.listFiles();
            player.addChatComponentMessage(new TextComponentString(String.format("Found %d skins to be recovered.", skinFiles.length)));
            player.addChatComponentMessage(new TextComponentString("Working..."));
            int unnamedSkinCount = 0;
            int successCount = 0;
            int failCount = 0;

            for (int i = 0; i < skinFiles.length; i++) {
                File skinFile = skinFiles[i];
                Skin skin = loadSkinFromFile(skinFile);
                if (skin != null) {
                    String fileName = skin.getProperties().getPropertyString(Skin.KEY_FILE_NAME, null);
                    String customName = skin.getProperties().getPropertyString(Skin.KEY_CUSTOM_NAME, null);
                    if (!StringUtils.isNullOrEmpty(fileName)) {
                        fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
                        File newSkinFile = new File(recoverDir, fileName);
                        if (newSkinFile.exists()) {
                            int nameCount = 0;
                            while (true) {
                                nameCount++;
                                newSkinFile = new File(recoverDir, fileName + "-" + nameCount);
                                if (!newSkinFile.exists()) {
                                    break;
                                }
                            }
                        }
                        saveSkinToFile(newSkinFile, skin);
                        successCount++;
                        continue;
                    }
                    if (!StringUtils.isNullOrEmpty(customName)) {
                        customName = customName.replaceAll("[^a-zA-Z0-9.-]", "_");
                        File newSkinFile = new File(recoverDir, customName);
                        if (newSkinFile.exists()) {
                            int nameCount = 0;
                            while (true) {
                                nameCount++;
                                newSkinFile = new File(recoverDir, customName + "-" + nameCount);
                                if (!newSkinFile.exists()) {
                                    break;
                                }
                            }
                        }
                        saveSkinToFile(newSkinFile, skin);
                        successCount++;
                        continue;
                    }
                    unnamedSkinCount++;
                    saveSkinToFile(new File(recoverDir, "unnamed-skin-" + unnamedSkinCount), skin);
                    successCount++;
                } else {
                    failCount++;
                }
            }
            player.addChatComponentMessage(new TextComponentString("Finished skin recovery."));
            player.addChatComponentMessage(new TextComponentString(String.format("%d skins were recovered and %d fail recovery.", successCount, failCount)));
        } else {
            player.addChatComponentMessage(new TextComponentString("No skins found to recover."));
        }
    }
}
