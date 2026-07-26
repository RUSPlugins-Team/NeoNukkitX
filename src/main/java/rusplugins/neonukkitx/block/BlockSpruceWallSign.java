package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;

public class BlockSpruceWallSign extends BlockWallSign {

    public BlockSpruceWallSign() {
        this(0);
    }

    public BlockSpruceWallSign(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Spruce Wall Sign";
    }

    @Override
    public int getId() {
        return SPRUCE_WALL_SIGN;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.SPRUCE_SIGN);
    }

    @Override
    protected int getPostId() {
        return SPRUCE_STANDING_SIGN;
    }

    @Override
    protected int getWallId() {
        return SPRUCE_WALL_SIGN;
    }
}
