package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.utils.Utils;

public class EntityBreeze extends EntityFlyingMob {

    public static final int NETWORK_ID = 140;

    public EntityBreeze(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.77f;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(30);
        super.initEntity();
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{Item.get(Item.BREEZE_ROD, 0, Utils.rand(1, 2))};
    }

    @Override
    public int getKillExperience() {
        return 10;
    }
}
