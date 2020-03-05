package jndev.gamesmaster.lobby.game;

import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.Message;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.lobby.gui.GUIColor;
import org.bukkit.Material;

import java.util.HashMap;

public class Game { //todo remove icon from here. icon will be chosen with gui builder
    
    public static ConfigManager gameConfig = new ConfigManager(ConfigType.GAME);
    public static HashMap<String, Game> games = new HashMap<>();
    private String name;
    private String displayName;
    private Material iconMaterial;
    
    public Game(String name) {
        this.name = name;
        games.put(name, this);
    }
    
    public static Game getGame(String name) {
        return games.get(name);
    }
    
    public void remove() {
        games.remove(name);
        gameConfig.getConfig().set(name, null);
        gameConfig.saveConfig();
    }
    
    public String getName() {
        return name;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        gameConfig.getConfig().set(name + ".display-name", displayName);
        gameConfig.saveConfig();
        games.put(name, this);
    }
    
    public Material getIcon() {
        return iconMaterial;
    }
    
    public void setIcon(Material material) {
        iconMaterial = material;
        games.put(name, this);
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
            player.sendMessage(Message.SUCCESS_SET_GAME_DESCRIPTION.replace("$DESCRIPTION$", description));
        }
    }
    
    public GUIColor getGuiColor() {
        if (exists()) {
            if (gameConfig.getConfig().get(game + ".gui-color") != null) {
                return (GUIColor) gameConfig.getConfig().get(game + ".gui-color");
            }
        }
        return null;
    }
    
    public void setGuiColor(GUIColor color) {
        if (exists()) {
            gameConfig.getConfig().set(game + ".gui-color", color);
            gameConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_SET_GAME_COLOR.replace("$COLOR$",
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
            player.sendMessage(Message.SUCCESS_SET_GAME_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
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
            player.sendMessage(Message.SUCCESS_SET_GAME_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
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
            if (getDescription() != null && getGuiColor() != null && getIcon() != null && getName() != null) {
                gameConfig.getConfig().set(game + ".enabled", true);
                gameConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_SET_GAME_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
            } else {
                //message
            }
        }
    }
}
