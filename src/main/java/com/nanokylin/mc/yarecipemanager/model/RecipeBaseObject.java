package com.nanokylin.mc.yarecipemanager.model;

import com.nanokylin.mc.yarecipemanager.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class RecipeBaseObject {
    ItemStack[] itemStacks;
    ItemStack targetItem;

    public RecipeBaseObject() {

    }

    public RecipeBaseObject(ItemStack[] itemStacks, ItemStack targetItem) {
        this.itemStacks = itemStacks;
        this.targetItem = targetItem;
    }

    public void save(String namespacedKey) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("item",targetItem);
        map.put("itemStacks",itemStacks);
        if (FileUtil.doesItExist(namespacedKey + ".recipe")){
            Bukkit.getServer().getLogger().info("recipe文件已存在");
        }
        else{
            FileUtil.createFile(namespacedKey + ".recipe");
            File file = new File(namespacedKey + ".recipe");
            try {
                BukkitObjectOutputStream oos = new BukkitObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(map);
                oos.close();
            } catch (Exception e) {
                System.out.println("[YaRecipeManager] Error qwq " + e);
            }
        }

    }

    public void load(String path) {
        if (FileUtil.doesItExist(path)) {
            try {
                File file = new File(path);
                HashMap<String,Object> map = new HashMap<>();
                BukkitObjectInputStream ois = new BukkitObjectInputStream(new FileInputStream(file));
                Object readMap = ois.readObject();
                if (readMap instanceof HashMap) {
                    map.putAll((HashMap) readMap);
                    this.targetItem = (ItemStack)map.get("item");
                    this.itemStacks = (ItemStack[])map.get("itemStacks");
                }
                ois.close();
            } catch (Exception e) {
                System.out.println("[YaRecipeManager] Error qwq " + e);
            }
        }
    }

    public ItemStack getTargetItem() {
        return targetItem;
    }

    public ItemStack[] getItemStacks() {
        return itemStacks;
    }

    public void setItemStacks(ItemStack[] itemStacks) {
        this.itemStacks = itemStacks;
    }

    public void setTargetItem(ItemStack targetItem) {
        this.targetItem = targetItem;
    }
}
