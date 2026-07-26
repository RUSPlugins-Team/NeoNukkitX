package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.utils.BlockColor;

/*
 * Created on 2015/12/11 by Pub4Game.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockRedstone extends BlockSolid {

    @Override
    public int getId() {
        return REDSTONE_BLOCK;
    }

    @Override
    public double getResistance() {
        return 10;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public String getName() {
        return "Block of Redstone";
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (!super.place(item, block, target, face, fx, fy, fz, player)) {
            return false;
        }
        this.level.updateAroundRedstone(this, null);
        return true;
    }

    @Override
    public boolean onBreak(Item item) {
        if (!super.onBreak(item)) {
            return false;
        }
        this.level.updateAroundRedstone(this, null);
        return true;
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
        return BlockColor.REDSTONE_BLOCK_COLOR;
    }

    @Override
    public boolean isPowerSource() {
        return true;
    }

    @Override
    public int getWeakPower(BlockFace face) {
        return 15;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false; // TODO: remove when crash issue fixed
    }
}
