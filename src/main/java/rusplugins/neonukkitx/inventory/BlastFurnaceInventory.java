package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.blockentity.BlockEntityBlastFurnace;

public class BlastFurnaceInventory extends FurnaceInventory {

    public BlastFurnaceInventory(BlockEntityBlastFurnace furnace) {
        super(furnace, InventoryType.BLAST_FURNACE);
    }

    @Override
    public BlockEntityBlastFurnace getHolder() {
        return (BlockEntityBlastFurnace) this.holder;
    }
}
