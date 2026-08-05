package rusplugins.neonukkitx.api;

import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.item.ItemBuilder;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.player.PlayerAPI;
import rusplugins.neonukkitx.scheduler.NeoScheduler;

/**
 * Central API facade. Provides access to all NeoNukkitX extended APIs.
 * Does not replace Server — complements it.
 */
public final class NeoNukkitXAPI {

    private static NeoNukkitXAPI instance;

    private NeoNukkitXAPI() {}

    public static void init() {
        if (instance == null) {
            instance = new NeoNukkitXAPI();
        }
    }

    public static NeoNukkitXAPI getInstance() {
        if (instance == null) {
            throw new IllegalStateException("API not initialized");
        }
        return instance;
    }

    public static Server getServer() {
        return Server.getInstance();
    }

    public static ItemBuilder createItem(int id) {
        return new ItemBuilder(id);
    }

    public static ItemBuilder createItem(int id, int meta) {
        return new ItemBuilder(id, meta);
    }

    public static PlayerAPI wrapPlayer(rusplugins.neonukkitx.Player player) {
        return new PlayerAPI(player);
    }

    public static NeoScheduler getScheduler() {
        return new NeoScheduler();
    }

    public static Level getLevel(String name) {
        return Server.getInstance().getLevelByName(name);
    }

    public static void broadcast(String message) {
        Server.getInstance().broadcastMessage(message);
    }

    public static double getTPS() {
        return Server.getInstance().getTicksPerSecond();
    }

    public static long getCurrentTick() {
        return Server.getInstance().getTick();
    }
}
