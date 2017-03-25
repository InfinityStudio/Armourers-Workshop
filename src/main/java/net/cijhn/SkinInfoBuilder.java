package net.cijhn;

import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author ci010
 */
public class SkinInfoBuilder {
    private UUID playerID;
    private Map<String, Skin> stringSkinMap;

    public SkinInfoBuilder(UUID playerID) {
        this.playerID = playerID;
        this.stringSkinMap = new TreeMap<String, Skin>();
    }

    public static SkinInfoBuilder create(UUID uuid) {
        return new SkinInfoBuilder(uuid);
    }

    public SkinInfoBuilder addSkin(Skin skin) {
        stringSkinMap.put(skin.getSkinType().getRegistryName(), skin);
        return this;
    }

    public SkinInfo build() {
        return new SkinInfo(playerID, Collections.unmodifiableMap(stringSkinMap));
    }
}
