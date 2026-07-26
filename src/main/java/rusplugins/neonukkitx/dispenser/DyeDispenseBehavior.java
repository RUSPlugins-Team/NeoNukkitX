package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.*;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.utils.DyeColor;

public class DyeDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (DyeColor.getByDyeData(item.getDamage()) == DyeColor.WHITE) {
            if (target instanceof BlockCrops || target instanceof BlockSapling || target instanceof BlockTallGrass
                    || target instanceof BlockDoublePlant || target instanceof BlockMushroom) {
                target.onActivate(item);

            }

            return null;
        }

        return super.dispense(block, face, item);
    }
}
