package rusplugins.neonukkitx.inventory.transaction.data;

import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.math.BlockFace;
import rusplugins.neonukkitx.math.BlockVector3;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.math.Vector3f;
import lombok.ToString;

/**
 * @author CreeperFace
 */
@ToString
public class UseItemData implements TransactionData {

    public int actionType;
    public int triggerType;
    public BlockVector3 blockPos;
    public BlockFace face;
    public int hotbarSlot;
    public Item itemInHand;
    public Vector3 playerPos;
    public Vector3f clickPos;
    public int blockRuntimeId;
    public int clientInteractPrediction;
    public int clientCooldownState;
}
