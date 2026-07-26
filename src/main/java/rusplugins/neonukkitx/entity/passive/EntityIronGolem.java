package rusplugins.neonukkitx.entity.passive;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.entity.Attribute;
import rusplugins.neonukkitx.entity.mob.EntityWalkingMob;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;
import rusplugins.neonukkitx.network.protocol.UpdateAttributesPacket;
import rusplugins.neonukkitx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EntityIronGolem extends EntityWalkingMob {

    public static final int NETWORK_ID = 20;

    public EntityIronGolem(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 1.4f;
    }

    @Override
    public float getHeight() {
        return 2.9f;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(100);
        super.initEntity();
        this.noFallDamage = true;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();

        for (int i = 0; i < Utils.rand(3, 5); i++) {
            drops.add(Item.get(Item.IRON_INGOT, 0, 1));
        }

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(Item.POPPY, 0, 1));
        }

        return drops.toArray(new Item[0]);
    }

    @Override
    public int getKillExperience() {
        return 0;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.getNameTag() : "Iron Golem";
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        this.sendHealth();
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);

        this.sendHealth();
    }

    private void sendHealth() {
        if (this.isAlive()) {
            UpdateAttributesPacket pk = new UpdateAttributesPacket();
            int max = this.getMaxHealth();
            pk.entries = new Attribute[]{Attribute.getAttribute(Attribute.MAX_HEALTH).setMaxValue(max).setValue(this.health < max ? this.health : max)};
            pk.entityId = this.id;
            Server.broadcastPacket(this.getViewers().values(), pk);
        }
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (item.getId() == ItemID.IRON_INGOT && this.health < this.getRealMaxHealth() && this.isAlive()) {
            this.heal(25f);
            this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_REPAIR_IRON_GOLEM);
            return true; // onInteract: true = decrease count
        }
        return super.onInteract(player, item, clickedPos);
    }
}
