package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public abstract class EntityHorseBase extends EntityWalkingAnimal /*implements EntityRideable, EntityControllable*/ {

    public EntityHorseBase(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getKillExperience() {
        return 0;
    }
}
