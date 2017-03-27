package net.skin43d.utils;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class ModLogger {
    public static void log(Object object) {
        FMLLog.log("SKIN43D", Level.INFO, String.valueOf(object));
    }

    public static void log(Level logLevel, Object object) {
        FMLLog.log("SKIN43D", logLevel, String.valueOf(object));
    }
}
