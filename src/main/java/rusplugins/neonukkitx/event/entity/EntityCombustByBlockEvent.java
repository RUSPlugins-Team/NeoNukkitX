package rusplugins.neonukkitx.event.entity;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.entity.Entity;

/**
 * @author Box
 * Nukkit Project
 */
public class EntityCombustByBlockEvent extends EntityCombustEvent {

    protected final Block combuster;

    public EntityCombustByBlockEvent(Block combuster, Entity combustee, int duration) {
        super(combustee, duration);
        this.combuster = combuster;
    }

    public Block getCombuster() {
        return combuster;
    }
}
