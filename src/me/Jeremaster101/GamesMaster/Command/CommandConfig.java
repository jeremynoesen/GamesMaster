package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class CommandConfig {

    private static File commandConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (commandConfigFile == null) {
            commandConfigFile = new File(GamesMaster.plugin.getDataFolder(), "commands.yml");
        }

        config = YamlConfiguration.loadConfiguration(commandConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("commands.yml"),
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
        if (config == null || commandConfigFile == null) {
            return;
        }
        try {
            getConfig().save(commandConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + commandConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (commandConfigFile == null) {
            commandConfigFile = new File(GamesMaster.plugin.getDataFolder(), "commands.yml");
        }
        if (!commandConfigFile.exists()) {
            GamesMaster.plugin.saveResource("commands.yml", false);
            config = YamlConfiguration.loadConfiguration(commandConfigFile);
        }
    }

}
