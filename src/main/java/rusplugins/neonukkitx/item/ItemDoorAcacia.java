package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.block.Block;

public class ItemDoorAcacia extends Item {

    public ItemDoorAcacia() {
        this(0, 1);
    }

    public ItemDoorAcacia(Integer meta) {
        this(meta, 1);
    }

    public ItemDoorAcacia(Integer meta, int count) {
        super(ACACIA_DOOR, 0, count, "Acacia Door");
        this.block = Block.get(ACACIA_DOOR_BLOCK);
    }
}
