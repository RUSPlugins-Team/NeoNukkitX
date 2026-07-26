package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDirt;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemPotion;
import rusplugins.neonukkitx.math.BlockFace;

public class PotionDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        if (item.getDamage() == ItemPotion.NO_EFFECTS) {
            Block target = block.getSide(face);

            if (target instanceof BlockDirt) {
                if (target.onActivate(item, null)) {
                    return Item.get(Item.GLASS_BOTTLE);
                }
                return item;
            }
        }

        return super.dispense(block, face, item);
    }
}
