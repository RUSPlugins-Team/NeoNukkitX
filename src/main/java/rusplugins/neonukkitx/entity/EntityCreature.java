package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.item.Item;
import rusplugins.neonukkitx.level.format.FullChunk;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.nbt.tag.CompoundTag;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class EntityCreature extends EntityLiving {

    public EntityCreature(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (item.getId() == Item.NAME_TAG && !player.isAdventure()) {
            return applyNameTag(player, item);
        }

        return false;
    }

    /**
     * Called when player interacts the entity with a name tag item
     * @param player player
     * @param nameTag name tag item
     * @return true to reduce name tag item count
     */
    protected boolean applyNameTag(Player player, Item nameTag) {
        return false; // Override in BaseEntity
    }
}
