package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class EntityGlowSquid extends EntitySquid {

    public static final int NETWORK_ID = 129;

    public EntityGlowSquid(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
}
