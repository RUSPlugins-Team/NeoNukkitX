package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.level.Level;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class LevelSaveEvent extends LevelEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public LevelSaveEvent(Level level) {
        super(level);
    }
}
