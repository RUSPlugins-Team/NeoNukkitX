package rusplugins.neonukkitx.event.vehicle;

import rusplugins.neonukkitx.entity.item.EntityVehicle;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class VehicleCreateEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public VehicleCreateEvent(EntityVehicle vehicle) {
        super(vehicle);
    }
}
