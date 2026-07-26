package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemSeedsWheat extends Item {

    public ItemSeedsWheat() {
        this(0, 1);
    }

    public ItemSeedsWheat(Integer meta) {
        this(meta, 1);
    }

    public ItemSeedsWheat(Integer meta, int count) {
        super(WHEAT_SEEDS, 0, count, "Seeds");
        this.block = Block.get(WHEAT_BLOCK);
    }
}
