package me.Jeremaster101.GamesMaster.Lobby.Game;

import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIColor;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Game {
    
    private String game;
    private Player player;
    
    //todo fully implement this functionality of custom games
    public Game(Player player, String game) {//todo remove player from pramas, remove messages from class
        this.player = player;
        if (game != null) {
            this.game = game;
            if (GameConfig.getConfig().getConfigurationSection(game) == null) {
                GameConfig.getConfig().set(game + ".enabled", false);
                GameConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_GAME_ADDED.replace("$GAME$", game));
            }
        }
    }
    
    public static Game getGame(Player player, String game) {
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            return new Game(player, game);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
            return null;
        }
    }
    
    public void remove() {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game, null);
            player.sendMessage(Message.SUCCESS_GAME_REMOVED.replace("$GAME$", game));
            GameConfig.saveConfig();
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
        
    }
    
    public String getName() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".display-name") != null) {
            return GameConfig.getConfig().getString(game + ".display-name");
        }
        return null;
    }
    
    public void setName(String displayName) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game + ".display-name", displayName);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_NAME.replace("$NAME$", displayName));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
    
    public Material getIcon() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".icon") != null) {
            try {
                return Material.getMaterial(GameConfig.getConfig().getString(game + ".icon"));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    public void setIcon(Material icon) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            //try {
            //    Material.getMaterial(icon);
            GameConfig.getConfig().set(game + ".icon", icon.name());
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_ICON.replace("$ICON$",
                    icon.name().replace("_", "").toLowerCase()));
            //} catch (Exception e) {
            //    player.sendMessage(Message.ERROR_UNKNOWN_MATERIAL);
            //}
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
        
    }
    
    public String getDescription() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".description") != null) {
            return GameConfig.getConfig().getString(game + ".description");
        }
        return null;
    }
    
    public void setDescription(String description) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game + ".description", description);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_DESCRIPTION.replace("$DESCRIPTION$", description));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
    
    public void setGuiColor(GUIColor color) {
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game + ".gui-color", color);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_COLOR.replace("$COLOR$",
                    color.toString().replace("_", "").toLowerCase()));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
    
    public int getPriority() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".priority") != null) {
            return GameConfig.getConfig().getInt(game + ".priority");
        }
        return 0;
    }
    
    public void setPriority(int priority) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game + ".priority", priority);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
    
    public boolean isHidden() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".hidden") != null) {
            return GameConfig.getConfig().getBoolean(game + ".hidden");
        }
        return false;
    }
    
    public void setHidden(boolean hidden) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            GameConfig.getConfig().set(game + ".hidden", hidden);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
    
    public boolean isEnabled() {
        if (GameConfig.getConfig().getConfigurationSection(game) != null &&
                GameConfig.getConfig().get(game + ".enabled") != null) {
            return GameConfig.getConfig().getBoolean(game + ".enabled");
        }
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        
        if (GameConfig.getConfig().getConfigurationSection(game) != null) {
            //todo only enable if all requirements met
            GameConfig.getConfig().set(game + ".enabled", true);
            GameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_GAME_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_GAME);
    }
}
