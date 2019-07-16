package me.Jeremaster101.GamesMaster.Lobby;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class LobbyConfig {

    private static File lobbyConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (lobbyConfigFile == null) {
            lobbyConfigFile = new File(GamesMaster.plugin.getDataFolder(), "lobby.yml");
        }

        config = YamlConfiguration.loadConfiguration(lobbyConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("lobby.yml"),
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
        if (config == null || lobbyConfigFile == null) {
            return;
        }
        try {
            getConfig().save(lobbyConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + lobbyConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (lobbyConfigFile == null) {
            lobbyConfigFile = new File(GamesMaster.plugin.getDataFolder(), "lobby.yml");
        }
        if (!lobbyConfigFile.exists()) {
            GamesMaster.plugin.saveResource("lobby.yml", false);
            config = YamlConfiguration.loadConfiguration(lobbyConfigFile);
        }
    }

}
