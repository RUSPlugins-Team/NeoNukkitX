package rusplugins.neonukkitx.item.food;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;

/**
 * Created by Snake1999 on 2016/1/14.
 * Package rusplugins.neonukkitx.item.food in project nukkit.
 */
public class FoodInBowl extends Food {

    public FoodInBowl(int restoreFood, float restoreSaturation) {
        this.setRestoreFood(restoreFood);
        this.setRestoreSaturation(restoreSaturation);
    }

    @Override
    protected boolean onEatenBy(Player player) {
        super.onEatenBy(player);
        player.getInventory().addItem(Item.get(Item.BOWL)); // TODO: set to same slot but don't have it replaced
        return true;
    }
}
