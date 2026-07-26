package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.blockentity.BlockEntitySmoker;

public class SmokerInventory extends FurnaceInventory {

    public SmokerInventory(BlockEntitySmoker smoker) {
        super(smoker, InventoryType.SMOKER);
    }

    @Override
    public BlockEntitySmoker getHolder() {
        return (BlockEntitySmoker) this.holder;
    }
}
