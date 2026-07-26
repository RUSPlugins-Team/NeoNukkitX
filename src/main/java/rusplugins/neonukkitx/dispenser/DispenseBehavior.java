package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;

/**
 * @author CreeperFace
 */
public interface DispenseBehavior {

    Item dispense(BlockDispenser block, BlockFace face, Item item);
}
