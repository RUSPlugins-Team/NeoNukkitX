package rusplugins.neonukkitx.inventory.transaction.action;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.inventory.transaction.CraftingTransaction;
import rusplugins.neonukkitx.inventory.transaction.InventoryTransaction;
import rusplugins.neonukkitx.item.Item;

/**
 * @author CreeperFace
 */
public class CraftingTransferMaterialAction extends InventoryAction {

    public CraftingTransferMaterialAction(Item sourceItem, Item targetItem, int slot) {
        super(sourceItem, targetItem);
    }

    @Override
    public void onAddToTransaction(InventoryTransaction transaction) {
        if (transaction instanceof CraftingTransaction) {
            if (this.sourceItem.isNull()) {
                ((CraftingTransaction) transaction).setInput(this.targetItem);
            } else if (this.targetItem.isNull()) {
                ((CraftingTransaction) transaction).setExtraOutput(this.sourceItem);
            } else {
                throw new RuntimeException("Invalid " + getClass().getName() + ", either source or target item must be air, got source: " + this.sourceItem + ", target: " + this.targetItem);
            }
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
