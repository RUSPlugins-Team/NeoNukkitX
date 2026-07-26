package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.entity.EntityJumping;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityJumpingMob extends EntityJumping implements EntityMob {

    public EntityJumpingMob(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
