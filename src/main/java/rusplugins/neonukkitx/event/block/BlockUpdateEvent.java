package rusplugins.neonukkitx.event.block;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

/**
 * Event for Block Update
 * @author MagicDroidX
 */
public class BlockUpdateEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Event called on a block being updated.
     * @param block Block updated.
     */
    public BlockUpdateEvent(Block block) {
        super(block);
    }
}
