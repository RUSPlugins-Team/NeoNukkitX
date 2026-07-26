package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.block.properties.OxidizationLevel;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockCopperDoorOxidized extends BlockCopperDoor {

    public BlockCopperDoorOxidized() {
        this(0);
    }

    public BlockCopperDoorOxidized(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Oxidized Copper Door";
    }

    @Override
    public int getId() {
        return OXIDIZED_COPPER_DOOR_BLOCK;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.WARPED_NYLIUM_BLOCK_COLOR;
    }

    @Override
    public OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.OXIDIZED;
    }
}
