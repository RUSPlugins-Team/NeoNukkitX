package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.utils.BlockColor;
import rusplugins.neonukkitx.utils.DyeColor;

/**
 * Created by CreeperFace on 2.6.2017.
 */
public class BlockConcretePowder extends BlockFallableMeta {

    public BlockConcretePowder() {
        super(0);
    }

    public BlockConcretePowder(int meta) {
        super(meta);
    }

    @Override
    public int getFullId() {
        return (CONCRETE_POWDER << Block.DATA_BITS) + getDamage();
    }

    @Override
    public int getId() {
        return CONCRETE_POWDER;
    }

    @Override
    public String getName() {
        return getDyeColor().getName() + " Concrete Powder";
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }
    
    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            super.onUpdate(Level.BLOCK_UPDATE_NORMAL);

            for (int side = 1; side <= 5; side++) {
                Block block = this.getSide(BlockFace.fromIndex(side));
                if (block.getId() == Block.WATER || block.getId() == Block.STILL_WATER) {
                    this.level.setBlock(this, Block.get(Block.CONCRETE, this.getDamage()), true, true);
                }
            }

            return Level.BLOCK_UPDATE_NORMAL;
        }
        return 0;
    }

    @Override
    public boolean place(Item item, Block b, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        boolean concrete = false;

        for (int side = 1; side <= 5; side++) {
            Block block = this.getSide(BlockFace.fromIndex(side));
            if (block.getId() == Block.WATER || block.getId() == Block.STILL_WATER) {
                concrete = true;
                break;
            }
        }

        if (concrete) {
            this.level.setBlock(this, Block.get(Block.CONCRETE, this.getDamage()), true, true);
        } else {
            this.level.setBlock(this, this, true, true);
        }

        return true;
    }

    @Override
    public BlockColor getColor() {
        return DyeColor.getByWoolData(getDamage()).getColor();
    }

    public DyeColor getDyeColor() {
        return DyeColor.getByWoolData(getDamage());
    }
}
