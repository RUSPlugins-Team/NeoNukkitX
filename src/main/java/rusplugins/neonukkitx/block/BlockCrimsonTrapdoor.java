package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;

public class BlockCrimsonTrapdoor extends BlockTrapdoor {

    public BlockCrimsonTrapdoor() {
        this(0);
    }

    public BlockCrimsonTrapdoor(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Crimson Trapdoor";
    }

    @Override
    public int getId() {
        return CRIMSON_TRAPDOOR;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }
}
