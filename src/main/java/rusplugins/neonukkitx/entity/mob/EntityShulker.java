package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.utils.Utils;

public class EntityShulker extends EntityWalkingMob {

    public static final int NETWORK_ID = 54;

    public EntityShulker(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 1f;
    }

    @Override
    public float getHeight() {
        return 1f;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(15);
        super.initEntity();
        this.fireProof = true;
        this.noFallDamage = true;

        if (this.namedTag.contains("Color")) {
            this.dataProperties.putInt(DATA_VARIANT, this.namedTag.getByte("Color"));
        } else {
            this.dataProperties.putInt(DATA_VARIANT, 16);
        }

        this.setCollidable(true);
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{Item.get(Item.SHULKER_SHELL, 0, Utils.rand(0, 1))};
    }

    @Override
    public int getKillExperience() {
        return 5;
    }
}
