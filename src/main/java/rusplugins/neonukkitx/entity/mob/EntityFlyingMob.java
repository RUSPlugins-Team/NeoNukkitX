package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.entity.EntityFlying;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityFlyingMob extends EntityFlying implements EntityMob {

    public EntityFlyingMob(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
