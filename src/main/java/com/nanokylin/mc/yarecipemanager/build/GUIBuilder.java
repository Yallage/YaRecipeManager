package com.nanokylin.mc.yarecipemanager.build;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIBuilder implements InventoryHolder {
    // 窗口
    Inventory inv;

    // QWQ
    public GUIBuilder(Player player, String type) {
        this.inv = Bukkit.createInventory(this, InventoryType.DISPENSER, ChatColor.BOLD + "[YaRecipeManager] Edit " + type);
        player.openInventory(inv);
    }

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
