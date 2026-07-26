package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.entity.custom.CustomEntity;
import rusplugins.neonukkitx.entity.custom.EntityDefinition;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EntitySulfurCube extends EntityJumpingMob implements CustomEntity {

    public static final int NETWORK_ID = 153;

    private EntityDefinition entityDefinition;

    public EntitySulfurCube(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return this.entityDefinition;
    }

    public void setEntityDefinition(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;
    }

    @Override
    public int getNetworkId() {
        if (this.entityDefinition != null) {
            return this.entityDefinition.getRuntimeId();
        }
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
    public float getLength() {
        return 1f;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(16);
        super.initEntity();
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(ItemID.SULFUR, 0, 1));
        }

        return drops.toArray(new Item[0]);
    }

    @Override
    public int getKillExperience() {
        return 4;
    }
}
