package rusplugins.neonukkitx.event.level;

import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.level.Position;

public class NetherPortalSpawnEvent extends LevelEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Position portalPosition;

    public NetherPortalSpawnEvent(Position portalPosition) {
        super(portalPosition.getLevel());

        this.portalPosition = portalPosition.clone();
    }

    public Position getPortalPosition() {
        return this.portalPosition;
    }
}
