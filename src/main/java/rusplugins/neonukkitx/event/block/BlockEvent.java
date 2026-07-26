package rusplugins.neonukkitx.event.block;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Event;

/**
 * Generic block event.
 * @author MagicDroidX
 */
public abstract class BlockEvent extends Event {

    protected final Block block;

    /**
     * Generic block event.
     * NOTICE: This event isn't meant to be called.
     * @param block Block.
     */
    public BlockEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
