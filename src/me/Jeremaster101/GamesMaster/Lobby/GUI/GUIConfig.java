package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class GUIConfig {
    
    private static File guiConfigFile;
    private static YamlConfiguration config;
    
    public static void reloadConfig() {
        if (guiConfigFile == null) {
            guiConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "GUI.yml");
        }
        
        config = YamlConfiguration.loadConfiguration(guiConfigFile);
        
        Reader defConfigStream = new InputStreamReader(GamesMaster.getInstance().getResource("GUI.yml"),
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
        if (config == null || guiConfigFile == null) {
            return;
        }
        try {
            getConfig().save(guiConfigFile);
        } catch (IOException ex) {
            GamesMaster.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + guiConfigFile, ex);
        }
    }
    
    public static void saveDefaultConfig() {
        if (guiConfigFile == null) {
            guiConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "GUI.yml");
        }
        if (!guiConfigFile.exists()) {
            GamesMaster.getInstance().saveResource("GUI.yml", false);
            config = YamlConfiguration.loadConfiguration(guiConfigFile);
        }
    }
    
}
