package rusplugins.neonukkitx.event.block;

import rusplugins.neonukkitx.block.Block;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.item.Item;

public class BlockHarvestEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Block newState;
    private Item[] drops;

    public BlockHarvestEvent(Block block, Block newState, Item[] drops) {
        super(block);
        this.newState = newState;
        this.drops = drops;
    }

    public Block getNewState() {
        return newState;
    }

    public void setNewState(Block newState) {
        this.newState = newState;
    }

    public Item[] getDrops() {
        return drops;
    }

    public void setDrops(Item[] drops) {
        this.drops = drops;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}