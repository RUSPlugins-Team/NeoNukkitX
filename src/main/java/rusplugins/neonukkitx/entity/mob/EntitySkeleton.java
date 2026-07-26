package rusplugins.neonukkitx.entity.mob;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.EntityMobWithTool;
import rusplugins.neonukkitx.entity.EntitySmite;
import rusplugins.neonukkitx.entity.projectile.EntityArrow;
import rusplugins.neonukkitx.event.entity.EntityDamageByChildEntityEvent;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.Vector2;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EntitySkeleton extends EntityWalkingMob implements EntitySmite, EntityMobWithTool {

    public static final int NETWORK_ID = 34;

    private Item tool;
    private Item offhand;

    public EntitySkeleton(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(20);
        super.initEntity();
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
        return 1.9f;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(Item.BONE, 0, 1));
        }

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(Item.ARROW, 0, 1));
        }

        return drops.toArray(new Item[0]);
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public void kill() {
        if (!this.isAlive()) {
            return;
        }

        super.kill();

        if (this.lastDamageCause instanceof EntityDamageByChildEntityEvent) {
            Entity damager;
            if (((EntityDamageByChildEntityEvent) this.lastDamageCause).getChild() instanceof EntityArrow && (damager = ((EntityDamageByChildEntityEvent) this.lastDamageCause).getDamager()) instanceof Player) {
                if (new Vector2(this.x, this.z).distanceSquared(damager.x, damager.z) >= 2500) { // 50 blocks
                    ((Player) damager).awardAchievement("snipeSkeleton");
                }
            }
        }
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        this.sendHandItems(player);
    }

    @Override
    public Item getTool() {
        return this.tool;
    }

    @Override
    public void setTool(Item tool) {
        this.tool = tool;
    }

    @Override
    public Item getOffhand() {
        return this.offhand;
    }

    @Override
    public void setOffhand(Item offhand) {
        this.offhand = offhand;
    }
}
