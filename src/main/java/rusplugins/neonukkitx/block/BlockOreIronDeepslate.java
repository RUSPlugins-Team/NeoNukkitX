package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockOreIronDeepslate extends BlockOreIron {

    public BlockOreIronDeepslate() {
    }

    @Override
    protected int getRawMaterial() {
        return ItemID.RAW_IRON;
    }

    @Override
    public int getId() {
        return DEEPSLATE_IRON_ORE;
    }

    @Override
    public double getHardness() {
        return 4.5;
    }

    @Override
    public String getName() {
        return "Deepslate Iron Ore";
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DEEPSLATE_GRAY_BLOCK_COLOR;
    }
}
