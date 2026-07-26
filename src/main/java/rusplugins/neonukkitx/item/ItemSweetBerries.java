package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;

public class ItemSweetBerries extends ItemEdible {

    public ItemSweetBerries() {
        this(0, 1);
    }

    public ItemSweetBerries(Integer meta) {
        this(meta, 1);
    }

    public ItemSweetBerries(Integer meta, int count) {
        super(SWEET_BERRIES, meta, count, "Sweet Berries");
        this.block = Block.get(SWEET_BERRY_BUSH);
    }
}
