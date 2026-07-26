package rusplugins.neonukkitx.blockentity;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

public class BlockEntityItemFrameGlow extends BlockEntityItemFrame {

    public BlockEntityItemFrameGlow(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public String getName() {
        return "Glow Item Frame";
    }

    @Override
    public boolean isBlockEntityValid() {
        return level.getBlockIdAt(chunk, (int) x, (int) y, (int) z) == Block.GLOW_FRAME;
    }
}
