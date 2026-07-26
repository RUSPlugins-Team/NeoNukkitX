package rusplugins.neonukkitx.event.inventory;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.inventory.Inventory;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class InventoryEvent extends Event {

    protected final Inventory inventory;

    public InventoryEvent(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player[] getViewers() {
        return this.inventory.getViewers().toArray(new Player[0]);
    }
}
