package me.Jeremaster101.GamesMaster.Region;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class RegionConfig {

    private static File regionConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (regionConfigFile == null) {
            regionConfigFile = new File(GamesMaster.plugin.getDataFolder(), "regions.yml");
        }

        config = YamlConfiguration.loadConfiguration(regionConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("regions.yml"),
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
        if (config == null || regionConfigFile == null) {
            return;
        }
        try {
            getConfig().save(regionConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + regionConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (regionConfigFile == null) {
            regionConfigFile = new File(GamesMaster.plugin.getDataFolder(), "regions.yml");
        }
        if (!regionConfigFile.exists()) {
            GamesMaster.plugin.saveResource("regions.yml", false);
            config = YamlConfiguration.loadConfiguration(regionConfigFile);
        }
    }

}
