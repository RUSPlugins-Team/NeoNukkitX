package rusplugins.neonukkitx.event.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class CraftingTableOpenEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block craftingTable;

    public CraftingTableOpenEvent(Player player, Block craftingTable) {
        this.player = player;
        this.craftingTable = craftingTable;
    }

    public Block getCraftingTable() {
        return this.craftingTable;
    }
}
