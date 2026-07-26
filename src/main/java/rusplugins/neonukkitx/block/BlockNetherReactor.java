package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;

public class BlockNetherReactor extends BlockSolid {

    @Override
    public int getId() {
        return NETHER_REACTOR;
    }

    @Override
    public String getName() {
        return "Nether Reactor Core";
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{Item.get(Item.DIAMOND, 0, 3), Item.get(Item.IRON_INGOT, 0, 6)};
        } else return new Item[0];
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}
