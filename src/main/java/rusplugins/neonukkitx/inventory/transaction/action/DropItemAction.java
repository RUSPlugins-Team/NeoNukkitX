package rusplugins.neonukkitx.inventory.transaction.action;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.player.PlayerDropItemEvent;
import rusplugins.neonukkitx.item.Item;

/**
 * @author CreeperFace
 */
public class DropItemAction extends InventoryAction {

    public DropItemAction(Item source, Item target) {
        super(source, target);
    }

    /**
     * Verifies that the source item of a drop-item action must be air. This is not strictly necessary, just a sanity
     * check.
     */
    public boolean isValid(Player source) {
        return this.sourceItem.isNull();
    }

    @Override
    public boolean onPreExecute(Player source) {
        PlayerDropItemEvent ev;
        source.getServer().getPluginManager().callEvent(ev = new PlayerDropItemEvent(source, this.targetItem));
        if (ev.isCancelled()) {
            source.stopAction();
        }
        return !ev.isCancelled();
    }

    /**
     * Drops the target item in front of the player.
     */
    public boolean execute(Player source) {
        return source.dropItem(this.targetItem);
    }

    public void onExecuteSuccess(Player source) {
    }

    public void onExecuteFail(Player source) {
    }
}
