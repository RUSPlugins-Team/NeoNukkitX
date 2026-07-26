package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.level.Level;

/**
 * @author funcraft
 * Nukkit Project
 */
public abstract class WeatherEvent extends Event {

    private final Level level;

    public WeatherEvent(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
