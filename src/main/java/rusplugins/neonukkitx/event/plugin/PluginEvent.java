package rusplugins.neonukkitx.event.plugin;

import rusplugins.neonukkitx.event.Event;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.plugin.Plugin;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class PluginEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Plugin plugin;

    public PluginEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
