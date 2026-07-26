package rusplugins.neonukkitx.entity.ai;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.EntityLiving;
import rusplugins.neonukkitx.entity.mob.EntityMob;
import rusplugins.neonukkitx.entity.mob.EntityCreeper;
import rusplugins.neonukkitx.event.entity.EntityDamageByEntityEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.utils.Utils;

/**
 * Optimized EntityAI with distance-based ticking and cached targets.
 */
public class EntityAI {

    private final EntityLiving entity;
    private Vector3 target;
    private Vector3 spawnPoint; // Original spawn position for wander limit
    private AIState state;
    private int tickCounter;
    private int stateTickCounter; // Ticks in current state
    private Player cachedNearestPlayer;
    private int cachedPlayerTick;
    private int creeperFuseTick; // Delay before creeper explodes

    // Optimization constants
    private static final int IDLE_TICK_RATE = 20;      // Check IDLE every 20 ticks
    private static final int WANDER_TICK_RATE = 5;     // Move every 5 ticks
    private static final int CHASE_TICK_RATE = 1;      // Chase every tick (responsive)
    private static final int ATTACK_TICK_RATE = 20;    // Attack every 20 ticks
    private static final int FLEE_TICK_RATE = 5;       // Flee every 5 ticks
    private static final int PLAYER_CACHE_TICKS = 40;  // Cache nearest player for 40 ticks
    private static final double SLEEP_DISTANCE_SQ = 2304; // 48 blocks squared
    private static final double WANDER_RADIUS = 8.0;
    private static final double WANDER_RADIUS_SQ = 64.0;
    private static final int CREEPER_FUSE_TIME = 30; // 1.5 seconds before explosion

    public EntityAI(EntityLiving entity) {
        this.entity = entity;
        this.state = AIState.IDLE;
        this.tickCounter = 0;
        this.stateTickCounter = 0;
        this.spawnPoint = new Vector3(entity.x, entity.y, entity.z);
        this.cachedNearestPlayer = null;
        this.cachedPlayerTick = 0;
        this.creeperFuseTick = 0;
    }

    public void onUpdate(int currentTick) {
        if (entity.isClosed() || !entity.isAlive()) return;

        tickCounter++;
        stateTickCounter++;

        // OPTIMIZATION: Sleep if far from any player
        if (tickCounter % 20 == 0) {
            if (isTooFarFromPlayers()) {
                return; // Skip AI update — mob is "sleeping"
            }
        }

        // State-specific tick rate
        int tickRate = getTickRateForState(state);
        if (tickCounter % tickRate != 0) {
            return;
        }

        switch (state) {
            case IDLE:
                updateIdle();
                break;
            case WANDER:
                updateWander();
                break;
            case CHASE:
                updateChase();
                break;
            case ATTACK:
                updateAttack();
                break;
            case FLEE:
                updateFlee();
                break;
        }
    }

    private boolean isTooFarFromPlayers() {
        Player nearest = getCachedNearestPlayer(64);
        return nearest == null || entity.distanceSquared(nearest) > SLEEP_DISTANCE_SQ;
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
            Player nearest = getCachedNearestPlayer(16);
            if (nearest != null && !nearest.isCreative() && !nearest.isSpectator()) {
                target = nearest;
                state = AIState.CHASE;
                stateTickCounter = 0;
                return;
            }
        }

        // Wander with 20% chance (reduced from 30%)
        if (Utils.rand(0, 100) < 20) {
            state = AIState.WANDER;
            stateTickCounter = 0;
            
            // Limit wander to radius from spawn point
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

        // Check if wandered too far from spawn
        if (entity.distanceSquared(spawnPoint) > WANDER_RADIUS_SQ * 4) {
            // Return to spawn
            target = new Vector3(spawnPoint.x, entity.y, spawnPoint.z);
        }

        moveToTarget(0.2); // Reduced speed from 0.25

        if (entity.distance(target) < 1.5 || stateTickCounter > 60) { // Reduced from 100
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

        if (distance < 2.5) { // Slightly increased from 2.0
            state = AIState.ATTACK;
            stateTickCounter = 0;
            creeperFuseTick = 0; // Reset fuse
            return;
        }

        moveToTarget(0.3); // Reduced from 0.35
    }

    private void updateAttack() {
        if (target == null || !(target instanceof Player)) {
            state = AIState.IDLE;
            return;
        }

        double distance = entity.distance(target);

        if (distance > 3.5) { // Increased from 3.0
            state = AIState.CHASE;
            creeperFuseTick = 0;
            return;
        }

        // Creeper fuse delay
        if (entity instanceof EntityCreeper) {
            creeperFuseTick++;
            if (creeperFuseTick < CREEPER_FUSE_TIME) {
                // Hissing phase — don't explode yet
                entity.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_IGNITED, true);
                return;
            }
            // Now explode
            ((EntityCreeper) entity).explode();
            return;
        }

        // Normal attack
        if (stateTickCounter % 20 == 0) {
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

        moveToTarget(0.35); // Reduced from 0.4

        if (entity.distance(target) > 10 || stateTickCounter > 40) { // Reduced from 60
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
            entity.motionX = (dx / dist) * speed * 0.3;
            entity.motionZ = (dz / dist) * speed * 0.3;
        } else {
            entity.motionX = 0;
            entity.motionZ = 0;
        }

        entity.yaw = Math.toDegrees(Math.atan2(dz, dx)) - 90;
    }

    private Player getCachedNearestPlayer(double radius) {
        // Return cached player if still valid
        if (cachedNearestPlayer != null && cachedNearestPlayer.isOnline() 
            && tickCounter - cachedPlayerTick < PLAYER_CACHE_TICKS) {
            double dist = entity.distanceSquared(cachedNearestPlayer);
            if (dist < radius * radius) {
                return cachedNearestPlayer;
            }
        }

        // Find new nearest player
        Player nearest = null;
        double nearestDist = radius * radius;

        for (Player player : entity.getLevel().getPlayers().values()) {
            double dist = entity.distanceSquared(player);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = player;
            }
        }

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
