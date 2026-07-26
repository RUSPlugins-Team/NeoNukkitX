package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockBricksDeepslate extends BlockSolid {

    public BlockBricksDeepslate() {
    }

    @Override
    public int getId() {
        return DEEPSLATE_BRICKS;
    }

    @Override
    public String getName() {
        return "Deepslate Bricks";
    }

    @Override
    public double getHardness() {
        return 3.5;
    }

    @Override
    public double getResistance() {
        return 6;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DEEPSLATE_GRAY_BLOCK_COLOR;
    }
}
