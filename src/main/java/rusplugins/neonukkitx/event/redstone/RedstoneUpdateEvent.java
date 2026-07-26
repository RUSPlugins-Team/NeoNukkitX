package rusplugins.neonukkitx.event.redstone;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.event.block.BlockUpdateEvent;

/**
 * @author Angelic47
 * Nukkit Project
 */
public class RedstoneUpdateEvent extends BlockUpdateEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public RedstoneUpdateEvent(Block source) {
        super(source);
    }
}