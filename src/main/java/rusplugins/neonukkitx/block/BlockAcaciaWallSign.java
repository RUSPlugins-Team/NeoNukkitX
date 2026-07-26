package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;

public class BlockAcaciaWallSign extends BlockWallSign {

    public BlockAcaciaWallSign() {
        this(0);
    }

    public BlockAcaciaWallSign(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Acacia Wall Sign";
    }

    @Override
    public int getId() {
        return ACACIA_WALL_SIGN;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.ACACIA_SIGN);
    }

    @Override
    protected int getPostId() {
        return ACACIA_STANDING_SIGN;
    }

    @Override
    protected int getWallId() {
        return ACACIA_WALL_SIGN;
    }
}
