package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;

public abstract class BlockStemStripped extends BlockStem {

    public BlockStemStripped() {
        this(0);
    }

    public BlockStemStripped(int meta) {
        super(meta);
    }

    @Override
    public boolean canBeActivated() {
        return false;
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        return false;
    }

    @Override
    public int getStrippedId() {
        return this.getId();
    }
}