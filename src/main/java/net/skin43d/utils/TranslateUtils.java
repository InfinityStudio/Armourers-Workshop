package net.skin43d.utils;


import net.minecraft.util.text.translation.I18n;

public final class TranslateUtils {
    
    public static String translate(String unlocalizedText) {
        String localizedText = I18n.translateToLocal(unlocalizedText);
        return localizedText.replace("&", "\u00a7");
    }
    
    public static String translate(String unlocalizedText, Object ... args) {
        String localizedText = I18n.translateToLocalFormatted(unlocalizedText, args);
        return localizedText.replace("&", "\u00a7");
    }
}
