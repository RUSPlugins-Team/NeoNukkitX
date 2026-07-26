package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;

public class ItemCampfire extends Item {

    public ItemCampfire() {
        this(0, 1);
    }

    public ItemCampfire(Integer meta) {
        this(meta, 1);
    }

    public ItemCampfire(Integer meta, int count) {
        super(CAMPFIRE, meta, count, "Campfire");
        this.block = Block.get(CAMPFIRE_BLOCK);
    }
}
