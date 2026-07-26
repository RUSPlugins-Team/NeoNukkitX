package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockBeehive;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.entity.passive.EntitySheep;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.SimpleAxisAlignedBB;

public class ShearsDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);
        item = item.clone();
        for (Entity entity : block.getLevel().getNearbyEntities(new SimpleAxisAlignedBB(
                target.x,
                target.y,
                target.z,
                target.x + 1,
                target.y + 1,
                target.z + 1
        ))) {
            if (entity instanceof EntitySheep) {
                if (!((EntitySheep) entity).isSheared()) {
                    ((EntitySheep) entity).shear(true);
                    item.useOn(entity);
                    return item.getDamage() >= item.getMaxDurability() ? null : item;
                }
            }
        }

        if (target instanceof BlockBeehive && target.onActivate(item, null)) {
            return item.getDamage() >= item.getMaxDurability() ? null : item;
        }

        return item;
    }
}
