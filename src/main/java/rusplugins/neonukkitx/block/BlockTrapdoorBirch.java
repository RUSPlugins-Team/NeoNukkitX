package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockTrapdoorBirch extends BlockTrapdoor {

    public BlockTrapdoorBirch() {
        this(0);
    }

    public BlockTrapdoorBirch(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Birch Trapdoor";
    }

    @Override
    public int getId() {
        return BIRCH_TRAPDOOR;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.SAND_BLOCK_COLOR;
    }
}
