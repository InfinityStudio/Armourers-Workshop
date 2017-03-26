package net.skin43d;

import riskyken.armourersWorkshop.api.common.skin.type.ISkinPartType;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author ci010
 */
public class SkinInfo {
    public static final SkinInfo EMPTY = new SkinInfo(UUID.randomUUID(), Collections.<String, Skin>emptyMap());

    private UUID playerID;
    private Map<String, Skin> stringSkinMap;

    public SkinInfo(UUID playerID, Map<String, Skin> stringSkinMap) {
        this.playerID = playerID;
        this.stringSkinMap = stringSkinMap;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public Map<String, Skin> getStringSkinMap() {
        return stringSkinMap;
    }

    public Skin getSkin(ISkinType skinType) {
        return stringSkinMap.get(skinType.getRegistryName());
    }

    public SkinPart getSkinPart(ISkinType type, ISkinPartType partType) {
        Skin skin = stringSkinMap.get(type.getRegistryName());
        if (skin == null) return null;
        for (SkinPart part : skin.getParts())
            if (part.getPartType() == partType)
                return part;
        return null;
    }
}
