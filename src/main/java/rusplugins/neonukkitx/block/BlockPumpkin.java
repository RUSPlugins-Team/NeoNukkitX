package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.item.ItemID;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.utils.BlockColor;
import rusplugins.neonukkitx.utils.Faceable;

/**
 * Created on 2015/12/8 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockPumpkin extends BlockSolidMeta implements Faceable {

    public BlockPumpkin() {
        this(0);
    }

    public BlockPumpkin(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Pumpkin";
    }

    @Override
    public int getId() {
        return PUMPKIN;
    }

    @Override
    public double getHardness() {
        return 1;
    }

    @Override
    public double getResistance() {
        return 5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(this.getId(), 0), 0);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        this.setBlockFace(player != null ? player.getDirection().getOpposite() : BlockFace.SOUTH);
        this.getLevel().setBlock(block, this, true, true);
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ORANGE_BLOCK_COLOR;
    }

    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromHorizontalIndex(this.getDamage() & 0x7);
    }

    @Override
    public boolean breakWhenPushed() {
        return true;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }


    @Override
    public boolean onActivate(Item item, Player player) {
        if (!item.isShears()) {
            return false;
        }

        BlockPumpkinCarved carvedPumpkin = new BlockPumpkinCarved();
        carvedPumpkin.setBlockFace(this.getBlockFace());
        item.useOn(this);
        this.level.setBlock(this, carvedPumpkin, true, true);
        this.getLevel().dropItem(add(0.5, 0.5, 0.5), Item.get(ItemID.PUMPKIN_SEEDS));
        this.getLevel().dropItem(add(0.5, 0.5, 0.5), Item.get(Item.PUMPKIN_SEEDS));return true;
    }

    public void setBlockFace(BlockFace blockFace) {
        this.setDamage(blockFace.getHorizontalIndex());
    }
}
