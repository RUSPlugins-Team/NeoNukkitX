package rusplugins.neonukkitx.event.inventory;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.inventory.LoomInventory;
import rusplugins.neonukkitx.item.Item;

public class LoomItemEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item newItem;
    private final Player player;

    public LoomItemEvent(LoomInventory inventory, Item newItem, Player player) {
        super(inventory);
        this.newItem = newItem;
        this.player = player;
    }

    public Item getNewItem() {
        return this.newItem;
    }

    public Player getPlayer() {
        return this.player;
    }
}
