package rusplugins.neonukkitx.event.block;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

/**
 * Event for forming blocks.
 * @author MagicDroidX
 */
public class BlockFormEvent extends BlockGrowEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }
    /**
     * Event for forming blocks.
     * NOTICE: This event isn't meant to be called.
     * @param block Block affected by the event.
     * @param newState New state of the block.
     */
    public BlockFormEvent(Block block, Block newState) {
        super(block, newState);
    }
}
