package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class EntityDamageBlockedEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityDamageEvent damage;
    private boolean knockBackAttacker;
    private boolean animation;

    public EntityDamageBlockedEvent(Entity entity, EntityDamageEvent damage, boolean knockBack, boolean animation) {
        this.entity = entity;
        this.damage = damage;
        this.knockBackAttacker = knockBack;
        this.animation = animation;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return damage.getCause();
    }

    public Entity getAttacker() {
        if (damage instanceof EntityDamageByEntityEvent) {
            return ((EntityDamageByEntityEvent) damage).getDamager();
        }
        return damage.getEntity();
    }

    public EntityDamageEvent getDamage() {
        return damage;
    }

    public boolean getKnockBackAttacker() {
        return knockBackAttacker;
    }

    public boolean getAnimation() {
        return animation;
    }

    public void setKnockBackAttacker(boolean val) {
        knockBackAttacker = val;
    }

    public void setAnimation(boolean val) {
        animation = val;
    }
}
