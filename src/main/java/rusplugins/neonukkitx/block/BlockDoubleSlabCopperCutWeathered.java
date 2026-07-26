package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.block.properties.OxidizationLevel;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockDoubleSlabCopperCutWeathered extends BlockDoubleSlabCopperCut {
    
    public BlockDoubleSlabCopperCutWeathered() {
        this(0);
    }
    
    public BlockDoubleSlabCopperCutWeathered(int meta) {
        super(meta);
    }

    @Override
    public int getSingleSlabId() {
        return WEATHERED_CUT_COPPER_SLAB;
    }

    @Override
    public int getId() {
        return WEATHERED_DOUBLE_CUT_COPPER_SLAB;
    }

    @Override
    public OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.WEATHERED;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.WARPED_STEM_BLOCK_COLOR;
    }
}
