package rusplugins.neonukkitx.event.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class PlayerToggleGlideEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final boolean isGliding;

    public PlayerToggleGlideEvent(Player player, boolean isGliding) {
        this.player = player;
        this.isGliding = isGliding;
    }

    public boolean isGliding() {
        return this.isGliding;
    }
}
