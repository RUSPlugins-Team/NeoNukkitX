package rusplugins.neonukkitx.inventory.transaction.data;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.Vector3;
import lombok.ToString;

/**
 * @author CreeperFace
 */
@ToString
public class UseItemOnEntityData implements TransactionData {

    public long entityRuntimeId;
    public int actionType;
    public int hotbarSlot;
    public Item itemInHand;
    public Vector3 playerPos;
    public Vector3 clickPos;
}
