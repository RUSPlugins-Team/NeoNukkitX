package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.entity.EntityJumping;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityJumpingAnimal extends EntityJumping implements EntityAnimal {

    public EntityJumpingAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
