package rusplugins.neonukkitx.item.food;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.entity.EntityPotionEffectEvent;
import rusplugins.neonukkitx.item.Item;

/**
 * Created by Snake1999 on 2016/1/21.
 * Package rusplugins.neonukkitx.item.food in project nukkit.
 */
public class FoodMilk extends Food {

    @Override
    protected boolean onEatenBy(Player player) {
        super.onEatenBy(player);
        player.getInventory().addItem(Item.get(Item.BUCKET));
        player.removeAllEffects(EntityPotionEffectEvent.Cause.MILK);
        return true;
    }
}
