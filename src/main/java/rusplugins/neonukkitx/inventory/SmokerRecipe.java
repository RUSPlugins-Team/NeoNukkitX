package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.item.Item;

public class SmokerRecipe extends FurnaceRecipe {

    public SmokerRecipe(String recipeId, Item result, Item ingredient) {
        super(recipeId, result, ingredient);
    }

    @Override
    public void registerToCraftingManager(CraftingManager manager) {
        manager.registerSmokerRecipe(this);
    }
}
