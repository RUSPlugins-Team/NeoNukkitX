package rusplugins.neonukkitx.entity.ai;

import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.mob.EntityCreeper;
import rusplugins.neonukkitx.entity.mob.EntityPiglin;
import rusplugins.neonukkitx.entity.mob.EntitySkeleton;
import rusplugins.neonukkitx.entity.mob.EntitySpider;
import rusplugins.neonukkitx.entity.mob.EntityZombie;
import rusplugins.neonukkitx.entity.mob.EntityZombiePigman;
import rusplugins.neonukkitx.entity.passive.EntityChicken;
import rusplugins.neonukkitx.entity.passive.EntityCow;
import rusplugins.neonukkitx.entity.passive.EntityPig;
import rusplugins.neonukkitx.entity.passive.EntitySheep;
import rusplugins.neonukkitx.level.GameRule;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.Position;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.scheduler.Task;
import rusplugins.neonukkitx.utils.NeoLog;
import rusplugins.neonukkitx.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobSpawnerTask extends Task {

    private static final int SPAWN_RADIUS = 48;
    private static final int SPAWN_MIN_RADIUS = 16;
    private static final int MAX_MOBS_PER_PLAYER = 12;
    private static final int MAX_MOBS_PER_WORLD = 80;
    private static final int SPAWN_INTERVAL = 200;

    private static final List<String> HOSTILE_MOBS = Arrays.asList(
        "Zombie", "Skeleton", "Creeper", "Spider", "Piglin", "ZombiePigman"
    );
    private static final List<String> PASSIVE_MOBS = Arrays.asList(
        "Sheep", "Cow", "Pig", "Chicken"
    );

    private final Server server;
    private int spawnCounter = 0;
    private long lastLogTick = 0;
    private int lastMobCount = 0;
    private long lastMobCountTick = 0;

    public MobSpawnerTask(Server server) {
        this.server = server;
        NeoLog.info("Creating Mobs");
    }

    @Override
    public void onRun(int currentTick) {
        if (currentTick % SPAWN_INTERVAL != 0) {
            return;
        }

        for (Level level : server.getLevels().values()) {
            if (!level.getGameRules().getBoolean(GameRule.DO_MOB_SPAWNING)) {
                continue;
            }

            int playerCount = level.getPlayers().size();
            if (playerCount == 0) continue;

            int currentMobs = countMobs(level);
            if (currentMobs >= MAX_MOBS_PER_WORLD) {
                continue;
            }

            for (Entity entity : level.getPlayers().values()) {
                if (!entity.isAlive()) continue;

                int playerMobs = countMobsNearPlayer(level, entity.getPosition(), SPAWN_RADIUS);
                if (playerMobs >= MAX_MOBS_PER_PLAYER) {
                    continue;
                }

                Position playerPos = entity.getPosition();
                long time = level.getTime() % Level.TIME_FULL;
                boolean isNight = time >= 13000 && time < 23000;

                String mobType = isNight ? getHostileMob() : getPassiveMob();
                boolean ok = attemptSpawn(level, playerPos, mobType);
                if (ok) {
                    spawnCounter++;
                    NeoLog.info("Created mob: " + mobType);
                }
            }
        }

        if (spawnCounter > 0 && currentTick - lastLogTick > 6000) {
            server.getLogger().info("[MobSpawner] Spawned " + spawnCounter + " mobs in last 5min");
            spawnCounter = 0;
            lastLogTick = currentTick;
        }
    }

    private int countMobs(Level level) {
        if (lastMobCountTick == level.getCurrentTick() - (level.getCurrentTick() % 5)) {
            return lastMobCount;
        }
        int count = 0;
        for (Entity entity : level.getEntities()) {
            if (entity instanceof rusplugins.neonukkitx.entity.mob.EntityMob 
                || entity instanceof rusplugins.neonukkitx.entity.passive.EntityAnimal) {
                count++;
            }
        }
        lastMobCount = count;
        lastMobCountTick = level.getCurrentTick();
        return count;
    }

    private int countMobsNearPlayer(Level level, Position pos, int radius) {
        int count = 0;
        double radiusSq = radius * radius;
        for (Entity entity : level.getEntities()) {
            if (entity instanceof rusplugins.neonukkitx.entity.mob.EntityMob 
                || entity instanceof rusplugins.neonukkitx.entity.passive.EntityAnimal) {
                if (pos.distanceSquared(entity.getPosition()) < radiusSq) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean attemptSpawn(Level level, Position playerPos, String mobType) {
        int x = playerPos.getFloorX() + Utils.rand(-SPAWN_RADIUS, SPAWN_RADIUS);
        int z = playerPos.getFloorZ() + Utils.rand(-SPAWN_RADIUS, SPAWN_RADIUS);
        
        FullChunk chunk = level.getChunkIfLoaded(x >> 4, z >> 4);
        if (chunk == null) {
            return false;
        }

        int y = getHighestBlockY(level, x, z);
        if (y < level.getMinBlockY()) {
            return false;
        }

        Position spawnPos = new Position(x + 0.5, y + 1, z + 0.5, level);
        double distance = spawnPos.distance(playerPos);

        if (distance < SPAWN_MIN_RADIUS || distance > SPAWN_RADIUS) {
            return false;
        }

        int lightLevel = level.getBlockLightAt(x, y + 1, z);
        boolean isHostile = isHostileMob(mobType);
        
        if (isHostile && lightLevel > 7) {
            return false;
        }
        if (!isHostile && lightLevel < 9) {
            return false;
        }

        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", spawnPos.x))
                        .add(new DoubleTag("", spawnPos.y))
                        .add(new DoubleTag("", spawnPos.z)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", ThreadLocalRandom.current().nextFloat() * 360))
                        .add(new FloatTag("", 0)));

        Entity entity = null;
        switch (mobType) {
            case "Zombie": entity = new EntityZombie(chunk, nbt); break;
            case "Skeleton": entity = new EntitySkeleton(chunk, nbt); break;
            case "Creeper": entity = new EntityCreeper(chunk, nbt); break;
            case "Spider": entity = new EntitySpider(chunk, nbt); break;
            case "Piglin": entity = new EntityPiglin(chunk, nbt); break;
            case "ZombiePigman": entity = new EntityZombiePigman(chunk, nbt); break;
            case "Sheep": entity = new EntitySheep(chunk, nbt); break;
            case "Cow": entity = new EntityCow(chunk, nbt); break;
            case "Pig": entity = new EntityPig(chunk, nbt); break;
            case "Chicken": entity = new EntityChicken(chunk, nbt); break;
        }

        if (entity != null) {
            entity.spawnToAll();
            return true;
        } else {
            NeoLog.fatal("Spawn failed: unknown mob type " + mobType, "MobSpawnerTask.java", 175);
        }

        return false;
    }

    private int getHighestBlockY(Level level, int x, int z) {
        for (int y = level.getMaxBlockY(); y >= level.getMinBlockY(); y--) {
            if (!level.getBlock(x, y, z).isTransparent()) {
                return y;
            }
        }
        return -1;
    }

    private String getHostileMob() {
        return HOSTILE_MOBS.get(Utils.rand(0, HOSTILE_MOBS.size() - 1));
    }

    private String getPassiveMob() {
        return PASSIVE_MOBS.get(Utils.rand(0, PASSIVE_MOBS.size() - 1));
    }

    private boolean isHostileMob(String mobType) {
        return HOSTILE_MOBS.contains(mobType);
    }
}
