package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;

public class BlockStairsSmoothRedSandstone extends BlockStairsRedSandstone {

    public BlockStairsSmoothRedSandstone() {
        this(0);
    }

    public BlockStairsSmoothRedSandstone(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Smooth Red Sandstone Stairs";
    }

    @Override
    public int getId() {
        return SMOOTH_RED_SANDSTONE_STAIRS;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }
}
