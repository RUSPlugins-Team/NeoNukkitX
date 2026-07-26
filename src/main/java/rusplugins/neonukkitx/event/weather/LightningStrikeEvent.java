package rusplugins.neonukkitx.event.weather;

import rusplugins.neonukkitx.entity.weather.EntityLightningStrike;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.event.level.WeatherEvent;
import rusplugins.neonukkitx.level.Level;

/**
 * @author funcraft
 * Nukkit Project
 */
public class LightningStrikeEvent extends WeatherEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final EntityLightningStrike bolt;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public LightningStrikeEvent(Level level, final EntityLightningStrike bolt) {
        super(level);
        this.bolt = bolt;
    }

    /**
     * Gets the bolt which is striking the earth.
     * @return lightning entity
     */
    public EntityLightningStrike getLightning() {
        return bolt;
    }
}
