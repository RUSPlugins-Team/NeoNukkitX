package rusplugins.neonukkitx.item.enchantment.bow;

import rusplugins.neonukkitx.item.enchantment.Enchantment;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class EnchantmentBowKnockback extends EnchantmentBow {
    public EnchantmentBowKnockback() {
        super(Enchantment.ID_BOW_KNOCKBACK, "arrowKnockback", Rarity.RARE);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 25;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
