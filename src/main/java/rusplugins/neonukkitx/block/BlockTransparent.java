package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.utils.BlockColor;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class BlockTransparent extends Block {

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.TRANSPARENT_BLOCK_COLOR;
    }
}
