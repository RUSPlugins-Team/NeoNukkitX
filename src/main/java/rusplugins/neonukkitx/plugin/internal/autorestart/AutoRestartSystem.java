package rusplugins.neonukkitx.plugin.internal.autorestart;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;
import rusplugins.neonukkitx.player.PlayerAPI;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AutoRestartSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int CHECK_INTERVAL = 1200;
    private static final long RESTART_INTERVAL = 24L * 60 * 60 * 1000;
    private static final long WARNING_TIME = 5 * 60 * 1000;

    private final long restartTime;
    private boolean warned = false;

    public AutoRestartSystem(NEONKXInternalModule module) {
        this.module = module;
        this.restartTime = System.currentTimeMillis() + RESTART_INTERVAL;
    }

    public void start() {
        this.task = NeoScheduler.runRepeating(module, this, CHECK_INTERVAL);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

    @Override
    public void run() {
        Server server = Server.getInstance();
        long now = System.currentTimeMillis();
        long remaining = restartTime - now;

        if (!warned && remaining <= WARNING_TIME && remaining > 0) {
            warned = true;
            module.getLogger().info("[AutoRestart] Server restart in 5 minutes.");
            for (Player player : server.getOnlinePlayers().values()) {
                new PlayerAPI(player).sendActionBar("§c§l[AutoRestart] §r§eServer restart in 5 minutes!");
            }
        }

        if (now >= restartTime) {
            module.getLogger().info("[AutoRestart] Executing scheduled server restart.");
            for (Player player : server.getOnlinePlayers().values()) {
                player.kick("§cServer is restarting. Please reconnect in a moment.", false);
            }
            server.shutdown();
        }
    }
}
