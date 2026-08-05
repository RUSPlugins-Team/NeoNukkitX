package rusplugins.neonukkitx.plugin.internal.antiddos;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AntiDDoSSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int CHECK_INTERVAL = 200;
    private static final int MAX_CONNECTIONS_PER_IP = 5;
    private static final long WINDOW_MS = 60000;

    private final Map<String, IpData> ipTracker = new ConcurrentHashMap<>();

    private static final class IpData {
        int count;
        long windowStart;

        IpData() {
            this.count = 0;
            this.windowStart = System.currentTimeMillis();
        }
    }

    public AntiDDoSSystem(NEONKXInternalModule module) {
        this.module = module;
    }

    public void start() {
        this.task = NeoScheduler.runRepeating(module, this, CHECK_INTERVAL);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
        this.ipTracker.clear();
    }

    @Override
    public void run() {
        Server server = Server.getInstance();
        long now = System.currentTimeMillis();

        ipTracker.entrySet().removeIf(entry -> now - entry.getValue().windowStart > WINDOW_MS);

        Map<String, Integer> currentCounts = new ConcurrentHashMap<>();
        for (Player player : server.getOnlinePlayers().values()) {
            String ip = player.getAddress();
            currentCounts.merge(ip, 1, Integer::sum);
        }

        for (Map.Entry<String, Integer> entry : currentCounts.entrySet()) {
            String ip = entry.getKey();
            int current = entry.getValue();

            IpData data = ipTracker.computeIfAbsent(ip, k -> new IpData());
            data.count += current;

            if (data.count > MAX_CONNECTIONS_PER_IP) {
                server.getIPBans().addBan(ip, "AntiDDoS: Connection flood detected", null, "AntiDDoS");
                module.getLogger().warning("[AntiDDoS] IP banned for flood: " + ip + " (" + data.count + " connections)");

                for (Player player : server.getOnlinePlayers().values()) {
                    if (player.getAddress().equals(ip)) {
                        player.kick("§cConnection flood detected. Your IP has been banned.", false);
                    }
                }

                ipTracker.remove(ip);
            }
        }
    }
}
