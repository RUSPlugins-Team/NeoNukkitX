package rusplugins.neonukkitx.blockentity;

import rusplugins.neonukkitx.block.BlockID;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class BlockEntityCrafter extends BlockEntity {

    public BlockEntityCrafter(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        return level.getBlockIdAt(chunk, (int) x, (int) y, (int) z) == BlockID.CRAFTER;
    }
}
