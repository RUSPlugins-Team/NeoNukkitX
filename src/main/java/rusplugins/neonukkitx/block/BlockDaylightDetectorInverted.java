package rusplugins.neonukkitx.block;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemBlock;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.BlockFace;

/**
 * Created on 2015/11/22 by CreeperFace.
 * Package rusplugins.neonukkitx.block in project Nukkit .
 */
public class BlockDaylightDetectorInverted extends BlockDaylightDetector {

    @Override
    public int getId() {
        return DAYLIGHT_DETECTOR_INVERTED;
    }

    @Override
    public String getName() {
        return "Daylight Detector Inverted";
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        this.getLevel().setBlock(this, Block.get(DAYLIGHT_DETECTOR));
        return true;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(DAYLIGHT_DETECTOR), 0);
    }

    @Override
    public boolean isPowerSource() {
        return true;
    }

    @Override
    public int getWeakPower(BlockFace face) {
        int time = level.getTime() % Level.TIME_FULL;
        return time < 13184 || time > 22800 ? 0 : 15;
    }
}
