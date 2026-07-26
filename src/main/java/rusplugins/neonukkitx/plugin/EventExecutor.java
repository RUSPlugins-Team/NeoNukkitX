package rusplugins.neonukkitx.plugin;

import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.event.Listener;
import rusplugins.neonukkitx.utils.EventException;

/**
 * @author iNevet
 * Nukkit Project
 */
public interface EventExecutor {

    void execute(Listener listener, Event event) throws EventException;
}
