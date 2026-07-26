package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityVehicle;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class EntityVehicleExitEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityVehicle vehicle;

    public EntityVehicleExitEvent(Entity entity, EntityVehicle vehicle) {
        this.entity = entity;
        this.vehicle = vehicle;
    }

    public EntityVehicle getVehicle() {
        return vehicle;
    }
}
