package rusplugins.neonukkitx.item.enchantment.loot;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class EnchantmentLootWeapon extends EnchantmentLoot {

    public EnchantmentLootWeapon() {
        super(Enchantment.ID_LOOTING, "lootBonus", Rarity.RARE, EnchantmentType.SWORD);
    }
}
