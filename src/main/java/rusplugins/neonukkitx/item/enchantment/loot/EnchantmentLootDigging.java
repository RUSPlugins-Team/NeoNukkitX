package rusplugins.neonukkitx.item.enchantment.loot;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class EnchantmentLootDigging extends EnchantmentLoot {

    public EnchantmentLootDigging() {
        super(Enchantment.ID_FORTUNE_DIGGING, "lootBonusDigger", Rarity.RARE, EnchantmentType.DIGGER);
    }
}
