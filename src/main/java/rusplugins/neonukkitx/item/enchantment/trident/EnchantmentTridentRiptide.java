package rusplugins.neonukkitx.item.enchantment.trident;

import rusplugins.neonukkitx.item.enchantment.Enchantment;

public class EnchantmentTridentRiptide extends EnchantmentTrident {

    public EnchantmentTridentRiptide() {
        super(Enchantment.ID_TRIDENT_RIPTIDE, "tridentRiptide", Rarity.RARE);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 7 * level + 10;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
