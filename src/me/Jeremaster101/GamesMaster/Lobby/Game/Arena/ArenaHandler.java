package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class ArenaHandler {

    public void enableArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (!ArenaConfig.getConfig().getBoolean(game + "." + arena + ".enabled")) {
                ArenaConfig.getConfig().set(game + "." + arena + ".enabled", true);
                p.sendMessage(Message.SUCCESS_ARENA_ENABLED.replace("$ARENA", game + " " + arena));
                ArenaConfig.saveConfig();
            } else
                p.sendMessage(Message.ERROR_ARENA_ALREADY_ENABLED);
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void disableArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (ArenaConfig.getConfig().getBoolean(game + "." + arena + ".enabled")) {
                ArenaConfig.getConfig().set(game + "." + arena + ".enabled", false);
                p.sendMessage(Message.SUCCESS_ARENA_DISABLED.replace("$ARENA", game + " " + arena));
                ArenaConfig.saveConfig();
            } else
                p.sendMessage(Message.ERROR_ARENA_ALREADY_DISABLED);
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
}
