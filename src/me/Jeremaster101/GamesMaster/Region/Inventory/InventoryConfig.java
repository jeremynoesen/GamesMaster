package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class InventoryConfig {

    private static File inventoryConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (inventoryConfigFile == null) {
            inventoryConfigFile = new File(GamesMaster.plugin.getDataFolder(), "inventories.yml");
            config = YamlConfiguration.loadConfiguration(inventoryConfigFile);
        }
        try {
            config.load(inventoryConfigFile);
        } catch (IOException | InvalidConfigurationException ignored) {
        }

        Reader defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("inventories.yml"),
                StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public static YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public static void saveConfig() {
        if (config == null || inventoryConfigFile == null) {
            return;
        }
        try {
            getConfig().save(inventoryConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + inventoryConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (inventoryConfigFile == null) {
            inventoryConfigFile = new File(GamesMaster.plugin.getDataFolder(), "inventories.yml");
        }
        if (!inventoryConfigFile.exists()) {
            GamesMaster.plugin.saveResource("inventories.yml", false);
        }
    }
}
