package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.entity.EntityHuman;
import rusplugins.neonukkitx.entity.EntityHumanType;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.network.protocol.InventoryContentPacket;
import rusplugins.neonukkitx.network.protocol.InventorySlotPacket;
import rusplugins.neonukkitx.network.protocol.MobEquipmentPacket;
import rusplugins.neonukkitx.network.protocol.types.ContainerIds;

public class PlayerOffhandInventory extends BaseInventory {

    public PlayerOffhandInventory(EntityHumanType holder) {
        super(holder, InventoryType.OFFHAND);
    }

    @Override
    public void setSize(int size) {
        throw new UnsupportedOperationException("Offhand can only carry one item at a time");
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        EntityHuman holder = this.getHolder();
        if (holder instanceof Player && !((Player) holder).spawned) {
            return;
        }

        this.sendContents(this.getViewers());
        this.sendContents(holder.getViewers().values());
    }

    @Override
    public void sendContents(Player... players) {
        Item item = this.getItem(0);

        for (Player player : players) {
            if (player == this.getHolder()) {
                InventoryContentPacket pk = new InventoryContentPacket(); // content vs slot
                pk.inventoryId = ContainerIds.OFFHAND;
                pk.slots = new Item[]{item};
                player.dataPacket(pk);
            } else {
                MobEquipmentPacket pk = this.createMobEquipmentPacket(item);
                player.dataPacket(pk);
            }
        }
    }

    @Override
    public void sendSlot(int index, Player... players) {
        Item item = this.getItem(0);

        for (Player player : players) {
            if (player == this.getHolder()) {
                InventorySlotPacket pk = new InventorySlotPacket(); // slot vs content
                pk.inventoryId = ContainerIds.OFFHAND;
                pk.item = item;
                player.dataPacket(pk);
            } else {
                MobEquipmentPacket pk = this.createMobEquipmentPacket(item);
                player.dataPacket(pk);
            }
        }
    }

    private MobEquipmentPacket createMobEquipmentPacket(Item item) {
        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getHolder().getId();
        pk.item = item;
        pk.inventorySlot = 1;
        pk.windowId = ContainerIds.OFFHAND;
        return pk;
    }

    @Override
    public EntityHuman getHolder() {
        return (EntityHuman) super.getHolder();
    }

    @Override
    public boolean allowedToAdd(Item item) {
        return item.allowOffhand();
    }
}
