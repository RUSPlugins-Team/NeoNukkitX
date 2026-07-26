package rusplugins.neonukkitx.item;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.player.PlayerItemConsumeEvent;
import rusplugins.neonukkitx.item.food.Food;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.LevelSoundEventPacket;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class ItemEdible extends Item {

    public ItemEdible(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    public ItemEdible(int id) {
        super(id);
    }

    public ItemEdible(int id, Integer meta) {
        super(id, meta);
    }

    public ItemEdible(int id, Integer meta, int count) {
        super(id, meta, count);
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return this.canAlwaysEat() || player.canEat(true);
    }

    /**
     * How many ticks player must eat the food before it can be consumed
     */
    public int getUseTicks() {
        return 30;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        if (ticksUsed < this.getUseTicks()) {
            player.getServer().getLogger().debug(player.getName() + ": food ticksUsed=" + ticksUsed);
            return false;
        }
        PlayerItemConsumeEvent consumeEvent = new PlayerItemConsumeEvent(player, this);

        player.getServer().getPluginManager().callEvent(consumeEvent);
        if (consumeEvent.isCancelled()) {
            return false; // Inventory#sendContents is called in Player
        }

        Food food = Food.getByRelative(this);
        if (food != null && food.eatenBy(player)) {
            player.getLevel().addLevelSoundEvent(player, LevelSoundEventPacket.SOUND_BURP);
            if (!player.isCreative() && !player.isSpectator()) {
                --this.count;
                player.getInventory().setItemInHand(this);
            }
        }
        return true;
    }

    /**
     * Whether food is a drink (mainly used for custom food item sounds)
     */
    public boolean isDrink() {
        return false;
    }

    /**
     * Whether food can be eaten even if food bar is full
     */
    public boolean canAlwaysEat() {
        return false;
    }
}
