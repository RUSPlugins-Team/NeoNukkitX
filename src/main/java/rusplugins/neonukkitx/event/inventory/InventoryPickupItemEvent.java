package rusplugins.neonukkitx.event.inventory;

import rusplugins.neonukkitx.entity.item.EntityItem;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.inventory.Inventory;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class InventoryPickupItemEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityItem item;

    public InventoryPickupItemEvent(Inventory inventory, EntityItem item) {
        super(inventory);
        this.item = item;
    }

    public EntityItem getItem() {
        return item;
    }
}