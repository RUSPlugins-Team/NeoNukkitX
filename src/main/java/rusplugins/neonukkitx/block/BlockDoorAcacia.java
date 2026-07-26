package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.utils.BlockColor;

public class BlockDoorAcacia extends BlockDoorWood {

    public BlockDoorAcacia() {
        this(0);
    }

    public BlockDoorAcacia(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Acacia Door Block";
    }

    @Override
    public int getId() {
        return ACACIA_DOOR_BLOCK;
    }

    @Override
    public Item toItem() {
        return Item.get(Item.ACACIA_DOOR);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ORANGE_BLOCK_COLOR;
    }
}
