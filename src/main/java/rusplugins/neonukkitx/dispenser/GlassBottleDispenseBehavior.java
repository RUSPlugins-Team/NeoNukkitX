package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockBeehive;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.block.BlockWater;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.math.BlockFace;

public class GlassBottleDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (target instanceof BlockBeehive) {
            if (target.onActivate(item, null)) {
                return Item.get(Item.HONEY_BOTTLE);
            }
            return item;
        }

        if (target instanceof BlockWater && target.getDamage() == 0) {
            return Item.get(ItemID.POTION, 0, 1);
        }

        return super.dispense(block, face, item);
    }
}
