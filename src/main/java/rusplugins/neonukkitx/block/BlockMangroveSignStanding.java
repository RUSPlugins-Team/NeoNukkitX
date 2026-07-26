package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;

public class BlockMangroveSignStanding extends BlockSignPost {

    public BlockMangroveSignStanding() {
        this(0);
    }

    public BlockMangroveSignStanding(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Mangrove Sign Post";
    }

    @Override
    public int getId() {
        return MANGROVE_STANDING_SIGN;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.MANGROVE_SIGN);
    }

    @Override
    protected int getPostId() {
        return MANGROVE_STANDING_SIGN;
    }

    @Override
    protected int getWallId() {
        return MANGROVE_WALL_SIGN;
    }
}
