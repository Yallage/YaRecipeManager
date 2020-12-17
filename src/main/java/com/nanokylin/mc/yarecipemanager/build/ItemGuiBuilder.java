package com.nanokylin.mc.yarecipemanager.build;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.stream.IntStream;

public class ItemGuiBuilder implements InventoryHolder {
    public ItemGuiBuilder(Player player, ItemStack[] itemStacks) {
        this.inv = Bukkit.createInventory(this, InventoryType.DISPENSER, ChatColor.BOLD + "[YaRecipeManager] View");
        IntStream.range(0, itemStacks.length).forEach(i -> {
            if (itemStacks[i] == null) {
                // 设置空的物品
                ItemStack itemStackTemp = new ItemStack(Material.APPLE);
                // itemMetaTemp用于设置DisplayName
                ItemMeta itemMetaTemp = itemStackTemp.getItemMeta();
                // 其实这个requireNonNull是多余的 已经做过null判断了
                Objects.requireNonNull(itemMetaTemp).setDisplayName(ChatColor.RED + "ZNILNVz3Wz [NULL ITEM] Number" + i);
                itemStackTemp.setItemMeta(itemMetaTemp);
                inv.addItem(itemStackTemp);
                return;
            }
            // itemMetaTemp用于设置DisplayName
            ItemMeta itemMetaTemp = itemStacks[i].getItemMeta();
            // 其实这个requireNonNull是多余的 已经做过null判断了
            String oldName = (Objects.requireNonNull(itemMetaTemp)).getDisplayName();
            Objects.requireNonNull(itemMetaTemp).setDisplayName(ChatColor.YELLOW + "Item: " + i + " OLD_NAME: " + oldName);
            itemStacks[i].setItemMeta(itemMetaTemp);
            inv.addItem(itemStacks[i]);
        });
        // 清理被标记[NULL ITEM]的物品
        for (int i = 0; i < inv.getSize(); i++) {
            if (Objects.requireNonNull(Objects.requireNonNull(inv.getItem(i)).getItemMeta()).getDisplayName().contains(ChatColor.RED + "ZNILNVz3Wz [NULL ITEM] Number")) {
                inv.remove(Objects.requireNonNull(inv.getItem(i)));
            }
        }
        player.openInventory(inv);
    }

    // 窗口
    Inventory inv;

    // 实现一把 InventoryHolder
    @Override
    public Inventory getInventory() {
        return inv;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }
}
