package rusplugins.neonukkitx.item.enchantment.loot;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class EnchantmentLootFishing extends EnchantmentLoot {

    public EnchantmentLootFishing() {
        super(Enchantment.ID_FORTUNE_FISHING, "lootBonusFishing", Rarity.RARE, EnchantmentType.FISHING_ROD);
    }
}
