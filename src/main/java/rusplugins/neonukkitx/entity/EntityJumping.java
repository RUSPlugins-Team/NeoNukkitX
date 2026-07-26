package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityJumping extends BaseEntity {

    public EntityJumping(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
