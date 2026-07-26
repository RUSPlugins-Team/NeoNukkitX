package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.properties.OxidizationLevel;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.utils.BlockColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockSlabCopperBase extends BlockSlab implements Waxable, Oxidizable {
    
    public BlockSlabCopperBase(int meta, int doubleSlab) {
        super(meta, doubleSlab);
    }

    @Override
    public boolean onActivate(@Nonnull Item item, @Nullable Player player) {
        return Waxable.super.onActivate(item, player)
                || Oxidizable.super.onActivate(item, player);
    }

    @Override
    public int onUpdate(int type) {
        return Oxidizable.super.onUpdate(type);
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ORANGE_BLOCK_COLOR;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_STONE;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public Block getStateWithOxidizationLevel(OxidizationLevel oxidizationLevel) {
        return Block.get(this.getCopperId(this.isWaxed(), oxidizationLevel), this.getDamage());
    }

    @Override
    public boolean setOxidizationLevel(OxidizationLevel oxidizationLevel) {
        if (this.getOxidizationLevel().equals(oxidizationLevel)) {
            return true;
        }
        return this.level.setBlock(this, Block.get(this.getCopperId(this.isWaxed(), oxidizationLevel)));
    }

    @Override
    public boolean setWaxed(boolean waxed) {
        if (this.isWaxed() == waxed) {
            return true;
        }
        return this.level.setBlock(this, Block.get(getCopperId(waxed, getOxidizationLevel())));
    }

    @Override
    public boolean isWaxed() {
        return false;
    }

    protected abstract int getCopperId(boolean waxed, OxidizationLevel oxidizationLevel);
}
