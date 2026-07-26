package rusplugins.neonukkitx.event.vehicle;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityVehicle;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class VehicleDestroyEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Entity attacker;

    public VehicleDestroyEvent(EntityVehicle vehicle, Entity attacker) {
        super(vehicle);
        this.attacker = attacker;
    }

    public Entity getAttacker() {
        return attacker;
    }
}
