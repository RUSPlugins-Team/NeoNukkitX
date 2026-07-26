package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.entity.mob.EntityEnderman;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class EndermanBlockPickUpEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block block;

    public EndermanBlockPickUpEvent(EntityEnderman entity, Block block) {
        this.entity = entity;
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    @Override
    public EntityEnderman getEntity() {
        return (EntityEnderman) super.getEntity();
    }
}
