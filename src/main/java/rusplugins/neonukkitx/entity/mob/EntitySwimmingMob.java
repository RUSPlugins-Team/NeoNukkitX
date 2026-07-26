package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.entity.EntitySwimming;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntitySwimmingMob extends EntitySwimming implements EntityMob {

    public EntitySwimmingMob(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
