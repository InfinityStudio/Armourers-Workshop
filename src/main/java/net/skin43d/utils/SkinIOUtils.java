package net.skin43d.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import net.skin43d.exception.InvalidCubeTypeException;
import net.skin43d.exception.NewerFileVersionException;
import net.skin43d.impl.Context;
import net.skin43d.impl.skin.Skin;
import net.skin43d.impl.skin.serialize.SkinReader;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.nio.ByteBuffer;

public final class SkinIOUtils {

    public static boolean saveSkinToFile(File file, Skin skin) {
        File dir = file.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        DataOutputStream stream = null;
        try {
            stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
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

    public static Skin loadSkinFromFileByBuffer(File file ) {
        try {
            byte[] bytes = new byte[(int) file.length()];
            IOUtils.readFully(new FileInputStream(file), bytes);
            ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
            ByteBufInputStream stream = new ByteBufInputStream(buffer);
            String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
            boolean isShield = name.endsWith("{L}");
            return loadSkinFromStream(stream, isShield, Context.instance());
        } catch (FileNotFoundException e) {
            ModLogger.log(Level.WARN, "Skin file not found.");
            ModLogger.log(Level.WARN, file);
        } catch (IOException e) {
            ModLogger.log(Level.ERROR, "Skin file load failed.");
            ModLogger.log(Level.WARN, file);
            e.printStackTrace();
        }
        return null;
    }

    public static Skin loadSkinFromFileByBuffer(File file, Context context) {
        try {
            byte[] bytes = new byte[(int) file.length()];
            IOUtils.readFully(new FileInputStream(file), bytes);
            ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
            ByteBufInputStream stream = new ByteBufInputStream(buffer);
            String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
            boolean isShield = name.endsWith("{L}");
            return loadSkinFromStream(stream, isShield, context);
        } catch (FileNotFoundException e) {
            ModLogger.log(Level.WARN, "Skin file not found.");
            ModLogger.log(Level.WARN, file);
        } catch (IOException e) {
            ModLogger.log(Level.ERROR, "Skin file load failed.");
            ModLogger.log(Level.WARN, file);
            e.printStackTrace();
        }
        return null;
    }

    public static Skin loadSkinFromStream(InputStream inputStream, boolean isShield, Context context) {
        DataInputStream stream = null;
        Skin skin = null;

        try {
            stream = new DataInputStream(new BufferedInputStream(inputStream));
            skin = new SkinReader().readSkin(stream, context, isShield);
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

//    public static void makeDatabaseDirectory() {
//        File directory = getSkinDatabaseDirectory();
//        ModLogger.log("Loading skin database at: " + directory.getAbsolutePath());
//        copyGlobalDatabase();
//        if (!directory.exists())
//            if (directory.mkdir())
//                copyOldDatabase();
//    }


//    public static void copyOldDatabase() {
//        ModLogger.log("Moving skin database to a new location.");
//
//        File dirNewDatabase = getSkinDatabaseDirectory();
//        File dirOldDatabase = getOldSkinDatabaseDirectory();
//        if (!dirOldDatabase.exists()) {
//            ModLogger.log("Old database not found.");
//            return;
//        }
//
//        File[] oldFiles = dirOldDatabase.listFiles();
//        for (int i = 0; i < oldFiles.length; i++) {
//            File oldFile = oldFiles[i];
//            ModLogger.log("Copying file: " + oldFile.getName());
//            File newFile = new File(dirNewDatabase, oldFile.getName());
//            try {
//                FileUtils.copyFile(oldFile, newFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public static void copyGlobalDatabase() {
//        File dirGlobalDatabase = getGlobalSkinDatabaseDirectory();
//        if (dirGlobalDatabase.exists()) {
//            File dirWorldDatabase = getSkinDatabaseDirectory();
//            File[] globalFiles = dirGlobalDatabase.listFiles();
//            for (int i = 0; i < globalFiles.length; i++) {
//                File globalFile = globalFiles[i];
//                File worldFile = new File(dirWorldDatabase, globalFile.getName());
//                if (!globalFile.getName().equals("readme.txt") & !worldFile.exists()) {
//                    try {
//                        FileUtils.copyFile(globalFile, worldFile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        createGlobalDatabaseReadme();
//    }

//    private static void createGlobalDatabaseReadme() {
//        File globalDatabaseReadme = new File(getGlobalSkinDatabaseDirectory(), "readme.txt");
//        if (!getGlobalSkinDatabaseDirectory().exists()) {
//            getGlobalSkinDatabaseDirectory().mkdirs();
//        }
//        if (!globalDatabaseReadme.exists()) {
//            DataOutputStream outputStream = null;
//            try {
//                String crlf = "\r\n";
//                outputStream = new DataOutputStream(new FileOutputStream(globalDatabaseReadme));
//                outputStream.writeBytes("Any files placed in this directory will be copied into the skin-database folder of any worlds that are loaded." + crlf);
//                outputStream.writeBytes("Please read Info for Map & Mod Pack Makers on the main forum post if you want to know how to use this." + crlf);
//                outputStream.writeBytes("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2309193-wip-alpha-armourers-workshop-weapon-armour-skins");
//                outputStream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                IOUtils.closeQuietly(outputStream);
//            }
//        }
//    }

    public static boolean createDirectory(File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

//    public static void recoverSkins(EntityPlayer player) {
//        player.addChatComponentMessage(new TextComponentString("Starting skin recovery."));
//        File skinDir = getSkinDatabaseDirectory();
//        if (skinDir.exists() & skinDir.isDirectory()) {
//            File recoverDir = new File(System.getProperty("user.dir"), "recovered-skins");
//            if (!recoverDir.exists()) {
//                recoverDir.mkdirs();
//            }
//            File[] skinFiles = skinDir.listFiles();
//            player.addChatComponentMessage(new TextComponentString(String.format("Found %d skins to be recovered.", skinFiles.length)));
//            player.addChatComponentMessage(new TextComponentString("Working..."));
//            int unnamedSkinCount = 0;
//            int successCount = 0;
//            int failCount = 0;
//
//            for (int i = 0; i < skinFiles.length; i++) {
//                File skinFile = skinFiles[i];
//                Skin skin = loadSkinFromFile(skinFile);
//                if (skin != null) {
//                    String fileName = skin.getProperties().getPropertyString(Skin.KEY_FILE_NAME, null);
//                    String customName = skin.getProperties().getPropertyString(Skin.KEY_CUSTOM_NAME, null);
//                    if (!StringUtils.isNullOrEmpty(fileName)) {
//                        fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
//                        File newSkinFile = new File(recoverDir, fileName);
//                        if (newSkinFile.exists()) {
//                            int nameCount = 0;
//                            while (true) {
//                                nameCount++;
//                                newSkinFile = new File(recoverDir, fileName + "-" + nameCount);
//                                if (!newSkinFile.exists()) {
//                                    break;
//                                }
//                            }
//                        }
//                        saveSkinToFile(newSkinFile, skin);
//                        successCount++;
//                        continue;
//                    }
//                    if (!StringUtils.isNullOrEmpty(customName)) {
//                        customName = customName.replaceAll("[^a-zA-Z0-9.-]", "_");
//                        File newSkinFile = new File(recoverDir, customName);
//                        if (newSkinFile.exists()) {
//                            int nameCount = 0;
//                            while (true) {
//                                nameCount++;
//                                newSkinFile = new File(recoverDir, customName + "-" + nameCount);
//                                if (!newSkinFile.exists()) {
//                                    break;
//                                }
//                            }
//                        }
//                        saveSkinToFile(newSkinFile, skin);
//                        successCount++;
//                        continue;
//                    }
//                    unnamedSkinCount++;
//                    saveSkinToFile(new File(recoverDir, "unnamed-skin-" + unnamedSkinCount), skin);
//                    successCount++;
//                } else {
//                    failCount++;
//                }
//            }
//            player.addChatComponentMessage(new TextComponentString("Finished skin recovery."));
//            player.addChatComponentMessage(new TextComponentString(String.format("%d skins were recovered and %d fail recovery.", successCount, failCount)));
//        } else {
//            player.addChatComponentMessage(new TextComponentString("No skins found to recover."));
//        }
//    }
}
