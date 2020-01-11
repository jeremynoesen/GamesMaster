package me.Jeremaster101.GamesMaster.Lobby.Game;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIColor;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Game {
    
    private String game;
    private Player player;
    
    public static ConfigManager gameConfig = new ConfigManager(ConfigType.GAME);
    
    //todo fully implement this functionality of custom games
    public Game(Player player, String game) {
        this.player = player;
        if (game != null) {
            this.game = game;
            if (gameConfig.getConfig().getConfigurationSection(game) == null) {
                gameConfig.getConfig().set(game + ".enabled", false);
                gameConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_GAME_ADDED.replace("$GAME$", game));
            }
        }
    }
    
    public static Game getGame(Player player, String game) {
        if (gameConfig.getConfig().getConfigurationSection(game) != null) {
            return new Game(player, game);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
            return null;
        }
    }
    
    boolean exists() {
        if (gameConfig.getConfig().getConfigurationSection(game) != null)
            return true;
        else player.sendMessage(Message.ERROR_UNKNOWN_GAME);
        return false;
    }
    
    public void remove() {
        
        if (exists()) {
            gameConfig.getConfig().set(game, null);
            player.sendMessage(Message.SUCCESS_GAME_REMOVED.replace("$GAME$", game));
            gameConfig.saveConfig();
        }
    }
    
    public String getName() {
        if (exists() && gameConfig.getConfig().get(game + ".display-name") != null) {
            return gameConfig.getConfig().getString(game + ".display-name");
        }
        return null;
    }
    
    public void setName(String displayName) {
    
        if (exists()) {
            gameConfig.getConfig().set(game + ".display-name", displayName);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_NAME.replace("$NAME$", displayName));
        }
    }
    
    public Material getIcon() {
        if (exists() && gameConfig.getConfig().get(game + ".icon") != null) {
            try {
                return Material.getMaterial(gameConfig.getConfig().getString(game + ".icon"));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    public void setIcon(Material icon) {
        
        if (exists()) {
            //try {
            //    Material.getMaterial(icon);
            gameConfig.getConfig().set(game + ".icon", icon.name());
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_ICON.replace("$ICON$",
                    icon.name().replace("_", "").toLowerCase()));
            //} catch (Exception e) {
            //    player.sendMessage(Message.ERROR_UNKNOWN_MATERIAL);
            //}
        }
        
    }
    
    public String getDescription() {
        if (exists() && gameConfig.getConfig().get(game + ".description") != null) {
            return gameConfig.getConfig().getString(game + ".description");
        }
        return null;
    }
    
    public void setDescription(String description) {
        
        if (exists()) {
            gameConfig.getConfig().set(game + ".description", description);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_DESCRIPTION.replace("$DESCRIPTION$", description));
        }
    }
    
    public void setGuiColor(GUIColor color) {
        if (exists()) {
            gameConfig.getConfig().set(game + ".gui-color", color);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_COLOR.replace("$COLOR$",
                    color.toString().replace("_", "").toLowerCase()));
        }
    }
    
    public int getPriority() {
        if (exists() && gameConfig.getConfig().get(game + ".priority") != null) {
            return gameConfig.getConfig().getInt(game + ".priority");
        }
        return 0;
    }
    
    public void setPriority(int priority) {
        
        if (exists()) {
            gameConfig.getConfig().set(game + ".priority", priority);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
        }
    }
    
    public boolean isHidden() {
        if (exists() && gameConfig.getConfig().get(game + ".hidden") != null) {
            return gameConfig.getConfig().getBoolean(game + ".hidden");
        }
        
        return false;
    }
    
    public void setHidden(boolean hidden) {
        
        if (exists()) {
            gameConfig.getConfig().set(game + ".hidden", hidden);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
        }
    }
    
    public boolean isEnabled() {
        if (exists() && gameConfig.getConfig().get(game + ".enabled") != null) {
            return gameConfig.getConfig().getBoolean(game + ".enabled");
        }
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        
        if (exists()) {
            //todo only enable if all requirements met
            gameConfig.getConfig().set(game + ".enabled", true);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
        }
    }
}
