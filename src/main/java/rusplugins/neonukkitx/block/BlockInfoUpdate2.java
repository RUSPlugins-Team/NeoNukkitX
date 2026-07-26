package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;

public class BlockInfoUpdate2 extends BlockSolid {

    @Override
    public int getId() {
        return INFO_UPDATE2;
    }

    @Override
    public String getName() {
        return "Update Game Block";
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[0];
    }
}
