package rusplugins.neonukkitx.event.potion;

import rusplugins.neonukkitx.entity.item.EntityPotion;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.potion.Potion;

/**
 * Created by Snake1999 on 2016/1/12.
 * Package rusplugins.neonukkitx.event.potion in project nukkit
 */
public class PotionCollideEvent extends PotionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityPotion thrownPotion;

    public PotionCollideEvent(Potion potion, EntityPotion thrownPotion) {
        super(potion);
        this.thrownPotion = thrownPotion;
    }

    public EntityPotion getThrownPotion() {
        return thrownPotion;
    }
}
