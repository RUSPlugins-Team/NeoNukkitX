package rusplugins.neonukkitx.entity.item;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.potion.Effect;
import rusplugins.neonukkitx.potion.Potion;

public class EntityPotionLingering extends EntityPotion {

    public static final int NETWORK_ID = 101;

    public EntityPotionLingering(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityPotionLingering(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        setDataFlag(DATA_FLAGS, DATA_FLAG_LINGER, true);
    }

    @Override
    protected void splash(Entity collidedWith) {
        super.splash(collidedWith);
        EntityAreaEffectCloud entity = (EntityAreaEffectCloud) Entity.createEntity("AreaEffectCloud", this.chunk,
                getDefaultNBT(this).putShort("PotionId", potionId)
        );

        Effect effect = Potion.getEffect(potionId, true);

        if (effect != null && entity != null) {
            entity.cloudEffects.add(effect.setDuration(1).setVisible(false).setAmbient(false));
            entity.spawnToAll();
        }
    }
}
