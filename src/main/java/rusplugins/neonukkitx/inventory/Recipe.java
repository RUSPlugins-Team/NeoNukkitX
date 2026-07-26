package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.item.Item;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public interface Recipe {

    Item getResult();

    void registerToCraftingManager(CraftingManager manager);

    RecipeType getType();
}
