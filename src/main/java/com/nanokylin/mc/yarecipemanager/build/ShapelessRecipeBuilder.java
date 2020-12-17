package com.nanokylin.mc.yarecipemanager.build;

import com.nanokylin.mc.yarecipemanager.YaRecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class ShapelessRecipeBuilder {

    public ShapelessRecipeBuilder(ItemStack[] itemStacks, ItemStack targetItem,String namespacedKey) {
        NamespacedKey targetKey = new NamespacedKey(YaRecipeManager.instance, namespacedKey);
        ShapelessRecipe targetRecipe = new ShapelessRecipe(targetKey, targetItem);
        for (int i = 0; i < 9; i++) {
            if (itemStacks[i] != null) {
                targetRecipe.addIngredient(itemStacks[i].getType());
            }
        }
        Bukkit.addRecipe(targetRecipe);
    }
}
