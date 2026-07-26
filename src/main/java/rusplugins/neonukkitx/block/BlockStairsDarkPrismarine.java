package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockStairsDarkPrismarine extends BlockStairs {

    public BlockStairsDarkPrismarine() {
        this(0);
    }

    public BlockStairsDarkPrismarine(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return DARK_PRISMARINE_STAIRS;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public double getResistance() {
        return 6;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Dark Prismarine Stairs";
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DIAMOND_BLOCK_COLOR;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}
