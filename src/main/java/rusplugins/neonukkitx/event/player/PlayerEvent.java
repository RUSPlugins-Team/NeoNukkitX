package rusplugins.neonukkitx.event.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Event;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class PlayerEvent extends Event {

    protected Player player;

    public Player getPlayer() {
        return player;
    }
}
