package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockDoorMangrove extends BlockDoorWood {

    public BlockDoorMangrove() {
        this(0);
    }

    public BlockDoorMangrove(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Mangrove Door Block";
    }

    @Override
    public int getId() {
        return MANGROVE_DOOR_BLOCK;
    }

    @Override
    public Item toItem() {
        return Item.get(ItemID.MANGROVE_DOOR);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.RED_BLOCK_COLOR;
    }
}
