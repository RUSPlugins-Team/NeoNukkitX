package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;

public class BlockDarkOakSignStanding extends BlockSignPost {

    public BlockDarkOakSignStanding() {
        this(0);
    }

    public BlockDarkOakSignStanding(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Dark Oak Sign Post";
    }

    @Override
    public int getId() {
        return DARK_OAK_STANDING_SIGN;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.DARKOAK_SIGN);
    }

    @Override
    protected int getPostId() {
        return DARK_OAK_STANDING_SIGN;
    }

    @Override
    protected int getWallId() {
        return DARK_OAK_WALL_SIGN;
    }
}
