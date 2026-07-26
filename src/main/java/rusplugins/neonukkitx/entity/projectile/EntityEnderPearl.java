package rusplugins.neonukkitx.entity.projectile;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.event.entity.EntityDamageByEntityEvent;
import rusplugins.neonukkitx.event.entity.EntityDamageEvent;
import rusplugins.neonukkitx.event.player.PlayerTeleportEvent.TeleportCause;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.NukkitMath;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;

public class EntityEnderPearl extends EntityProjectile {

    public static final int NETWORK_ID = 87;

    private int currentLevel;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getLength() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    protected float getGravity() {
        return 0.03f;
    }

    @Override
    protected float getDrag() {
        return 0.01f;
    }

    public EntityEnderPearl(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityEnderPearl(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);

        if (this.shootingEntity != null) {
            this.currentLevel = this.shootingEntity.getLevel().getId();
        }
    }

    @Override
    public boolean onUpdate(int currentTick) {
        boolean hasUpdate = super.onUpdate(currentTick);

        if (this.closed) {
            return false;
        }

        if (this.isCollided && this.shootingEntity instanceof Player) {
            boolean portal = false;
            for (Block collided : this.getCollisionBlocks()) {
                if (collided.getId() == Block.NETHER_PORTAL) {
                    portal = true;
                    break;
                }
            }

            this.close();

            if (!portal) {
                teleport();
            }

            return false;
        }

        if (this.isCollided || this.hadCollision || (this.shootingEntity instanceof Player && !((Player) this.shootingEntity).isOnline())) {
            this.close();
            return false;
        }

        return hasUpdate;
    }
    
    @Override
    public void onCollideWithEntity(Entity entity) {
        teleport();

        super.onCollideWithEntity(entity);
    }

    private void teleport() {
        if (!(this.shootingEntity instanceof Player) || this.shootingEntity.getLevel().getId() != this.currentLevel || !((Player) this.shootingEntity).isOnline()) {
            return;
        }

        this.shootingEntity.teleport(new Vector3(NukkitMath.floorDouble(this.x) + 0.5, this.y, NukkitMath.floorDouble(this.z) + 0.5), TeleportCause.ENDER_PEARL);

        int gamemode = ((Player) this.shootingEntity).getGamemode();
        if (gamemode == 0 || gamemode == 2) {
            this.shootingEntity.attack(new EntityDamageByEntityEvent(this, shootingEntity, EntityDamageEvent.DamageCause.FALL, 5f, 0f));
        }

        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_TELEPORT);
    }
}
