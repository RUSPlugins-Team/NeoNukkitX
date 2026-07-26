package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class ProjectileLaunchEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ProjectileLaunchEvent(EntityProjectile entity) {
        this.entity = entity;
    }

    public EntityProjectile getEntity() {
        return (EntityProjectile) this.entity;
    }
}
