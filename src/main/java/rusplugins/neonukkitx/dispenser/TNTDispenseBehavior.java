package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityPrimedTNT;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.Vector3;

public class TNTDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Vector3 pos = block.getSide(face).add(0.5, 0, 0.5);

        Entity.createEntity(EntityPrimedTNT.NETWORK_ID,
                block.getLevel().getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos)).spawnToAll();

        return null;
    }
}
