package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.utils.BlockColor;

/**
 * Created on 2015/11/25 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockStairsSpruce extends BlockStairsWood {

    public BlockStairsSpruce() {
        this(0);
    }

    public BlockStairsSpruce(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return SPRUCE_WOOD_STAIRS;
    }

    @Override
    public String getName() {
        return "Spruce Stairs";
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.SPRUCE_BLOCK_COLOR;
    }
}
