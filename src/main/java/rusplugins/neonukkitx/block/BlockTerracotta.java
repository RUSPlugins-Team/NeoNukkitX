package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.utils.BlockColor;
import rusplugins.neonukkitx.utils.DyeColor;
import rusplugins.neonukkitx.utils.TerracottaColor;

/**
 * Created on 2015/11/24 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockTerracotta extends BlockSolidMeta {

    public BlockTerracotta() {
        this(0);
    }

    public BlockTerracotta(int meta) {
        super(0);
    }

    public BlockTerracotta(DyeColor dyeColor) {
        this(dyeColor.getWoolData());
    }

    public BlockTerracotta(TerracottaColor dyeColor) {
        this(dyeColor.getTerracottaData());
    }

    @Override
    public int getId() {
        return TERRACOTTA;
    }

    @Override
    public String getName() {
        return "Terracotta";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 1.25;
    }

    @Override
    public double getResistance() {
        return 7;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }

    @Override
    public BlockColor getColor() {
        return TerracottaColor.getByTerracottaData(getDamage()).getColor();
    }

    public TerracottaColor getDyeColor() {
        return TerracottaColor.getByTerracottaData(getDamage());
    }
}
