package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.block.BlockWater;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityBoat;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.Location;
import rusplugins.neonukkitx.math.BlockFace;

public class BoatDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (!(target instanceof BlockWater)) {
            if (target.getId() != BlockID.AIR || !(target.down() instanceof BlockWater)) {
                return super.dispense(block, face, item);
            }
        }

        Location pos = target.getLocation().setYaw(face.getHorizontalAngle());

        EntityBoat boat = (EntityBoat) Entity.createEntity(EntityBoat.NETWORK_ID, block.level.getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos)
                        .putByte("woodID", item.getDamage())
        );

        boat.spawnToAll();

        return null;
    }
}
