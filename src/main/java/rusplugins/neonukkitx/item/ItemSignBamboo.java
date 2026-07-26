package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;

public class ItemSignBamboo extends Item {

    public ItemSignBamboo() {
        this(0, 1);
    }

    public ItemSignBamboo(Integer meta) {
        this(meta, 1);
    }

    public ItemSignBamboo(Integer meta, int count) {
        super(BAMBOO_SIGN, 0, count, "Bamboo Sign");
        this.block = Block.get(BlockID.BAMBOO_STANDING_SIGN);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }
}
