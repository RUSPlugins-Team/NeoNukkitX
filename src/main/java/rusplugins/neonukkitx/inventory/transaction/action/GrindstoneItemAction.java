package rusplugins.neonukkitx.inventory.transaction.action;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.inventory.GrindstoneInventory;
import rusplugins.neonukkitx.item.Item;
import lombok.Getter;

public class GrindstoneItemAction extends InventoryAction {

    @Getter
    private final int type;

    public GrindstoneItemAction(Item sourceItem, Item targetItem, int type) {
        super(sourceItem, targetItem);
        this.type = type;
    }

    @Override
    public boolean isValid(Player source) {
        return source.getWindowById(Player.GRINDSTONE_WINDOW_ID) instanceof GrindstoneInventory;
    }

    @Override
    public boolean execute(Player source) {
        return true;
    }

    @Override
    public void onExecuteSuccess(Player source) {
    }

    @Override
    public void onExecuteFail(Player source) {
    }
}
