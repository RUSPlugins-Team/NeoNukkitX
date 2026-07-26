package rusplugins.neonukkitx.level.generator.object.tree;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.ChunkManager;
import rusplugins.neonukkitx.math.BlockVector3;
import rusplugins.neonukkitx.math.Vector3;

public abstract class TreeGenerator extends rusplugins.neonukkitx.level.generator.object.BasicGenerator {

    /**
     * returns whether or not a tree can grow into a block
     * For example, a tree will not grow into stone
     */
    protected boolean canGrowInto(int id) {
        return id == Item.AIR || id == Item.LEAVES || id == Item.GRASS || id == Item.DIRT || id == Item.LOG || id == Item.LOG2 || id == Item.SAPLING || id == Item.VINE;
    }

    protected void setDirtAt(ChunkManager level, BlockVector3 pos) {
        if (level.getBlockIdAt(pos.x, pos.y, pos.z) != Item.DIRT) {
            this.setBlockAndNotifyAdequately(level, pos, Block.get(BlockID.DIRT));
        }
    }

    /**
     * sets dirt at a specific location if it isn't already dirt
     */
    protected void setDirtAt(ChunkManager level, Vector3 pos) {
        if (level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) != Item.DIRT) {
            this.setBlockAndNotifyAdequately(level, pos, Block.get(BlockID.DIRT));
        }
    }
}
