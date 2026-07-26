package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;

public class BlockBirchSignStanding extends BlockSignPost {

    public BlockBirchSignStanding() {
        this(0);
    }

    public BlockBirchSignStanding(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Birch Sign Post";
    }

    @Override
    public int getId() {
        return BIRCH_STANDING_SIGN;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.BIRCH_SIGN);
    }

    @Override
    protected int getPostId() {
        return BIRCH_STANDING_SIGN;
    }

    @Override
    protected int getWallId() {
        return BIRCH_WALL_SIGN;
    }
}
