package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.utils.BlockColor;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class BlockSolid extends Block {

    protected BlockSolid() {
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.STONE_BLOCK_COLOR;
    }
}
