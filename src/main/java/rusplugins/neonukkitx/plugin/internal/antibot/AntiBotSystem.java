package rusplugins.neonukkitx.plugin.internal.antibot;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AntiBotSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int CHECK_INTERVAL = 20;
    private static final int BOT_MODE_THRESHOLD = 10;
    private static final long BOT_MODE_DURATION = 30000;
    private static final long JOIN_WINDOW = 5000;

    private final Queue<Long> joinTimestamps = new LinkedList<>();
    private final Set<UUID> knownPlayers = ConcurrentHashMap.newKeySet();
    private boolean botMode = false;
    private long botModeEnd = 0;

    public AntiBotSystem(NEONKXInternalModule module) {
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
        this.knownPlayers.clear();
        this.joinTimestamps.clear();
    }

    @Override
    public void run() {
        Server server = Server.getInstance();
        long now = System.currentTimeMillis();

        while (!joinTimestamps.isEmpty() && now - joinTimestamps.peek() > JOIN_WINDOW) {
            joinTimestamps.poll();
        }

        if (joinTimestamps.size() >= BOT_MODE_THRESHOLD) {
            if (!botMode) {
                botMode = true;
                botModeEnd = now + BOT_MODE_DURATION;
                module.getLogger().warning("[AntiBot] Bot attack detected! Protection mode active for 30 seconds.");
            }
        }

        if (botMode && now > botModeEnd) {
            botMode = false;
            module.getLogger().info("[AntiBot] Protection mode deactivated.");
        }

        Set<UUID> current = new HashSet<>(server.getOnlinePlayers().keySet());

        for (UUID uuid : current) {
            if (!knownPlayers.contains(uuid)) {
                knownPlayers.add(uuid);
                joinTimestamps.add(now);

                Player player = server.getOnlinePlayers().get(uuid);
                if (player == null) continue;

                if (botMode) {
                    player.kick("§cBot attack protection active. Please try again later.", false);
                    continue;
                }

                if (isSuspicious(player)) {
                    player.kick("§cSuspicious activity detected.", false);
                    module.getLogger().warning("[AntiBot] Kicked suspicious player: " + player.getName());
                }
            }
        }

        knownPlayers.retainAll(current);
    }

    private boolean isSuspicious(Player player) {
        String name = player.getName();
        int ping = player.getPing();

        if (name.length() < 3) return true;
        if (name.matches("\\d+")) return true;
        if (hasRepeatingChars(name, 4)) return true;
        if (!containsVowel(name)) return true;
        if (ping == 0) return true;

        return false;
    }

    private boolean hasRepeatingChars(String s, int threshold) {
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
                if (count >= threshold) return true;
            } else {
                count = 1;
            }
        }
        return false;
    }

    private boolean containsVowel(String s) {
        String lower = s.toLowerCase();
        return lower.contains("a") || lower.contains("e") || lower.contains("i")
            || lower.contains("o") || lower.contains("u") || lower.contains("y")
            || lower.contains("а") || lower.contains("е") || lower.contains("о")
            || lower.contains("и") || lower.contains("у") || lower.contains("я")
            || lower.contains("э") || lower.contains("ю");
    }
}
