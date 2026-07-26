package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.event.block.BlockFadeEvent;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.level.Level;

/**
 * Created on 2015/12/6 by xtypr.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockOreRedstoneGlowing extends BlockOreRedstone {

    @Override
    public String getName() {
        return "Glowing Redstone Ore";
    }

    @Override
    public int getId() {
        return GLOWING_REDSTONE_ORE;
    }

    @Override
    public int getLightLevel() {
        return 9;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(REDSTONE_ORE));
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_SCHEDULED || type == Level.BLOCK_UPDATE_RANDOM) {
            BlockFadeEvent event = new BlockFadeEvent(this, get(REDSTONE_ORE));
            level.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                level.setBlock(this, event.getNewState(), false, true);
            }

            return Level.BLOCK_UPDATE_WEAK;
        }

        return 0;
    }
}
