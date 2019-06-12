package me.Jeremaster101.GamesMaster.Message;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class MessageConfig {

    private File messageConfigFile = null;
    private YamlConfiguration config = null;

    public void reloadConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(GamesMaster.plugin.getDataFolder(), "messages.yml");
        }
        config = YamlConfiguration.loadConfiguration(messageConfigFile);

        Reader defConfigStream = null;
        defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("messages.yml"),
                StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void saveConfig() {
        if (config == null || messageConfigFile == null) {
            return;
        }
        try {
            getConfig().save(messageConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + messageConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(GamesMaster.plugin.getDataFolder(), "messages.yml");
        }
        if (!messageConfigFile.exists()) {
            GamesMaster.plugin.saveResource("messages.yml", false);
        }
    }
}
