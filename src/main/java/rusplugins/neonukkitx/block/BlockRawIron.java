package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.utils.BlockColor;

public class BlockRawIron extends BlockRawOreVariant {

    public BlockRawIron() {
    }

    @Override
    public String getName() {
        return "Block of Raw Iron";
    }

    @Override
    public int getId() {
        return RAW_IRON_BLOCK;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.RAW_IRON_BLOCK_COLOR;
    }
}
