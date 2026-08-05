package rusplugins.neonukkitx.entity.ai;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.EntityLiving;
import rusplugins.neonukkitx.entity.mob.EntityMob;
import rusplugins.neonukkitx.entity.mob.EntityCreeper;
import rusplugins.neonukkitx.entity.mob.EntityZombie;
import rusplugins.neonukkitx.entity.mob.EntitySkeleton;
import rusplugins.neonukkitx.event.entity.EntityDamageByEntityEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.utils.Utils;

public class EntityAI {

    private final EntityLiving entity;
    private Vector3 target;
    private Vector3 spawnPoint;
    private AIState state;
    private int tickCounter;
    private int stateTickCounter;
    private Player cachedNearestPlayer;
    private int cachedPlayerTick;
    private int creeperFuseTick;
    private int burnTick;
    private boolean aiEnabled = true;

    private static final int IDLE_TICK_RATE = 5;
    private static final int WANDER_TICK_RATE = 5;
    private static final int CHASE_TICK_RATE = 1;
    private static final int ATTACK_TICK_RATE = 15;
    private static final int FLEE_TICK_RATE = 5;
    private static final int PLAYER_CACHE_TICKS = 40;
    private static final double SLEEP_DISTANCE_SQ = 2304;
    private static final double AI_ACTIVATE_DISTANCE_SQ = 400; // 20 blocks (was 576)
    private static final double WANDER_RADIUS = 8.0;
    private static final double WANDER_RADIUS_SQ = 64.0;
    private static final int CREEPER_FUSE_TIME = 30;

    public EntityAI(EntityLiving entity) {
        this.entity = entity;
        this.state = AIState.IDLE;
        this.tickCounter = 0;
        this.stateTickCounter = 0;
        this.spawnPoint = new Vector3(entity.x, entity.y, entity.z);
        this.cachedNearestPlayer = null;
        this.cachedPlayerTick = 0;
        this.creeperFuseTick = 0;
        this.burnTick = 0;
    }

