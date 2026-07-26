package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.blockentity.BlockEntityDispenser;
import rusplugins.neonukkitx.item.Item;

public class DispenserInventory extends ContainerInventory {

    public DispenserInventory(BlockEntityDispenser dispenser) {
        super(dispenser, InventoryType.DISPENSER);
    }

    @Override
    public BlockEntityDispenser getHolder() {
        return (BlockEntityDispenser) super.getHolder();
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        super.onSlotChange(index, before, send);

        this.getHolder().chunk.setChanged();
    }
}
