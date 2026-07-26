package rusplugins.neonukkitx.event.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.item.Item;

/**
 * @author CreeperFace
 */
public class PlayerBlockPickEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block blockClicked;
    private Item item;

    public PlayerBlockPickEvent(Player player, Block blockClicked, Item item) {
        this.blockClicked = blockClicked;
        this.item = item;
        this.player = player;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }
}
