package rusplugins.neonukkitx.item.enchantment.trident;

import rusplugins.neonukkitx.entity.Entity;
import rusplugins.neonukkitx.item.enchantment.Enchantment;

public class EnchantmentTridentImpaling extends EnchantmentTrident {

    public EnchantmentTridentImpaling() {
        super(Enchantment.ID_TRIDENT_IMPALING, "tridentImpaling", Rarity.RARE);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 8 * level - 7;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public double getDamageBonus(Entity entity) {
        if (entity.isInsideOfWater() || (entity.getLevel().isRaining() && entity.canSeeSky())) {
            return 2.5 * getLevel();
        }

        return 0;
    }
}
