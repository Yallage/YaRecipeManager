package com.nanokylin.mc.yarecipemanager.common;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Config {
    public static void verifyLanguageExist(String resource, String config, JavaPlugin plugin) {
        File temp = new File(config);
        if (!temp.exists()) {
            plugin.saveResource(resource, true);
        }
    }
}
