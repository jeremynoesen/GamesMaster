package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class ArenaHandler {

    private final Message msg = new Message();

    public void enableArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) != null) {
            if (!ArenaConfig.getConfig().getBoolean("arenas." + game + "." + arena + ".enabled")) {
                ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".enabled", true);
                p.sendMessage(msg.SUCCESS_ARENA_ENABLED.replace("$ARENA", game + " " + arena));
                ArenaConfig.saveConfig();
            } else
                p.sendMessage(msg.ERROR_ARENA_ALREADY_ENABLED);
        } else
            p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
    }

    public void disableArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) != null) {
            if (ArenaConfig.getConfig().getBoolean("arenas." + game + "." + arena + ".enabled")) {
                ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".enabled", false);
                p.sendMessage(msg.SUCCESS_ARENA_DISABLED.replace("$ARENA", game + " " + arena));
                ArenaConfig.saveConfig();
            } else
                p.sendMessage(msg.ERROR_ARENA_ALREADY_DISABLED);
        } else
            p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
    }
}
