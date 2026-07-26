package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.projectile.EntityProjectile;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.Vector3;

public class FireChargeDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Vector3 dispensePos = block.getDispensePosition();

        Entity projectile = Entity.createEntity("BlazeFireBall", block.level.getChunk(dispensePos.getChunkX(), dispensePos.getChunkZ()), Entity.getDefaultNBT(dispensePos));

        if (!(projectile instanceof EntityProjectile)) {
            return super.dispense(block, face, item);
        }

        projectile.setMotion(new Vector3(face.getXOffset(), face.getYOffset() + 0.1f, face.getZOffset()).normalize().multiply(1.3));
        ((EntityProjectile) projectile).inaccurate(6f);
        ((EntityProjectile) projectile).updateRotation();

        projectile.spawnToAll();
        return null;
    }
}
