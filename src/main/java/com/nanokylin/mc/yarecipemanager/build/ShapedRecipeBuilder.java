package com.nanokylin.mc.yarecipemanager.build;

import com.nanokylin.mc.yarecipemanager.YaRecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ShapedRecipeBuilder {
    public char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

    public ShapedRecipeBuilder(ItemStack[] itemStacks, ItemStack targetItem, String namespacedKey) {
        NamespacedKey targetKey = new NamespacedKey(YaRecipeManager.instance, namespacedKey);
        ShapedRecipe targetRecipe = new ShapedRecipe(targetKey, targetItem);
        targetRecipe.shape("ABC", "DEF", "GHI");
        for (int i = 0; i < 9; i++) {
            if (itemStacks[i] != null) {
                targetRecipe.setIngredient(alphabet[i], itemStacks[i].getType());
            }
        }
        Bukkit.addRecipe(targetRecipe);
    }
}
