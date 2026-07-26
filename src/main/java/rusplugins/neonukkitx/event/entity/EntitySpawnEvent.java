package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.EntityCreature;
import rusplugins.neonukkitx.entity.EntityHuman;
import rusplugins.neonukkitx.entity.item.EntityItem;
import rusplugins.neonukkitx.entity.item.EntityVehicle;
import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.level.Position;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class EntitySpawnEvent extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final int entityType;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
        this.entityType = entity.getNetworkId();
    }

    public Position getPosition() {
        return this.entity.getPosition();
    }

    public int getType() {
        return this.entityType;
    }

    public boolean isCreature() {
        return this.entity instanceof EntityCreature;
    }

    public boolean isHuman() {
        return this.entity instanceof EntityHuman;
    }

    public boolean isProjectile() {
        return this.entity instanceof EntityProjectile;
    }

    public boolean isVehicle() {
        return this.entity instanceof EntityVehicle;
    }

    public boolean isItem() {
        return this.entity instanceof EntityItem;
    }
}
