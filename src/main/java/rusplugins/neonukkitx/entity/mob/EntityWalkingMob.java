package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.entity.EntityWalking;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityWalkingMob extends EntityWalking implements EntityMob {

    public EntityWalkingMob(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
