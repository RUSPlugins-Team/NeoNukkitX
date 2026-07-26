package rusplugins.neonukkitx.item.enchantment.bow;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class EnchantmentBow extends Enchantment {

    protected EnchantmentBow(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.BOW);
    }
}
