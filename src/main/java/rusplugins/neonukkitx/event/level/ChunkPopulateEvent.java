package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.level.format.FullChunk;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ChunkPopulateEvent extends ChunkEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ChunkPopulateEvent(FullChunk chunk) {
        super(chunk);
    }
}