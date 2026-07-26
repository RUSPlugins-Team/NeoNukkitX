package rusplugins.neonukkitx.inventory.transaction;

import rusplugins.neonukkitx.NeoNukkitX;
import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.inventory.EnchantItemEvent;
import rusplugins.neonukkitx.inventory.EnchantInventory;
import rusplugins.neonukkitx.inventory.Inventory;
import rusplugins.neonukkitx.inventory.transaction.action.EnchantingAction;
import rusplugins.neonukkitx.inventory.transaction.action.InventoryAction;
import rusplugins.neonukkitx.inventory.transaction.action.SlotChangeAction;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.item.ItemArmor;
import rusplugins.neonukkitx.item.ItemBookEnchanted;
import rusplugins.neonukkitx.item.ItemTool;
import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;
import rusplugins.neonukkitx.network.protocol.types.NetworkInventoryAction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnchantTransaction extends InventoryTransaction {

    private Item inputItem;
    private Item outputItem;
    protected Item materialItem;

    private int cost = -1;

    public EnchantTransaction(Player source, List<InventoryAction> actions) {
        super(source, actions);
    }

    @Override
    public boolean canExecute() {
        if (!super.canExecute()) {
            return false;
        }

        Inventory inv = getSource().getWindowById(Player.ENCHANT_WINDOW_ID);
        if (!(inv instanceof EnchantInventory)) {
            return false;
        }

        EnchantInventory eInv = (EnchantInventory) inv;
        if (!getSource().isCreative()) {
            if (this.cost < 1) {
                return false;
            } else {
                Item reagent = eInv.getReagentSlot();
                if (reagent.count < this.cost || !reagent.equals(Item.get(Item.DYE, 4), true, false)) {
                    return false;
                }
            }
        }

        if (this.outputItem == null || this.outputItem.isNull() || this.inputItem == null || this.inputItem.isNull()) {
            return false;
        }

        for (InventoryAction action : actions) {
            if (action instanceof SlotChangeAction) {
                SlotChangeAction slotChangeAction = (SlotChangeAction) action;
                if (!(slotChangeAction.getInventory() instanceof EnchantInventory)) {
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

        return this.inputItem.equals(eInv.getInputSlot(), true, true)
                && (this.inputItem.getId() == this.outputItem.getId() || (this.inputItem.getId() == Item.BOOK && this.outputItem.getId() == Item.ENCHANTED_BOOK))
                && (this.inputItem.getCount() == this.outputItem.getCount() || (this.outputItem.getId() == Item.ENCHANTED_BOOK && this.outputItem.getCount() == 1)
                && validateNBT());
    }

    private boolean validateNBT() {
        if (!(outputItem instanceof ItemTool || outputItem instanceof ItemArmor || outputItem instanceof ItemBookEnchanted)) {
            source.getServer().getLogger().debug("Non-enchantable item");
            return false;
        }

        for (Enchantment e : outputItem.getEnchantments()) {
            if (e.isTreasure()) {
                source.getServer().getLogger().debug("Illegal treasure enchantment");
                return false;
            }
        }

        CompoundTag a = this.inputItem.getNamedTag();
        a = a == null ? new CompoundTag() : a.clone().remove("ench");
        CompoundTag b = this.outputItem.getNamedTag();
        b = b == null ? new CompoundTag() : b.clone().remove("ench");
        if (!a.equals(b)) {
            if (NeoNukkitX.DEBUG > 1) {
                source.getServer().getLogger().debug("NBT check failed: input=" + a + ", output=" + b);
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean execute() {
        // This will validate the enchant conditions
        if (this.hasExecuted() || !this.canExecute() || this.invalid) {
            this.source.removeAllWindows(false);
            this.sendInventories();
            return false;
        }

        EnchantInventory inv = (EnchantInventory) getSource().getWindowById(Player.ENCHANT_WINDOW_ID);
        EnchantItemEvent ev = new EnchantItemEvent(inv, inputItem, outputItem, cost, source);
        source.getServer().getPluginManager().callEvent(ev);
        if (ev.isCancelled()) {
            this.sendInventories();
            source.setNeedSendInventory(true);
            // Cancelled by plugin, means handled OK
            return true;
        }

        // This will process all the slot changes
        for (InventoryAction a : this.actions) {
            if (a.execute(source)) {
                a.onExecuteSuccess(source);
            } else {
                a.onExecuteFail(source);
            }
        }

        if (!ev.getNewItem().equals(this.outputItem, true, true)) {
            // Plugin changed item, so the previous slot change is going to be invalid
            // Send the replaced item to the enchant inventory manually
            inv.setItem(0, ev.getNewItem(), true);
        }

        if (!source.isCreative()) {
            source.setExperience(source.getExperience(), source.getExperienceLevel() - ev.getXpCost());
        }

        this.hasExecuted = true;
        return true;
    }

    @Override
    public void addAction(InventoryAction action) {
        if (action instanceof EnchantingAction) {
            switch (((EnchantingAction) action).getType()) {
                case NetworkInventoryAction.SOURCE_TYPE_ENCHANT_INPUT:
                    if (this.inputItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for inputItem");
                        return;
                    }
                    this.inputItem = action.getTargetItem(); // Input sent as newItem
                    break;
                case NetworkInventoryAction.SOURCE_TYPE_ENCHANT_OUTPUT:
                    if (this.outputItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for outputItem");
                        return;
                    }
                    this.outputItem = action.getSourceItem(); // Output sent as oldItem
                    break;
                case NetworkInventoryAction.SOURCE_TYPE_ENCHANT_MATERIAL:
                    if (this.materialItem != null) {
                        this.invalid = true;
                        source.getServer().getLogger().debug("Duplicate addAction for materialItem");
                        return;
                    }
                    this.materialItem = action.getTargetItem();

                    if (action.getTargetItemUnsafe().getId() == Item.AIR) {
                        this.cost = action.getSourceItemUnsafe().count;
                    } else {
                        this.cost = action.getSourceItemUnsafe().count - action.getTargetItemUnsafe().count;
                    }
                    break;
            }
        }
        super.addAction(action);
    }

    @Override
    public boolean checkForItemPart(List<InventoryAction> actions) {
        for (InventoryAction action : actions) {
            if (action instanceof EnchantingAction) return true;
        }
        return false;
    }
}
