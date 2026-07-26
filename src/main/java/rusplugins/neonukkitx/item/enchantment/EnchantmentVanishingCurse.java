package rusplugins.neonukkitx.item.enchantment;

import rusplugins.neonukkitx.item.Item;

public class EnchantmentVanishingCurse extends Enchantment {

    protected EnchantmentVanishingCurse() {
        super(ID_VANISHING_CURSE, "curse.vanishing", Rarity.VERY_RARE, EnchantmentType.BREAKABLE);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    public boolean canEnchant(Item item) {
        return item.getId() == Item.SKULL || item.getId() == Item.COMPASS || super.canEnchant(item);
    }
}
