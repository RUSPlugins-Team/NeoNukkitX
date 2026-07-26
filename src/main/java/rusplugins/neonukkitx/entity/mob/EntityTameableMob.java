package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityTameableMob extends EntityWalkingMob /*implements EntityTameable*/ {

    public EntityTameableMob(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
