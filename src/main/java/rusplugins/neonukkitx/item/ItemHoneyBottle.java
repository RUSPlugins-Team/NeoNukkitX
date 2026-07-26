package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.entity.EntityPotionEffectEvent;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.potion.Effect;

/**
 * @author Kaooot
 */
public class ItemHoneyBottle extends ItemEdible {
    
    public ItemHoneyBottle() {
        this(0, 1);
    }
    
    public ItemHoneyBottle(Integer meta) {
        this(meta, 1);
    }
    
    public ItemHoneyBottle(Integer meta, int count) {
        super(HONEY_BOTTLE, meta, count, "Honey Bottle");
    }
    
    @Override
    public int getMaxStackSize() {
        return 16;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return true;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        if (super.onUse(player, ticksUsed)) {
            player.removeEffect(Effect.POISON, EntityPotionEffectEvent.Cause.FOOD);

            if (!player.isCreative()) {
                player.getInventory().addItem(Item.get(Item.BOTTLE, 0, 1));
            }
        }
        return true;
    }

    @Override
    public boolean isDrink() {
        return true;
    }
}
