package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockBambooFence extends BlockFence {

    public BlockBambooFence() {
        this(0);
    }

    public BlockBambooFence(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Bamboo Fence";
    }

    @Override
    public int getId() {
        return BAMBOO_FENCE;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.YELLOW_BLOCK_COLOR;
    }
}
