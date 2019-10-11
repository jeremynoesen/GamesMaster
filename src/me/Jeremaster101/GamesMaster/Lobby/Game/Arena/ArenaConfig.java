package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class ArenaConfig {

    private static File arenaConfigFile;
    private static YamlConfiguration config;

    public static void reloadConfig() {
        if (arenaConfigFile == null) {
            arenaConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "arenas.yml");
        }

        config = YamlConfiguration.loadConfiguration(arenaConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.getInstance().getResource("arenas.yml"),
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
        if (config == null || arenaConfigFile == null) {
            return;
        }
        try {
            getConfig().save(arenaConfigFile);
        } catch (IOException ex) {
            GamesMaster.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + arenaConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (arenaConfigFile == null) {
            arenaConfigFile = new File(GamesMaster.getInstance().getDataFolder(), "arenas.yml");
        }
        if (!arenaConfigFile.exists()) {
            GamesMaster.getInstance().saveResource("arenas.yml", false);
            config = YamlConfiguration.loadConfiguration(arenaConfigFile);
        }
    }

}
