package me.Jeremaster101.GamesMaster.Lobby.Game;

import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GameCreation {

    Message msg = new Message();

    public void addGame(Player p, String game, String displayName, String icon, String description, String guiColor,
                        int priority) {

        if(GameConfig.getConfig().getConfigurationSection("games." + game) != null) {
            p.sendMessage(msg.ERROR_GAME_EXISTS);
            return;
        }

        try {
            Material.getMaterial(icon);
        } catch (Exception e) {
            p.sendMessage(msg.ERROR_UNKNOWN_MATERIAL);
            return;
        }

        GameConfig.getConfig().set("games." + game + ".display-name", displayName);
        GameConfig.getConfig().set("games." + game + ".icon", icon);
        GameConfig.getConfig().set("games." + game + ".description", description);
        GameConfig.getConfig().set("games." + game + ".gui-color", guiColor);
        GameConfig.getConfig().set("games." + game + ".enabled", true);
        GameConfig.getConfig().set("games." + game + ".priority", priority);

        p.sendMessage(msg.SUCCESS_GAME_ADDED.replace("$GAME$", game));

    }

    public void removeGame(Player p, String game) {

        if (GameConfig.getConfig().getConfigurationSection("games." + game) != null) {
            GameConfig.getConfig().set("games." + game, null);
            p.sendMessage(msg.SUCCESS_GAME_REMOVED.replace("$GAME$", game));
            GameConfig.saveConfig();
        } else
            p.sendMessage(msg.ERROR_UNKNOWN_GAME);

    }
}
