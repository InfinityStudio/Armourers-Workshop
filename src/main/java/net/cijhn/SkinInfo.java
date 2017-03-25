package net.cijhn;

import riskyken.armourersWorkshop.common.skin.data.Skin;

import java.util.UUID;

/**
 * @author ci010
 */
public class SkinInfo {
    private UUID playerID;
    private Skin[] skinsBySlot;

    public SkinInfo(UUID playerID, Skin[] skinsBySlot) {
        this.playerID = playerID;
        this.skinsBySlot = skinsBySlot;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public Skin getSkin(int slotId) {
        return skinsBySlot[slotId];
    }
}
