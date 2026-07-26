package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.utils.BlockColor;

/**
 * Created on 2015/12/7 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockFenceNetherBrick extends BlockFence {

    public BlockFenceNetherBrick() {
        this(0);
    }

    public BlockFenceNetherBrick(int meta) {
        super(meta);
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Nether Brick Fence";
    }

    @Override
    public int getId() {
        return NETHER_BRICK_FENCE;
    }

    @Override
    public double getResistance() {
        return 10;
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
    public boolean canConnect(Block block) {
        return (block instanceof BlockFenceNetherBrick || block instanceof BlockFenceGate) || block.isSolid() && !block.isTransparent();
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.NETHERRACK_BLOCK_COLOR;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public int getBurnChance() {
        return 0;
    }

    @Override
    public int getBurnAbility() {
        return 0;
    }
}
