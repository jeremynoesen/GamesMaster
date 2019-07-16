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

    private static File messageConfigFile = null;
    private static YamlConfiguration config = null;

    public static void reloadConfig() {

        if (messageConfigFile == null) {
            messageConfigFile = new File(GamesMaster.plugin.getDataFolder(), "messages.yml");
        }

        config = YamlConfiguration.loadConfiguration(messageConfigFile);

        Reader defConfigStream = new InputStreamReader(GamesMaster.plugin.getResource("messages.yml"),
                StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
        config.options().copyDefaults(true);
        saveConfig();

        Message.PREFIX = Message.colorize(getConfig().getString("PREFIX"));
        Message.ERROR_CMD_BLOCKED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_CMD_BLOCKED"));
        Message.ERROR_GAMEMODE = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_GAMEMODE"));
        Message.ERROR_WORLD = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_WORLD"));
        Message.ERROR_NULL_BOUNDS = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NULL_BOUNDS"));
        Message.ERROR_ARENA_DISABLED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_ARENA_DISABLED"));
        Message.ERROR_NOT_IN_LOBBY = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NOT_IN_LOBBY"));
        Message.ERROR_UNKNOWN_INV = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_INV"));
        Message.ERROR_INV_EXISTS = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_INV_EXISTS"));
        Message.ERROR_UNKNOWN_REGION = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_REGION"));
        Message.ERROR_DEFAULT_REGION = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_DEFAULT_REGION"));
        Message.ERROR_DEFAULT_INV = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_DEFAULT_INV"));
        Message.ERROR_GAME_LIST = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_GAME_LIST"));
        Message.ERROR_UNKNOWN_ARENA = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_ARENA"));
        Message.SUCCESS_REGION_SET = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_REGION_SET"));
        Message.SUCCESS_UPDATED_REGION_INV = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_UPDATED_REGION_INV"));
        Message.SUCCESS_UPDATED_REGION_BOUNDS = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_UPDATED_REGION_BOUNDS"));
        Message.SUCCESS_UPDATED_REGION_MODE = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_UPDATED_REGION_MODE"));
        Message.SUCCESS_UPDATED_REGION_LEAVE = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_UPDATED_REGION_LEAVE"));
        Message.SUCCESS_REMOVED_REGION_LEAVE = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_REMOVED_REGION_LEAVE"));
        Message.SUCCESS_REGION_REMOVED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_REGION_REMOVED"));
        Message.SUCCESS_REGION_SELECTED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_REGION_SELECTED"));
        Message.SUCCESS_ARENA_ENABLED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_ARENA_ENABLED"));
        Message.SUCCESS_ARENA_DISABLED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_ARENA_DISABLED"));
        Message.SUCCESS_ARENA_ADDED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_ARENA_ADDED"));
        Message.SUCCESS_ARENA_REMOVED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_ARENA_REMOVED"));
        Message.SUCCESS_ARENA_UPDATED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_ARENA_UPDATED"));
        Message.SUCCESS_INV_ADDED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_INV_ADDED"));
        Message.SUCCESS_INV_REMOVED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_INV_REMOVED"));
        Message.ERROR_CMD_ALREADY_BLOCKED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_CMD_ALREADY_BLOCKED"));
        Message.ERROR_CMD_NOT_BLOCKED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_CMD_NOT_BLOCKED"));
        Message.SUCCESS_CMD_BLOCKED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_CMD_BLOCKED"));
        Message.SUCCESS_CMD_UNBLOCKED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_CMD_UNBLOCKED"));
        Message.SUCCESS_PWI_FIX_ENABLED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_PWI_FIX_ENABLED"));
        Message.SUCCESS_PWI_FIX_DISABLED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_PWI_FIX_DISABLED"));
        Message.ERROR_NO_REGION_NAME = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_REGION_NAME"));
        Message.ERROR_NO_ARENA_NAME = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_ARENA_NAME"));
        Message.ERROR_NO_GAME = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_GAME"));
        Message.ERROR_NO_GAMES = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_GAMES"));
        Message.ERROR_NO_ARENA = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_ARENA"));
        Message.ERROR_NO_GAME_COMMAND = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_GAME_COMMAND"));
        Message.ERROR_NO_CMD_TO_BLOCK = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_NO_CMD_TO_BLOCK"));
        Message.ERROR_UNKNOWN_COMMAND = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_COMMAND"));
        Message.SUCCESS_RELOADED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_RELOADED"));
        Message.SUCCESS_RELOADED_ALL = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_RELOADED_ALL"));
        Message.SUCCESS_SET_WORLD = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_SET_WORLD"));
        Message.ERROR_GAME_NOT_SETUP = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_GAME_NOT_SETUP"));
        Message.ERROR_ARENA_MAX = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_ARENA_MAX"));
        Message.ERROR_REGION_EXISTS = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_REGION_EXISTS"));
        Message.ERROR_ARENA_EXISTS = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_ARENA_EXISTS"));
        Message.ERROR_GAME_EXISTS = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_GAME_EXISTS"));
        Message.ERROR_UNKNOWN_GAME = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_GAME"));
        Message.ERROR_UNKNOWN_MATERIAL = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_UNKNOWN_MATERIAL"));
        Message.SUCCESS_GAME_REMOVED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_GAME_REMOVED"));
        Message.SUCCESS_GAME_ADDED = Message.PREFIX + Message.colorize(getConfig().getString("SUCCESS_GAME_ADDED"));
        Message.ERROR_ARENA_ALREADY_ENABLED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_ARENA_ALREADY_ENABLED"));
        Message.ERROR_ARENA_ALREADY_DISABLED = Message.PREFIX + Message.colorize(getConfig().getString("ERROR_ARENA_ALREADY_DISABLED"));
        Message.LIST_BLOCKED_CMDS = Message.PREFIX + Message.colorize(getConfig().getString("LIST_BLOCKED_CMDS"));
        Message.LIST_REGIONS = Message.PREFIX + Message.colorize(getConfig().getString("LIST_REGIONS"));
        Message.LIST_INVS = Message.PREFIX + Message.colorize(getConfig().getString("LIST_INVS"));


    }

    public static YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public static void saveConfig() {
        if (config == null || messageConfigFile == null) {
            return;
        }
        try {
            getConfig().save(messageConfigFile);
        } catch (IOException ex) {
            GamesMaster.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + messageConfigFile, ex);
        }
    }

    public static void saveDefaultConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(GamesMaster.plugin.getDataFolder(), "messages.yml");
        }
        if (!messageConfigFile.exists()) {
            GamesMaster.plugin.saveResource("messages.yml", false);
            config = YamlConfiguration.loadConfiguration(messageConfigFile);
        }
    }
}
