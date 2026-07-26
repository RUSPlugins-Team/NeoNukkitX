package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.level.generator.object.mushroom.BigMushroom;
import rusplugins.neonukkitx.utils.BlockColor;
import rusplugins.neonukkitx.utils.Utils;

/**
 * Created by Pub4Game on 28.01.2016.
 */
public class BlockHugeMushroomBrown extends BlockSolidMeta {

    public BlockHugeMushroomBrown() {
        this(0);
    }

    public BlockHugeMushroomBrown(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Brown Mushroom Block";
    }

    @Override
    public int getId() {
        return BROWN_MUSHROOM_BLOCK;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item != null && item.hasEnchantment(Enchantment.ID_SILK_TOUCH)) {
            return new Item[]{this.toItem()};
        }
        return new Item[]{new ItemBlock(Block.get(BROWN_MUSHROOM), 0, Utils.rand() ? Utils.rand(0, 2) : 0)};
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.WOOD_BLOCK_COLOR;
    }

    @Override
    public Item toItem() {
        int type = this.getDamage() == BigMushroom.STEM ? BigMushroom.ALL_STEM : BigMushroom.ALL_OUTSIDE;
        return new ItemBlock(Block.get(this.getId(), type), type);
    }
}
