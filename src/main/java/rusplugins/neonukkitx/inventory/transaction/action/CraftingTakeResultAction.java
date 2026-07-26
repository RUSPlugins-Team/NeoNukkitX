package rusplugins.neonukkitx.inventory.transaction.action;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.inventory.transaction.CraftingTransaction;
import rusplugins.neonukkitx.inventory.transaction.InventoryTransaction;
import rusplugins.neonukkitx.item.Item;

/**
 * @author CreeperFace
 */
public class CraftingTakeResultAction extends InventoryAction {

    public CraftingTakeResultAction(Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
    }

    public void onAddToTransaction(InventoryTransaction transaction) {
        if (transaction instanceof CraftingTransaction) {
            ((CraftingTransaction) transaction).setPrimaryOutput(this.getSourceItem());
        } else {
            throw new RuntimeException(getClass().getName() + " can only be added to CraftingTransactions");
        }
    }

    @Override
    public boolean isValid(Player source) {
        return true;
    }

    @Override
    public boolean execute(Player source) {
        return true;
    }

    @Override
    public void onExecuteSuccess(Player $source) {
    }

    @Override
    public void onExecuteFail(Player source) {
    }
}
