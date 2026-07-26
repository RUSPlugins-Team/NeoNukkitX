package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.block.BlockID;

public class ItemItemFrameGlow extends Item {

    public ItemItemFrameGlow() {
        this(0, 1);
    }

    public ItemItemFrameGlow(Integer meta) {
        this(meta, 1);
    }

    public ItemItemFrameGlow(Integer meta, int count) {
        super(GLOW_ITEM_FRAME, meta, count, "Glow Item Frame");
        this.block = Block.get(BlockID.GLOW_FRAME);
    }
}