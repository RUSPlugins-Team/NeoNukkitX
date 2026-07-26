package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.entity.projectile.EntityThrownTrident;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

/**
 * @author CreeperFace
 */
public class ProjectileDispenseBehavior extends DefaultDispenseBehavior {

    private final String entityType;

    public ProjectileDispenseBehavior(String entity) {
        this.entityType = entity;
    }

    @Override
    public Item dispense(BlockDispenser source, BlockFace face, Item item) {
        Vector3 dispensePos = source.getDispensePosition();

        CompoundTag nbt = Entity.getDefaultNBT(dispensePos);
        this.correctNBT(nbt, item);

        Entity projectile = Entity.createEntity(entityType, source.level.getChunk(dispensePos.getChunkX(), dispensePos.getChunkZ()), nbt);

        if (!(projectile instanceof EntityProjectile)) {
            return super.dispense(source, face, item);
        }

        Vector3 motion = new Vector3(face.getXOffset(), face.getYOffset() + 0.1f, face.getZOffset())
                .normalize();

        projectile.setMotion(motion);
        ((EntityProjectile) projectile).inaccurate(getAccuracy());
        projectile.setMotion(projectile.getMotion().multiply(getMotion()));

        ((EntityProjectile) projectile).updateRotation();

        if (projectile instanceof EntityThrownTrident) {
            item.setDamage(item.getDamage() + 1);
            ((EntityThrownTrident) projectile).setItem(item);
        }

        projectile.spawnToAll();
        return null;
    }

    protected double getMotion() {
        return 1.1;
    }

    protected float getAccuracy() {
        return 6;
    }

    /**
     * You can add extra data of projectile here
     *
     * @param nbt tag
     * @param item item
     */
    protected void correctNBT(CompoundTag nbt, Item item) {
        if (item != null) {
            if (item.getId() == ItemID.SPLASH_POTION || item.getId() == ItemID.LINGERING_POTION) {
                nbt.putInt("PotionId", item.getDamage());
            } else if (item.getId() == ItemID.ARROW && item.getDamage() > 0) {
                nbt.putByte("arrowData", item.getDamage());
            }
        }
    }
}
