package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.entity.EntityWalking;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityWalkingAnimal extends EntityWalking implements EntityAnimal {

    public EntityWalkingAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
