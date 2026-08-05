package rusplugins.neonukkitx.plugin.internal.antibrake;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.EntityLiving;
import rusplugins.neonukkitx.entity.item.EntityItem;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;
import rusplugins.neonukkitx.player.PlayerAPI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AntiBrakeSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int CHECK_INTERVAL = 100;
    private static final int TPS_HISTORY_SIZE = 10;
    private static final float THRESHOLD_WARNING = 18.0f;
    private static final float THRESHOLD_CRITICAL = 15.0f;
    private static final float THRESHOLD_EMERGENCY = 10.0f;
    private static final int EMERGENCY_SHUTDOWN_TICKS = 12;
    private static final int AI_PLAYER_THRESHOLD = 100;
    private static final int MAX_ENTITIES_PER_CHUNK = 50;
    private static final int MAX_DROPS_PER_LEVEL = 500;
    private static final double CLUSTER_RADIUS = 5.0;
    private static final int MAX_CLUSTER_SIZE = 20;

    private final float[] tpsHistory = new float[TPS_HISTORY_SIZE];
    private int historyIndex = 0;
    private boolean historyFilled = false;

    private AlertLevel currentLevel = AlertLevel.NORMAL;
    private int emergencyTicks = 0;
    private boolean aiGloballyDisabled = false;
    private long lastActionTime = 0;

    private final Map<Long, EntitySnapshot> entityTracker = new ConcurrentHashMap<>();

    private enum AlertLevel { NORMAL, WARNING, CRITICAL, EMERGENCY }

    private static final class EntitySnapshot {
        double x, y, z;
        String levelName;
        long time;

        EntitySnapshot(double x, double y, double z, String levelName) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.levelName = levelName;
            this.time = System.currentTimeMillis();
        }
    }

    public AntiBrakeSystem(NEONKXInternalModule module) {
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
        this.entityTracker.clear();
    }

    @Override
    public void run() {
        Server server = Server.getInstance();
        float tps = server.getTicksPerSecond();
        updateTPSHistory(tps);
        float avgTps = getAverageTPS();

        AlertLevel newLevel = determineLevel(avgTps);
        if (newLevel != currentLevel) {
            currentLevel = newLevel;
            emergencyTicks = 0;
            onLevelChange(server, currentLevel);
        }

        if (currentLevel == AlertLevel.EMERGENCY) {
            emergencyTicks++;
        } else {
            emergencyTicks = 0;
        }

        scanEntities(server);
        handleMobAI(server);
        executeActions(server);
    }

    private void updateTPSHistory(float tps) {
        tpsHistory[historyIndex] = tps;
        historyIndex = (historyIndex + 1) % TPS_HISTORY_SIZE;
        if (historyIndex == 0) historyFilled = true;
    }

    private float getAverageTPS() {
        int count = historyFilled ? TPS_HISTORY_SIZE : historyIndex;
        if (count == 0) return 20.0f;
        float sum = 0;
        for (int i = 0; i < count; i++) {
            sum += tpsHistory[i];
        }
        return sum / count;
    }

    private AlertLevel determineLevel(float avgTps) {
        if (avgTps < THRESHOLD_EMERGENCY) return AlertLevel.EMERGENCY;
        if (avgTps < THRESHOLD_CRITICAL) return AlertLevel.CRITICAL;
        if (avgTps < THRESHOLD_WARNING) return AlertLevel.WARNING;
        return AlertLevel.NORMAL;
    }

    private void onLevelChange(Server server, AlertLevel level) {
        switch (level) {
            case WARNING:
                module.getLogger().warning("[AntiBrake] WARNING: TPS dropped to " + String.format("%.1f", getAverageTPS()));
                notifyAdmins(server, "§e§l[AntiBrake] §r§eWarning: TPS degradation detected.");
                break;
            case CRITICAL:
                module.getLogger().error("[AntiBrake] CRITICAL: TPS at " + String.format("%.1f", getAverageTPS()));
                notifyAdmins(server, "§c§l[AntiBrake] §r§cCritical server lag detected.");
                break;
            case EMERGENCY:
                module.getLogger().emergency("[AntiBrake] EMERGENCY: TPS critical " + String.format("%.1f", getAverageTPS()));
                notifyAdmins(server, "§4§l[AntiBrake] §r§4EMERGENCY STATE ACTIVE.");
                break;
            case NORMAL:
                module.getLogger().info("[AntiBrake] Server recovered. TPS normal.");
                notifyAdmins(server, "§a§l[AntiBrake] §r§aServer performance recovered.");
                break;
        }
    }

    private void executeActions(Server server) {
        long now = System.currentTimeMillis();
        if (now - lastActionTime < 5000) return;
        lastActionTime = now;

        switch (currentLevel) {
            case CRITICAL:
                activeGC();
                clearDrops(server);
                freezeSuspiciousEntities(server);
                break;
            case EMERGENCY:
                activeGC();
                clearDrops(server);
                killOverloadEntities(server);
                freezeAllMobs(server);
                kickIdlePlayers(server);
                if (emergencyTicks >= EMERGENCY_SHUTDOWN_TICKS) {
                    shutdownServer(server);
                }
                break;
            case NORMAL:
            case WARNING:
                break;
        }
    }

    private void scanEntities(Server server) {
        entityTracker.keySet().removeIf(id -> findEntityById(server, id) == null);

        for (Level level : server.getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (entity == null || entity.isClosed()) continue;
                long id = entity.getId();
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();

                EntitySnapshot old = entityTracker.get(id);
                if (old != null) {
                    double dx = x - old.x;
                    double dy = y - old.y;
                    double dz = z - old.z;
                    double distSq = dx * dx + dy * dy + dz * dz;

                    if (distSq > 10000.0 && !(entity instanceof Player)) {
                        module.getLogger().warning("[AntiBrake] Suspicious teleport/movement: " + entity.getClass().getSimpleName()
                                + " at " + level.getName() + " [" + (int) x + "," + (int) y + "," + (int) z + "]");
                    }
                }

                entityTracker.put(id, new EntitySnapshot(x, y, z, level.getName()));
            }
        }
    }

    private void handleMobAI(Server server) {
        int online = server.getOnlinePlayers().size();
        boolean shouldDisable = online < AI_PLAYER_THRESHOLD;

        if (shouldDisable && !aiGloballyDisabled) {
            aiGloballyDisabled = true;
            setMobAI(server, false);
            module.getLogger().info("[AntiBrake] Mob AI disabled (online < " + AI_PLAYER_THRESHOLD + ")");
        } else if (!shouldDisable && aiGloballyDisabled) {
            aiGloballyDisabled = false;
            setMobAI(server, true);
            module.getLogger().info("[AntiBrake] Mob AI enabled (online >= " + AI_PLAYER_THRESHOLD + ")");
        }
    }

    private void setMobAI(Server server, boolean enabled) {
        for (Level level : server.getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (!isMob(entity)) continue;
                try {
                    entity.setImmobile(!enabled);
                } catch (Exception ignored) {}
            }
        }
    }

    private void clearDrops(Server server) {
        int killed = 0;
        for (Level level : server.getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (entity instanceof EntityItem) {
                    entity.close();
                    killed++;
                    if (killed >= MAX_DROPS_PER_LEVEL) break;
                }
            }
        }
        if (killed > 0) {
            module.getLogger().info("[AntiBrake] Cleared " + killed + " dropped items.");
        }
    }

    private void freezeSuspiciousEntities(Server server) {
        for (Level level : server.getLevels().values()) {
            Map<String, List<Entity>> chunks = new HashMap<>();
            for (Entity entity : level.getEntities()) {
                if (entity instanceof Player) continue;
                String key = ((int) entity.getX() >> 4) + ":" + ((int) entity.getZ() >> 4);
                chunks.computeIfAbsent(key, k -> new ArrayList<>()).add(entity);
            }

            for (List<Entity> list : chunks.values()) {
                if (list.size() > MAX_ENTITIES_PER_CHUNK) {
                    for (int i = MAX_ENTITIES_PER_CHUNK; i < list.size(); i++) {
                        freezeEntity(list.get(i));
                    }
                    module.getLogger().warning("[AntiBrake] Froze " + (list.size() - MAX_ENTITIES_PER_CHUNK)
                            + " entities in dense chunk at " + level.getName());
                }
            }

            for (Entity entity : level.getEntities()) {
                if (entity instanceof Player) continue;
                int neighbors = countNearby(level, entity, CLUSTER_RADIUS);
                if (neighbors > MAX_CLUSTER_SIZE) {
                    freezeEntity(entity);
                }
            }
        }
    }

    private void killOverloadEntities(Server server) {
        for (Level level : server.getLevels().values()) {
            Map<String, List<Entity>> chunks = new HashMap<>();
            for (Entity entity : level.getEntities()) {
                if (entity instanceof Player) continue;
                String key = ((int) entity.getX() >> 4) + ":" + ((int) entity.getZ() >> 4);
                chunks.computeIfAbsent(key, k -> new ArrayList<>()).add(entity);
            }

            for (List<Entity> list : chunks.values()) {
                if (list.size() > MAX_ENTITIES_PER_CHUNK) {
                    for (int i = MAX_ENTITIES_PER_CHUNK; i < list.size(); i++) {
                        killEntity(list.get(i));
                    }
                    module.getLogger().emergency("[AntiBrake] Killed " + (list.size() - MAX_ENTITIES_PER_CHUNK)
                            + " overloaded entities in " + level.getName());
                }
            }
        }
    }

    private void freezeAllMobs(Server server) {
        for (Level level : server.getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (isMob(entity)) {
                    freezeEntity(entity);
                }
            }
        }
    }

    private void kickIdlePlayers(Server server) {
        int kicked = 0;
        for (Player player : server.getOnlinePlayers().values()) {
            if (player.isOp()) continue;
            player.kick("§cServer is experiencing critical lag. Please reconnect later.", false);
            kicked++;
            if (kicked >= 10) break;
        }
        if (kicked > 0) {
            module.getLogger().emergency("[AntiBrake] Kicked " + kicked + " players to relieve pressure.");
        }
    }

    private void shutdownServer(Server server) {
        module.getLogger().emergency("[AntiBrake] EMERGENCY SHUTDOWN initiated after persistent critical state.");
        for (Player player : server.getOnlinePlayers().values()) {
            player.kick("§4Emergency shutdown due to critical server instability.", false);
        }
        server.shutdown();
    }

    private void activeGC() {
        System.gc();
        module.getLogger().info("[AntiBrake] Active GC triggered.");
    }

    private void freezeEntity(Entity entity) {
        try {
            entity.setMotion(new Vector3(0, 0, 0));
        } catch (Exception ignored) {}
        try {
            entity.setImmobile(true);
        } catch (Exception ignored) {}
    }

    private void killEntity(Entity entity) {
        try {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).kill();
            } else {
                entity.close();
            }
        } catch (Exception e) {
            entity.close();
        }
    }

    private boolean isMob(Entity entity) {
        return entity instanceof EntityLiving && !(entity instanceof Player);
    }

    private int countNearby(Level level, Entity target, double radius) {
        int count = 0;
        double rSq = radius * radius;
        for (Entity e : level.getEntities()) {
            if (e == target || e instanceof Player) continue;
            double dx = e.getX() - target.getX();
            double dy = e.getY() - target.getY();
            double dz = e.getZ() - target.getZ();
            if (dx * dx + dy * dy + dz * dz <= rSq) count++;
        }
        return count;
    }

    private Entity findEntityById(Server server, long id) {
        for (Level level : server.getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (entity.getId() == id) return entity;
            }
        }
        return null;
    }

    private void notifyAdmins(Server server, String message) {
        for (Player player : server.getOnlinePlayers().values()) {
            if (player.isOp()) {
                new PlayerAPI(player).sendActionBar(message);
            }
        }
    }
}
