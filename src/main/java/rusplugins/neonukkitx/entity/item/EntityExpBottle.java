package rusplugins.neonukkitx.entity.item;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.level.particle.SpellParticle;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;
import rusplugins.neonukkitx.utils.Utils;

/**
 * @author xtypr
 */
public class EntityExpBottle extends EntityProjectile {

    public static final int NETWORK_ID = 68;

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
        return 0.1f;
    }

    @Override
    protected float getDrag() {
        return 0.01f;
    }

    public EntityExpBottle(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityExpBottle(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.age > 1200) {
            this.close();
            return false;
        }
        boolean update = super.onUpdate(currentTick);

        if (this.closed) {
            return false;
        }

        if (this.isCollided) {
            this.close();
            this.dropXp();
            return false;
        }
        return update;
    }

    @Override
    public void onCollideWithEntity(Entity entity) {
        this.close();
        this.dropXp();
    }

    public void dropXp() {
        this.getLevel().addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_GLASS);
        this.getLevel().addParticle(new SpellParticle(this, 0x00385dc6));
        this.getLevel().dropExpOrb(this, Utils.rand(3, 12));
    }
}
