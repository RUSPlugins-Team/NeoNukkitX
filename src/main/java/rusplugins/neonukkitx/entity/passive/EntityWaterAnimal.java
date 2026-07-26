package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.entity.EntitySwimming;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityWaterAnimal extends EntitySwimming implements EntityAnimal {

    public EntityWaterAnimal(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
