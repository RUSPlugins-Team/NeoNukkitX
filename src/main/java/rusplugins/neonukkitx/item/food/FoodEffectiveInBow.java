package rusplugins.neonukkitx.item.food;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;

public class FoodEffectiveInBow extends FoodEffective {

    public FoodEffectiveInBow(int restoreFood, float restoreSaturation) {
        super(restoreFood, restoreSaturation);
    }

    @Override
    protected boolean onEatenBy(Player player) {
        super.onEatenBy(player);
        player.getInventory().addItem(Item.get(Item.BOWL));; // TODO: set to same slot but don't have it replaced
        return true;
    }
}
