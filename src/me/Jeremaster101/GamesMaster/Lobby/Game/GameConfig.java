package me.Jeremaster101.GamesMaster.Lobby.Game;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class GameConfig {

    private static File gameConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (gameConfigFile == null) {
            gameConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "games.yml");
        }

        config = YamlConfiguration.loadConfiguration(gameConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.getInstance().getResource("games.yml"),
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
        if (config == null || gameConfigFile == null) {
            return;
        }
        try {
            getConfig().save(gameConfigFile);
        } catch (IOException ex) {
            GamesMaster.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + gameConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (gameConfigFile == null) {
            gameConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "games.yml");
        }
        if (!gameConfigFile.exists()) {
            GamesMaster.getInstance().saveResource("games.yml", false);
            config = YamlConfiguration.loadConfiguration(gameConfigFile);
        }
    }

}
