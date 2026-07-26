package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.entity.EntityFlying;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityFlyingAnimal extends EntityFlying implements EntityAnimal {

    public EntityFlyingAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
