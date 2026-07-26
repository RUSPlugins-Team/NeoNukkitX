package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityTameableAnimal extends EntityWalkingAnimal /*implements EntityTameable*/ {

    public EntityTameableAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
