package rusplugins.neonukkitx.event.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;

public class PlayerToggleCrawlEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final boolean isCrawling;

    public PlayerToggleCrawlEvent(Player player, boolean isCrawling) {
        this.player = player;
        this.isCrawling = isCrawling;
    }

    public boolean isCrawling() {
        return this.isCrawling;
    }
}
