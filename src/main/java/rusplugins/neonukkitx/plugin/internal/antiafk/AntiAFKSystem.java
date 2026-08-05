package rusplugins.neonukkitx.plugin.internal.antiafk;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;
import rusplugins.neonukkitx.player.PlayerAPI;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AntiAFKSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int AFK_TIMEOUT_TICKS = 6000;
    private static final int AFK_WARNING_TICKS = 600;

    private final Map<UUID, AFKData> playerData = new ConcurrentHashMap<>();

    private static final class AFKData {
        double lastX, lastY, lastZ;
        int inactiveTicks;
        boolean warned;

        AFKData(double x, double y, double z) {
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
            this.inactiveTicks = 0;
            this.warned = false;
        }
    }

    public AntiAFKSystem(NEONKXInternalModule module) {
        this.module = module;
    }

    public void start() {
        this.task = NeoScheduler.runRepeating(module, this, 20);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
        this.playerData.clear();
    }

    @Override
    public void run() {
        Server server = Server.getInstance();

        playerData.keySet().removeIf(uuid -> !server.getOnlinePlayers().containsKey(uuid));

        for (Player player : server.getOnlinePlayers().values()) {
            UUID uuid = player.getUniqueId();
            AFKData data = playerData.get(uuid);

            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            if (data == null) {
                playerData.put(uuid, new AFKData(x, y, z));
                continue;
            }

            if (data.lastX != x || data.lastY != y || data.lastZ != z) {
                data.lastX = x;
                data.lastY = y;
                data.lastZ = z;
                data.inactiveTicks = 0;
                data.warned = false;
            } else {
                data.inactiveTicks += 20;
            }

            if (!data.warned && data.inactiveTicks >= AFK_TIMEOUT_TICKS - AFK_WARNING_TICKS) {
                new PlayerAPI(player).sendActionBar("§c§lWARNING: §r§eYou will be kicked for AFK soon");
                data.warned = true;
            }

            if (data.inactiveTicks >= AFK_TIMEOUT_TICKS) {
                player.kick("§cYou have been kicked for inactivity (AFK)", false);
                playerData.remove(uuid);
            }
        }
    }
}
