package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockTrapdoorJungle extends BlockTrapdoor {

    public BlockTrapdoorJungle() {
        this(0);
    }

    public BlockTrapdoorJungle(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Jungle Trapdoor";
    }

    @Override
    public int getId() {
        return JUNGLE_TRAPDOOR;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DIRT_BLOCK_COLOR;
    }
}
