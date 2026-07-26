package rusplugins.neonukkitx.level.generator.object.tree;

import rusplugins.neonukkitx.block.Block;

/**
 * @author FlamingKnight
 */
public class ObjectCrimsonTree extends ObjectNetherTree {

    @Override
    public int getTrunkBlock() {
        return Block.CRIMSON_STEM;
    }

    @Override
    public int getLeafBlock() {
        return Block.BLOCK_NETHER_WART_BLOCK;
    }
}