package com.nanokylin.mc.yarecipemanager.event;

import com.nanokylin.mc.yarecipemanager.build.ShapedRecipeBuilder;
import com.nanokylin.mc.yarecipemanager.build.ShapelessRecipeBuilder;
import com.nanokylin.mc.yarecipemanager.model.RecipeBaseObject;
import com.nanokylin.mc.yarecipemanager.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    @EventHandler
    void onGuiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();
        if (title.contains("[YaRecipeManager] Edit")) {
            ItemStack[] itemStacks = event.getInventory().getContents();
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
            StringBuilder itemName = new StringBuilder(itemStack.getType().name() + "_");
            // 检查是否ItemStacks为空
            int count = 0;
            for (ItemStack stack : itemStacks) {
                if (stack == null) {
                    count = count + 1;
                }
            }
            if (count == 9) {
                player.sendMessage(ChatColor.RED + "[YaRecipeManager] 你不能让窗口空着啊 啊喂");
                return;
            }
            // 检索需要创建合成表
            String subTitle = title.substring(25);
            // 生成NamespacedKey
            for (int i = 0; ; i++) {
                itemName.append(i);
                if (!FileUtil.doesItExist("plugins/YaRecipeManager/Recipe/Shaped/" + itemName + ".recipe")) {
                    if (!FileUtil.doesItExist("plugins/YaRecipeManager/Recipe/Shapeless/" + itemName + ".recipe")) {
                        break;
                    }
                }
            }
            if (subTitle.equals("Shaped")) {
                new ShapedRecipeBuilder(event.getInventory().getContents(), itemStack, itemName.toString());
                RecipeBaseObject recipeBaseObject = new RecipeBaseObject(event.getInventory().getContents(), itemStack);
                recipeBaseObject.save("plugins/YaRecipeManager/Recipe/Shaped/" + itemName);
                player.sendMessage(ChatColor.GREEN + "[YaRecipeManager] 有序合成" + itemName + "创建成功");
            }
            if (subTitle.equals("Shapeless")) {
                new ShapelessRecipeBuilder(event.getInventory().getContents(), itemStack, itemName.toString());
                RecipeBaseObject recipeBaseObject = new RecipeBaseObject(event.getInventory().getContents(), itemStack);
                recipeBaseObject.save("plugins/YaRecipeManager/Recipe/Shapeless/" + itemName);
                player.sendMessage(ChatColor.GREEN + "[YaRecipeManager] 无序合成" + itemName + "创建成功");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.contains("[YaRecipeManager] View")) {
            event.setCancelled(true);
        }
    }
}
