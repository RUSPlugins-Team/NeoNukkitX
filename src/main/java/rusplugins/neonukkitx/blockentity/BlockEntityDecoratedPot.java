package rusplugins.neonukkitx.blockentity;

import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class BlockEntityDecoratedPot extends BlockEntity {

    public BlockEntityDecoratedPot(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        return level.getBlockIdAt(chunk, (int) x, (int) y, (int) z) == BlockID.DECORATED_POT;
    }
}
