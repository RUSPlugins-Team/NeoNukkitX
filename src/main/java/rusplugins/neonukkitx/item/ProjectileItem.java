package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.projectile.EntityEnderEye;
import rusplugins.neonukkitx.entity.projectile.EntityEnderPearl;
import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.event.entity.ProjectileLaunchEvent;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;

/**
 * @author CreeperFace
 */
public abstract class ProjectileItem extends Item {

    public ProjectileItem(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    abstract public String getProjectileEntityType();

    abstract public float getThrowForce();

    public boolean onClickAir(Player player, Vector3 directionVector) {
        Vector3 motion;

        if (this instanceof ItemEnderEye) {
            if (player.getLevel().getDimension() != Level.DIMENSION_OVERWORLD) {
                return false;
            }

            Vector3 vector = player // TODO: Stronghold position here. Meanwhile you can set custom motion in ProjectileLaunchEvent.
                    .subtract(player).normalize();
            vector.y = 0.55f;
            motion = vector.divide(this.getThrowForce());
        } else {
            motion = directionVector.multiply(this.getThrowForce());
        }

        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", player.x))
                        .add(new DoubleTag("", player.y + player.getEyeHeight()))
                        .add(new DoubleTag("", player.z)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", motion.x))
                        .add(new DoubleTag("", motion.y))
                        .add(new DoubleTag("", motion.z)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", (float) player.yaw))
                        .add(new FloatTag("", (float) player.pitch)));

        this.correctNBT(nbt);

        Entity projectile = Entity.createEntity(this.getProjectileEntityType(), player.getLevel().getChunk(player.getChunkX(), player.getChunkZ()), nbt, player);
        if (projectile instanceof EntityProjectile) {
            if (projectile instanceof EntityEnderPearl || projectile instanceof EntityEnderEye) {
                if (player.getServer().getTick() - player.getLastEnderPearlThrowingTick() < 20) {
                    projectile.close();
                    return false;
                }
            }

            ProjectileLaunchEvent ev = new ProjectileLaunchEvent((EntityProjectile) projectile);

            player.getServer().getPluginManager().callEvent(ev);

            if (ev.isCancelled()) {
                projectile.close();
            } else {
                if (!player.isCreative()) {
                    this.count--;
                }
                if (projectile instanceof EntityEnderPearl || projectile instanceof EntityEnderEye) {
                    player.onThrowEnderPearl();
                }
                projectile.spawnToAll();
                player.getLevel().addLevelSoundEvent(player, LevelSoundEventPacket.SOUND_BOW);
            }
        }

        return true;
    }

    protected void correctNBT(CompoundTag nbt) {
    }
}
