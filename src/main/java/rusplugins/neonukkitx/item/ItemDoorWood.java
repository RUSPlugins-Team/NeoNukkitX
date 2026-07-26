package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemDoorWood extends Item {

    public ItemDoorWood() {
        this(0, 1);
    }

    public ItemDoorWood(Integer meta) {
        this(meta, 1);
    }

    public ItemDoorWood(Integer meta, int count) {
        super(WOODEN_DOOR, 0, count, "Oak Door");
        this.block = Block.get(WOODEN_DOOR_BLOCK);
    }
}
