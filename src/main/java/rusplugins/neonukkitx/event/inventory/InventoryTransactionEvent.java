package rusplugins.neonukkitx.event.inventory;

import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.inventory.transaction.InventoryTransaction;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class InventoryTransactionEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final InventoryTransaction transaction;

    public InventoryTransactionEvent(InventoryTransaction transaction) {
        this.transaction = transaction;
    }

    public InventoryTransaction getTransaction() {
        return transaction;
    }
}