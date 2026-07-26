package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntitySwimming extends BaseEntity {

    public EntitySwimming(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
