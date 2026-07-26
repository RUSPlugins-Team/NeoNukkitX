package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;

public class ItemSignWarped extends Item {

    public ItemSignWarped() {
        this(0, 1);
    }

    public ItemSignWarped(Integer meta) {
        this(meta, 1);
    }

    public ItemSignWarped(Integer meta, int count) {
        super(WARPED_SIGN, 0, count, "Warped Sign");
        this.block = Block.get(BlockID.WARPED_STANDING_SIGN);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }
}
