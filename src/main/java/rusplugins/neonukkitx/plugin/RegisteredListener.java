package rusplugins.neonukkitx.plugin;

import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.event.EventPriority;
import rusplugins.neonukkitx.event.Listener;
import rusplugins.neonukkitx.utils.EventException;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class RegisteredListener {

    private final Listener listener;

    private final EventPriority priority;

    private final Plugin plugin;

    private final EventExecutor executor;

    private final boolean ignoreCancelled;

    public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin, boolean ignoreCancelled) {
        this.listener = listener;
        this.priority = priority;
        this.plugin = plugin;
        this.executor = executor;
        this.ignoreCancelled = ignoreCancelled;
    }

    public Listener getListener() {
        return listener;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public void callEvent(Event event) throws EventException {
        if (event instanceof Cancellable) {
            if (event.isCancelled() && ignoreCancelled) {
                return;
            }
        }
        executor.execute(listener, event);
    }

    public boolean isIgnoringCancelled() {
        return ignoreCancelled;
    }
}
