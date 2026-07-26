package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.item.EntityFirework;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.NBTIO;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class FireworksDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        BlockFace opposite = face.getOpposite();
        Vector3 pos = block.getSide(face).add(0.5 + opposite.getXOffset() * 0.2, 0.5 + opposite.getYOffset() * 0.2, 0.5 + opposite.getZOffset() * 0.2);
        CompoundTag nbt = Entity.getDefaultNBT(pos);
        nbt.putCompound("FireworkItem", NBTIO.putItemHelper(item));
        Entity.createEntity(EntityFirework.NETWORK_ID, block.level.getChunk(pos.getChunkX(), pos.getChunkZ()), nbt).spawnToAll();
        return null;
    }
}
