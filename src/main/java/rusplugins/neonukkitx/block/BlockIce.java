package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.event.block.BlockFadeEvent;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.utils.BlockColor;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class BlockIce extends BlockTransparent {

    @Override
    public int getId() {
        return ICE;
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public double getResistance() {
        return 0.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public double getFrictionFactor() {
        return 0.98;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public boolean onBreak(Item item) {
        if (this.getLevel().getDimension() == Level.DIMENSION_NETHER || item.hasEnchantment(Enchantment.ID_SILK_TOUCH) || down().getId() == BlockID.AIR) {
            return super.onBreak(item);
        }
        return this.getLevel().setBlock(this, Block.get(BlockID.WATER), true);
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (level.getBlockLightAt((int) this.x, (int) this.y, (int) this.z) >= 12) {
                BlockFadeEvent event = new BlockFadeEvent(this, level.getDimension() == Level.DIMENSION_NETHER ? get(AIR) : get(WATER));
                level.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    level.setBlock(this, event.getNewState(), true);
                }
                return Level.BLOCK_UPDATE_RANDOM;
            }
        }
        return 0;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.hasEnchantment(Enchantment.ID_SILK_TOUCH)) {
            return new Item[]{this.toItem()};
        }
        return new Item[0];
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ICE_BLOCK_COLOR;
    }
    
    @Override
    public boolean canSilkTouch() {
        return true;
    }
}
