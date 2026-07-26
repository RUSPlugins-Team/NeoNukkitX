package rusplugins.neonukkitx.inventory.transaction;

import rusplugins.neonukkitx.NeoNukkitX;
import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.inventory.GrindItemEvent;
import rusplugins.neonukkitx.inventory.GrindstoneInventory;
import rusplugins.neonukkitx.inventory.Inventory;
import rusplugins.neonukkitx.inventory.transaction.action.GrindstoneItemAction;
import rusplugins.neonukkitx.inventory.transaction.action.InventoryAction;
import rusplugins.neonukkitx.inventory.transaction.action.SlotChangeAction;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.network.protocol.types.NetworkInventoryAction;
import lombok.Getter;

import java.util.List;

public class GrindstoneTransaction extends InventoryTransaction {

    @Getter
    private Item equipmentItem;
    @Getter
    private Item ingredientItem;
    @Getter
    private Item outputItem;

    public GrindstoneTransaction(Player source, List<InventoryAction> actions) {
        super(source, actions);
    }

    @Override
    public void addAction(InventoryAction action) {
        if (action instanceof GrindstoneItemAction) {
            switch (((GrindstoneItemAction) action).getType()) {
                case NetworkInventoryAction.SOURCE_TYPE_ANVIL_INPUT:
                    if (this.equipmentItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for equipmentItem");
                        return;
                    }
                    this.equipmentItem = action.getTargetItem();
                    break;
                case NetworkInventoryAction.SOURCE_TYPE_ANVIL_MATERIAL:
                    if (this.ingredientItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for ingredientItem");
                        return;
                    }
                    this.ingredientItem = action.getTargetItem();
                    break;
                case NetworkInventoryAction.SOURCE_TYPE_ANVIL_RESULT:
                    if (this.outputItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for outputItem");
                        return;
                    }
                    this.outputItem = action.getSourceItem();
                    break;
            }
        }
        super.addAction(action);
    }

    @Override
    public boolean canExecute() {
        if (!super.canExecute()) {
            return false;
        }

        Inventory inventory = getSource().getWindowById(Player.GRINDSTONE_WINDOW_ID);
        if (!(inventory instanceof GrindstoneInventory)) {
            return false;
        }

        GrindstoneInventory grindstoneInventory = (GrindstoneInventory) inventory;

        if (this.outputItem == null || this.outputItem.isNull() || this.equipmentItem == null || this.equipmentItem.isNull()) {
            return false;
        }

        for (InventoryAction action : actions) {
            if (action instanceof SlotChangeAction) {
                SlotChangeAction slotChangeAction = (SlotChangeAction) action;
                if (!(slotChangeAction.getInventory() instanceof GrindstoneInventory)) {
                    Item item = slotChangeAction.getTargetItemUnsafe();
                    if (item != null && !item.isNull() && !this.outputItem.equals(item)) {
                        this.invalid = true;
                        if (NeoNukkitX.DEBUG > 1) {
                            source.getServer().getLogger().debug("Illegal output " + item);
                        }
                        return false;
                    }
                }
            }
        }

        Item ingredientOptional = ingredientItem != null ? ingredientItem : Item.get(Item.AIR);

        return ingredientOptional.equals(grindstoneInventory.getIngredient(), true, true)
                && equipmentItem.equals(grindstoneInventory.getEquipment(), true, true)
                && outputItem.equals(grindstoneInventory.getResult(), true, true);
    }

    @Override
    public boolean execute() {
        if (this.hasExecuted() || !this.canExecute() || this.invalid) {
            this.source.removeAllWindows(false);
            this.sendInventories();
            return false;
        }

        GrindstoneInventory inventory = (GrindstoneInventory) getSource().getWindowById(Player.GRINDSTONE_WINDOW_ID);
        GrindItemEvent event = new GrindItemEvent(inventory, this.equipmentItem, this.outputItem, this.ingredientItem, this.source);
        this.source.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            this.sendInventories();
            source.setNeedSendInventory(true);
            return true;
        }

        for (InventoryAction action : this.actions) {
            if (action.execute(this.source)) {
                action.onExecuteSuccess(this.source);
            } else {
                action.onExecuteFail(this.source);
            }
        }

        this.hasExecuted = true;
        return true;
    }

    public static boolean isIn(List<InventoryAction> actions) {
        for (InventoryAction action : actions) {
            if (action instanceof GrindstoneItemAction) return true;
        }
        return false;
    }

    @Override
    public boolean checkForItemPart(List<InventoryAction> actions) {
        return isIn(actions);
    }
}
