package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.blockentity.BlockEntity;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class ShulkerBoxDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (target.getId() == Block.AIR) {
            CompoundTag nbt = BlockEntity.getDefaultCompound(target, BlockEntity.SHULKER_BOX);
            nbt.putByte("facing", BlockFace.UP.getIndex());

            if (item.hasCustomName()) {
                nbt.putString("CustomName", item.getCustomName());
            }

            CompoundTag tag = item.getNamedTag();

            if (tag != null) {
                if (tag.contains("Items")) {
                    nbt.putList(tag.getList("Items"));
                }
            }

            block.level.setBlock(target, Block.get(BlockID.SHULKER_BOX, item.getDamage()), true);
            BlockEntity.createBlockEntity(BlockEntity.SHULKER_BOX, block.level.getChunk(target.getChunkX(), target.getChunkZ()), nbt);
            return null;
        }

        return item;
    }
}
