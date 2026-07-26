package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.block.properties.OxidizationLevel;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockCopperTrapdoorExposed extends BlockCopperTrapdoor {

    public BlockCopperTrapdoorExposed() {
        this(0);
    }

    public BlockCopperTrapdoorExposed(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Exposed Copper Trapdoor";
    }

    @Override
    public int getId() {
        return EXPOSED_COPPER_TRAPDOOR;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.LIGHT_GRAY_TERRACOTA_BLOCK_COLOR;
    }

    @Override
    public OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.EXPOSED;
    }
}
