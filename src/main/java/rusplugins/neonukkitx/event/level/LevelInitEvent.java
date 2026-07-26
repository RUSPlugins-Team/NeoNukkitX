package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.level.Level;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class LevelInitEvent extends LevelEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public LevelInitEvent(Level level) {
        super(level);
    }
}
