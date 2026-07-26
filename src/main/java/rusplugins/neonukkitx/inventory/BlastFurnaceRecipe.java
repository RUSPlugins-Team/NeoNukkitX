package rusplugins.neonukkitx.inventory;

import rusplugins.neonukkitx.item.Item;

public class BlastFurnaceRecipe extends FurnaceRecipe {

    public BlastFurnaceRecipe(String recipeId, Item result, Item ingredient) {
        super(recipeId, result, ingredient);
    }

    @Override
    public void registerToCraftingManager(CraftingManager manager) {
        manager.registerBlastFurnaceRecipe(this);
    }
}
