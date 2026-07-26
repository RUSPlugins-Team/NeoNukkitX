package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.event.entity.EntityDamageByEntityEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.network.protocol.EntityEventPacket;
import rusplugins.neonukkitx.utils.DyeColor;
import rusplugins.neonukkitx.utils.Utils;

public class EntitySquid extends EntityWaterAnimal {

    public static final int NETWORK_ID = 17;

    public EntitySquid(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.95f;
    }

    @Override
    public float getHeight() {
        return 0.95f;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(10);
        super.initEntity();
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{Item.get(Item.DYE, DyeColor.BLACK.getDyeData(), Utils.rand(1, 3))};
    }

    @Override
    public int getKillExperience() {
        return this.isBaby() ? 0 : Utils.rand(1, 3);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        boolean att = super.attack(source);
        if (source.isCancelled()) {
            return att;
        }

        if (source instanceof EntityDamageByEntityEvent) {
            EntityEventPacket pk = new EntityEventPacket();
            pk.eid = this.getId();
            pk.event = EntityEventPacket.SQUID_INK_CLOUD;
            this.level.addChunkPacket(this.getChunkX(), this.getChunkZ(), pk);
        }
        return att;
    }
}
