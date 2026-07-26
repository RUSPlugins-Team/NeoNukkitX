package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.level.Level;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class LevelEvent extends Event {

    private final Level level;

    public LevelEvent(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
