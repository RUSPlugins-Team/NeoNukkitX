package rusplugins.neonukkitx.scheduler;

import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.Plugin;

public class NeoScheduler {

    public static TaskHandler runLater(Plugin plugin, Runnable task, int ticks) {
        return Server.getInstance().getScheduler().scheduleDelayedTask(plugin, task, ticks);
    }

    public static TaskHandler runRepeating(Plugin plugin, Runnable task, int period) {
        return Server.getInstance().getScheduler().scheduleRepeatingTask(plugin, task, period);
    }

    public static TaskHandler runAsync(Plugin plugin, AsyncTask task) {
        return Server.getInstance().getScheduler().scheduleAsyncTask(plugin, task);
    }

    public static TaskHandler runLaterAsync(Plugin plugin, Runnable task, int ticks) {
        return Server.getInstance().getScheduler().scheduleDelayedTask(plugin, task, ticks, true);
    }
}
