package rusplugins.neonukkitx.item.enchantment.mace;

import rusplugins.neonukkitx.item.enchantment.Enchantment;

public class EnchantmentMaceDensity extends EnchantmentMace {

    public EnchantmentMaceDensity() {
        super(EnchantmentMace.ID_DENSITY, "density", Rarity.UNCOMMON);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) &&
                enchantment.id != EnchantmentMace.ID_BREACH &&
                enchantment.id != EnchantmentMace.ID_DAMAGE_SMITE &&
                enchantment.id != EnchantmentMace.ID_DAMAGE_ARTHROPODS;
    }
}
