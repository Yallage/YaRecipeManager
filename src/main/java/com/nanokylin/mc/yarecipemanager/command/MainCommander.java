package com.nanokylin.mc.yarecipemanager.command;

import com.nanokylin.mc.yarecipemanager.YaRecipeManager;
import com.nanokylin.mc.yarecipemanager.build.GUIBuilder;
import com.nanokylin.mc.yarecipemanager.build.ItemGuiBuilder;
import com.nanokylin.mc.yarecipemanager.model.RecipeBaseObject;
import com.nanokylin.mc.yarecipemanager.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainCommander implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // 记得加权限判断 + 玩家控制台判定
        if (commandSender.hasPermission("yarecipemanager.admin")) {
            if (strings.length == 0) {
                commandSender.sendMessage(ChatColor.YELLOW + "指令格式错误 /yrm help - 帮助");
                return true;
            }
            switch (strings[0].toLowerCase()) {
                case "reload": {
                    commandSender.sendMessage("Reloading..");
                    YaRecipeManager.instance.reloadConfig();
                    commandSender.sendMessage("YaRecipeManager fully reloaded.");
                }
                case "recipe": {
                    if (strings.length >= 2) {
                        ItemStack stack = ((Player) commandSender).getInventory().getItemInMainHand();
                        if (stack.getType() == Material.AIR) {
                            commandSender.sendMessage("你需要手持一个物品才能使用这个命令.");
                            return true;
                        } else if (strings[1].contains("shaped")) {
                            new GUIBuilder((Player) commandSender, "Shaped");
                        } else if (strings[1].contains("shapeless")) {
                            new GUIBuilder((Player) commandSender, "Shapeless");
                        }
                        return true;
                    }
                    break;
                }
                case "help": {
                    commandSender.sendMessage(ChatColor.YELLOW + "/yrm recipe shaped - 有序合成");
                    commandSender.sendMessage(ChatColor.YELLOW + "/yrm recipe shapeless - 无序合成");
                    commandSender.sendMessage(ChatColor.YELLOW + "/yrm reload - 重载插件");
                    commandSender.sendMessage(ChatColor.YELLOW + "/yrm view fileName - 通过文件名查看合成表");
                    commandSender.sendMessage(ChatColor.YELLOW + "/yrm help - 帮助");

                }
                case "view": {
                    if (strings.length >= 2) {
                        RecipeBaseObject recipeBaseObject = new RecipeBaseObject();
                        String shapedPath = "plugins/YaRecipeManager/Recipe/Shaped/" + strings[1] + ".recipe";
                        String shapelessPath = "plugins/YaRecipeManager/Recipe/Shapeless/" + strings[1] + ".recipe";
                        if (FileUtil.doesItExist(shapedPath)) {
                            recipeBaseObject.load(shapedPath);
                            new ItemGuiBuilder((Player) commandSender, recipeBaseObject.getItemStacks());
                        } else if (FileUtil.doesItExist(shapelessPath)) {
                            recipeBaseObject.load(shapelessPath);
                            new ItemGuiBuilder((Player) commandSender, recipeBaseObject.getItemStacks());
                        } else {
                            commandSender.sendMessage(ChatColor.YELLOW + "没有找到合成配方");
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.YELLOW + "指令格式错误 /yrm help - 帮助");
                    }
                }
            }
        }
        return false;
    }
}
