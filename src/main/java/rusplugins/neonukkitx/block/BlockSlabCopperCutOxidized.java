package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.block.properties.OxidizationLevel;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockSlabCopperCutOxidized extends BlockSlabCopperCut {
    
    public BlockSlabCopperCutOxidized() {
        this(0);
    }
    
    public BlockSlabCopperCutOxidized(int meta) {
        super(meta, OXIDIZED_DOUBLE_CUT_COPPER_SLAB);
    }
    
    protected BlockSlabCopperCutOxidized(int meta, int doubleSlab) {
        super(meta, doubleSlab);
    }

    @Override
    public int getId() {
        return OXIDIZED_CUT_COPPER_SLAB;
    }

    @Override
    public OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.OXIDIZED;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.WARPED_NYLIUM_BLOCK_COLOR;
    }
}
