package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class ArenaHandler {
    
    private static ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    
    public void enablearena(Player p, String game, String arena) {
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (!arenaConfig.getConfig().getBoolean(game + "." + arena + ".enabled")) {
                arenaConfig.getConfig().set(game + "." + arena + ".enabled", true);
                p.sendMessage(Message.SUCCESS_ARENA_ENABLED.replace("$ARENA", game + " " + arena));
                arenaConfig.saveConfig();
            } else
                p.sendMessage(Message.ERROR_ARENA_ALREADY_ENABLED);
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void disablearena(Player p, String game, String arena) {
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (arenaConfig.getConfig().getBoolean(game + "." + arena + ".enabled")) {
                arenaConfig.getConfig().set(game + "." + arena + ".enabled", false);
                p.sendMessage(Message.SUCCESS_ARENA_DISABLED.replace("$ARENA", game + " " + arena));
                arenaConfig.saveConfig();
            } else
                p.sendMessage(Message.ERROR_ARENA_ALREADY_DISABLED);
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
}
