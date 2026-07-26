package rusplugins.neonukkitx.level.generator.object.tree;

import rusplugins.neonukkitx.block.Block;

/**
 * @author FlamingKnight
 */
public class ObjectWarpedTree extends ObjectNetherTree {

    @Override
    public int getTrunkBlock() {
        return Block.WARPED_STEM;
    }

    @Override
    public int getLeafBlock() {
        return Block.WARPED_WART_BLOCK;
    }
}
