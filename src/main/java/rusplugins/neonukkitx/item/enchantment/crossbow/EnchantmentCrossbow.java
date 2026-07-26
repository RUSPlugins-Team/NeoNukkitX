package rusplugins.neonukkitx.item.enchantment.crossbow;

import rusplugins.neonukkitx.item.enchantment.Enchantment;
import rusplugins.neonukkitx.item.enchantment.EnchantmentType;

public abstract class EnchantmentCrossbow extends Enchantment {

    protected EnchantmentCrossbow(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.CROSSBOW);
    }
}
