package com.nanokylin.mc.yarecipemanager;

import com.nanokylin.mc.yarecipemanager.build.ShapedRecipeBuilder;
import com.nanokylin.mc.yarecipemanager.command.MainCommander;
import com.nanokylin.mc.yarecipemanager.common.Config;
import com.nanokylin.mc.yarecipemanager.event.GuiListener;
import com.nanokylin.mc.yarecipemanager.model.RecipeBaseObject;
import com.nanokylin.mc.yarecipemanager.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class YaRecipeManager extends JavaPlugin {
    public static YaRecipeManager instance;

    public YaRecipeManager() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[YaRecipeManager] Running...");
        // 注册指令表
        Objects.requireNonNull(Bukkit.getPluginCommand("yrm")).setExecutor(new MainCommander());
        // 注册监听器
        Bukkit.getServer().getPluginManager().registerEvents(new GuiListener(), this);
        // 加载配置文件
        Config.verifyLanguageExist("config.yml", "plugins/YaRecipeManager/config.yml", this);
        Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[YaRecipeManager] Config Loaded");
        // 判断是否drop
        if (this.getConfig().getBoolean("UsingClearRecipes")) {
            // Drops合成表
            Bukkit.clearRecipes();
            Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[YaRecipeManager] Drops All Recipe");
        }
        // 加载本插件合成表
        loadRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * 扫描获取文件列表并放入RBO种加载
     */
    public void loadRecipe() {
        String[] recipes = FileUtil.getFileList("plugins/YaRecipeManager/Recipe/Shaped/");
        if (recipes != null) {
            for (String recipe : recipes) {
                RecipeBaseObject recipeBaseObject = new RecipeBaseObject();
                recipeBaseObject.load("plugins/YaRecipeManager/Recipe/Shaped/" + recipe);
                new ShapedRecipeBuilder(recipeBaseObject.getItemStacks(), recipeBaseObject.getTargetItem(), recipe.substring(0, recipe.length() - 7));
                Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[YaRecipeManager][INFO] Load Shaped Recipe " + recipe);
            }
        }
        recipes = FileUtil.getFileList("plugins/YaRecipeManager/Recipe/Shapeless/");
        if (recipes != null) {
            for (String recipe : recipes) {
                RecipeBaseObject recipeBaseObject = new RecipeBaseObject();
                recipeBaseObject.load("plugins/YaRecipeManager/Recipe/Shapeless/" + recipe);
                new ShapedRecipeBuilder(recipeBaseObject.getItemStacks(), recipeBaseObject.getTargetItem(), recipe.substring(0, recipe.length() - 7));
                Bukkit.getServer().getLogger().info(ChatColor.GREEN + "[YaRecipeManager][INFO] Load Shapeless Recipe " + recipe);
            }
        }
    }
}
