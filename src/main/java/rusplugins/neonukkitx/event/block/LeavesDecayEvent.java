package rusplugins.neonukkitx.event.block;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

/**
 * Event called before checking nearby logs or making leaves decay.
 * @author MagicDroidX
 */
public class LeavesDecayEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Event for leaves decaying / disappearing.
     * @param block Leaves block.
     */
    public LeavesDecayEvent(Block block) {
        super(block);
    }
}
