package rusplugins.neonukkitx.dispenser;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockDispenser;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.block.BlockLiquid;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBucket;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.particle.SmokeParticle;
import rusplugins.neonukkitx.math.BlockFace;

/**
 * @author CreeperFace
 */
public class BucketDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);

        if (item.getDamage() > 0) {
            if (target.canBeFlowedInto()) {
                Block replace = Block.get(ItemBucket.getBlockByDamage(item.getDamage()));

                if (replace instanceof BlockLiquid) {
                    if (block.level.getDimension() == Level.DIMENSION_NETHER) {
                        replace = Block.get(Block.AIR);
                        block.level.addParticle(new SmokeParticle(target.add(0.5, 0.5, 0.5)), null, 4);
                    }
                    block.level.setBlock(target, replace);
                    return Item.get(Item.BUCKET);
                }
            }
        } else if (target instanceof BlockLiquid && target.getDamage() == 0) {
            target.level.setBlock(target, Block.get(BlockID.AIR));
            return Item.get(Item.BUCKET, ItemBucket.getDamageByTarget(target.getId()));
        }

        return super.dispense(block, face, item);
    }
}
