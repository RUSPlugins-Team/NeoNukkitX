package rusplugins.neonukkitx.blockentity;

import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class BlockEntitySculkSensor extends BlockEntity {

    public BlockEntitySculkSensor(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        return level.getBlockIdAt(chunk, (int) x, (int) y, (int) z) == BlockID.SCULK_SENSOR;
    }
}
