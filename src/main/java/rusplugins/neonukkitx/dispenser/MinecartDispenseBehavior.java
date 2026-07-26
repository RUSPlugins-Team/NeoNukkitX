package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.block.BlockRail;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.nbt.tag.DoubleTag;
import rusplugins.neonukkitx.nbt.tag.FloatTag;
import rusplugins.neonukkitx.nbt.tag.ListTag;
import rusplugins.neonukkitx.utils.Rail;

public class MinecartDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (Rail.isRailBlock(target)) {
            Rail.Orientation type = ((BlockRail) target).getOrientation();
            double adjacent = 0.0D;
            if (type.isAscending()) {
                adjacent = 0.5D;
            }

            Entity minecart = Entity.createEntity(getMinecartId(item),
                    block.level.getChunk(target.getChunkX(), target.getChunkZ()), new CompoundTag("")
                    .putList(new ListTag<>("Pos")
                            .add(new DoubleTag("", target.getX() + 0.5))
                            .add(new DoubleTag("", target.getY() + 0.0625D + adjacent))
                            .add(new DoubleTag("", target.getZ() + 0.5)))
                    .putList(new ListTag<>("Motion")
                            .add(new DoubleTag("", 0))
                            .add(new DoubleTag("", 0))
                            .add(new DoubleTag("", 0)))
                    .putList(new ListTag<>("Rotation")
                            .add(new FloatTag("", 0))
                            .add(new FloatTag("", 0)))
            );

            minecart.spawnToAll();
            return null;
        }

        return super.dispense(block, face, item);
    }

    private String getMinecartId(Item item) {
        switch (item.getId()) {
            case ItemID.MINECART_WITH_CHEST:
                return "MinecartChest";
            case ItemID.MINECART_WITH_HOPPER:
                return "MinecartHopper";
            case ItemID.MINECART_WITH_TNT:
                return "MinecartTnt";
        }
        return "MinecartRideable";
    }
}
