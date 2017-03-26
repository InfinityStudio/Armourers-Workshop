package net.skin43d;

import java.util.UUID;

/**
 * @author ci010
 */
public class SkinIdentity {
    private UUID playerUUID;
    private String skinType;
    private int slot;

    public SkinIdentity(UUID playerUUID, String skinType, int slot) {
        this.slot = slot;
        this.playerUUID = playerUUID;
        this.skinType = skinType;
    }

    public int getSlot() {
        return slot;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getSkinType() {
        return skinType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkinIdentity that = (SkinIdentity) o;

        if (slot != that.slot) return false;
        if (!playerUUID.equals(that.playerUUID)) return false;
        return skinType.equals(that.skinType);
    }

    @Override
    public int hashCode() {
        int result = slot;
        result = 31 * result + playerUUID.hashCode();
        result = 31 * result + skinType.hashCode();
        return result;
    }
}
