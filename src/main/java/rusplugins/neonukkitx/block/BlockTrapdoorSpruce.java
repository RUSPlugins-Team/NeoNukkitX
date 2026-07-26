package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockTrapdoorSpruce extends BlockTrapdoor {

    public BlockTrapdoorSpruce() {
        this(0);
    }

    public BlockTrapdoorSpruce(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Spruce Trapdoor";
    }

    @Override
    public int getId() {
        return SPRUCE_TRAPDOOR;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.SPRUCE_BLOCK_COLOR;
    }
}