    public void onUpdate(int currentTick) {
        if (entity.isClosed() || !entity.isAlive() || !aiEnabled) return;

        tickCounter++;
        stateTickCounter++;

        // Hard sleep: no players within 48 blocks = disable AI completely
        if (tickCounter % 40 == 0) {
            Player nearest = getCachedNearestPlayer(64);
            if (nearest == null || entity.distanceSquared(nearest) > SLEEP_DISTANCE_SQ) {
                aiEnabled = false;
                entity.motionX = 0;
                entity.motionZ = 0;
                return;
            }
        }

        // Soft sleep: AI ticks only within 20 blocks
        Player nearest = getCachedNearestPlayer(64);
        if (nearest == null || entity.distanceSquared(nearest) > AI_ACTIVATE_DISTANCE_SQ) {
            entity.motionX = 0;
            entity.motionZ = 0;
            return;
        }

        // Wake up from hard sleep if player is close
        aiEnabled = true;

        if (isUndead()) {
            boolean sunlit = isSunlit();
            if (sunlit) {
                burnTick++;
                if (burnTick >= 20) {
                    burnTick = 0;
                    entity.attack(new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.FIRE_TICK, 1.0f));
                    entity.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_ONFIRE, true);
                }
                if (state != AIState.FLEE) {
                    Vector3 shadow = findNearestShadow();
                    if (shadow != null) {
                        state = AIState.FLEE;
                        stateTickCounter = 0;
                        target = shadow;
                    }
                }
            } else {
                burnTick = 0;
                entity.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_ONFIRE, false);
                if (state == AIState.FLEE && !isDayTime(entity.getLevel())) {
                    state = AIState.IDLE;
                    target = null;
                }
            }
        }

        int tickRate = getTickRateForState(state);
        if (tickCounter % tickRate != 0) {
            return;
        }

        switch (state) {
            case IDLE: updateIdle(); break;
            case WANDER: updateWander(); break;
            case CHASE: updateChase(); break;
            case ATTACK: updateAttack(); break;
            case FLEE: updateFlee(); break;
        }
    }

    private boolean isUndead() {
        return entity instanceof EntityZombie || entity instanceof EntitySkeleton;
    }

    private boolean isSunlit() {
        if (!isDayTime(entity.getLevel())) return false;
        int bx = entity.getFloorX();
        int by = entity.getFloorY();
        int bz = entity.getFloorZ();
        for (int y = by + 1; y <= entity.getLevel().getMaxBlockY(); y++) {
            if (!entity.getLevel().getBlock(bx, y, bz).isTransparent()) {
                return false;
            }
        }
        return true;
    }

    private boolean isDayTime(Level level) {
        long time = level.getTime() % Level.TIME_FULL;
        return time >= 0 && time < 13000;
    }

    private Vector3 findNearestShadow() {
        Level level = entity.getLevel();
        int cx = entity.getFloorX();
        int cy = entity.getFloorY();
        int cz = entity.getFloorZ();
        Vector3 best = null;
        double bestDist = Double.MAX_VALUE;

        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                int tx = cx + x;
                int tz = cz + z;
                for (int y = cy + 2; y <= cy + 6 && y <= level.getMaxBlockY(); y++) {
                    if (!level.getBlock(tx, y, tz).isTransparent()) {
                        double dist = x * x + z * z;
                        if (dist < bestDist) {
                            bestDist = dist;
                            best = new Vector3(tx + 0.5, cy, tz + 0.5);
                        }
                        break;
                    }
                }
            }
        }
        return best;
    }

    private int getTickRateForState(AIState state) {
        switch (state) {
            case IDLE: return IDLE_TICK_RATE;
            case WANDER: return WANDER_TICK_RATE;
            case CHASE: return CHASE_TICK_RATE;
            case ATTACK: return ATTACK_TICK_RATE;
            case FLEE: return FLEE_TICK_RATE;
            default: return 20;
        }
    }

    private void updateIdle() {
        if (entity instanceof EntityMob) {
            Player nearest = findNearestPlayer(20);
            if (nearest != null && !nearest.isCreative() && !nearest.isSpectator()) {
                target = nearest;
                state = AIState.CHASE;
                stateTickCounter = 0;
                return;
            }
        }

        if (Utils.rand(0, 100) < 40) {
            state = AIState.WANDER;
            stateTickCounter = 0;
            double angle = Math.random() * 2 * Math.PI;
            double distance = Utils.rand(3, (int) WANDER_RADIUS);
            double wx = spawnPoint.x + Math.cos(angle) * distance;
            double wz = spawnPoint.z + Math.sin(angle) * distance;
            target = new Vector3(wx, entity.y, wz);
        }
    }

    private void updateWander() {
        if (target == null) {
            state = AIState.IDLE;
            return;
        }

        if (entity.distanceSquared(spawnPoint) > WANDER_RADIUS_SQ * 4) {
            target = new Vector3(spawnPoint.x, entity.y, spawnPoint.z);
        }

        moveToTarget(0.15);

        if (entity.distance(target) < 1.5 || stateTickCounter > 60) {
            state = AIState.IDLE;
            target = null;
        }
    }

    private void updateChase() {
        if (target == null || !(target instanceof Player) || !((Player) target).isOnline()) {
            state = AIState.IDLE;
            target = null;
            return;
        }

        double distance = entity.distance(target);

        if (distance > 32) {
            state = AIState.IDLE;
            target = null;
            return;
        }

        if (distance < 2.5) {
            state = AIState.ATTACK;
            stateTickCounter = 0;
            creeperFuseTick = 0;
            return;
        }

        moveToTarget(0.22);
    }

    private void updateAttack() {
        if (target == null || !(target instanceof Player)) {
            state = AIState.IDLE;
            return;
        }

        double distance = entity.distance(target);

        if (distance > 3.5) {
            state = AIState.CHASE;
            creeperFuseTick = 0;
            return;
        }

        if (entity instanceof EntityCreeper) {
            creeperFuseTick++;
            if (creeperFuseTick < CREEPER_FUSE_TIME) {
                entity.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_IGNITED, true);
                return;
            }
            entity.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_IGNITED, false);
            ((EntityCreeper) entity).explode();
            entity.close();
            return;
        }

        if (stateTickCounter % 15 == 0) {
            Player player = (Player) target;
            EntityDamageByEntityEvent ev = new EntityDamageByEntityEvent(
                entity, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 3.0f
            );
            player.attack(ev);
        }
    }

    private void updateFlee() {
        if (target == null) {
            state = AIState.IDLE;
            return;
        }

        moveToTarget(0.28);

        if (entity.distance(target) > 10 || stateTickCounter > 40) {
            state = AIState.IDLE;
            target = null;
        }
    }

    private void moveToTarget(double speed) {
        if (target == null) return;

        double dx = target.x - entity.x;
        double dz = target.z - entity.z;
        double dist = Math.sqrt(dx * dx + dz * dz);

        if (dist > 0.1) {
            entity.motionX = (dx / dist) * speed;
            entity.motionZ = (dz / dist) * speed;

            if (entity.onGround) {
                double lookX = Math.cos(Math.toRadians(entity.yaw + 90));
                double lookZ = Math.sin(Math.toRadians(entity.yaw + 90));
                int bx = (int) Math.floor(entity.x + lookX * 0.8);
                int by = (int) Math.floor(entity.y);
                int bz = (int) Math.floor(entity.z + lookZ * 0.8);
                if (!entity.getLevel().getBlock(bx, by, bz).isTransparent()
                        && entity.getLevel().getBlock(bx, by + 1, bz).isTransparent()) {
                    entity.motionY = 0.42;
                }
            }
        } else {
            entity.motionX = 0;
            entity.motionZ = 0;
        }

        entity.yaw = Math.toDegrees(Math.atan2(dz, dx)) - 90;
        entity.move(entity.motionX, entity.motionY, entity.motionZ);
    }

    private Player findNearestPlayer(double radius) {
        Player nearest = null;
        double nearestDist = radius * radius;
        for (Player player : entity.getLevel().getPlayers().values()) {
            double dist = entity.distanceSquared(player);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = player;
            }
        }
        return nearest;
    }

    private Player getCachedNearestPlayer(double radius) {
        if (cachedNearestPlayer != null && cachedNearestPlayer.isOnline() 
            && tickCounter - cachedPlayerTick < PLAYER_CACHE_TICKS) {
            double dist = entity.distanceSquared(cachedNearestPlayer);
            if (dist < radius * radius) {
                return cachedNearestPlayer;
            }
        }

        Player nearest = findNearestPlayer(radius);
        cachedNearestPlayer = nearest;
        cachedPlayerTick = tickCounter;
        return nearest;
    }

    public void setTarget(Vector3 target) {
        this.target = target;
    }

    public Vector3 getTarget() {
        return target;
    }

    public AIState getState() {
        return state;
    }

    public void setState(AIState state) {
        this.state = state;
        this.stateTickCounter = 0;
    }

    public void setSpawnPoint(Vector3 point) {
        this.spawnPoint = point;
    }

    public enum AIState {
        IDLE,
        WANDER,
        CHASE,
        ATTACK,
        FLEE
    }
}
