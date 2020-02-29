package jndev.gamesmaster.lobby.gui;

import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Types of GUIs
 */
public enum GUIType {
    MUSIC("music"), COSMETICS("cosmetics"), GAME_LIST("game-list"), GAME("game"), GADGET("gadget"), PREFERENCES(
            "preferences");
    
    private String type;
    private ConfigManager guiConfig = new ConfigManager(ConfigType.GUI);
    
    GUIType(String type) {
        this.type = type;
    }
    
    /**
     * @return config section for the specific gui type
     */
    public ConfigurationSection getConfigSection() {
        return guiConfig.getConfig().getConfigurationSection("guis." + type);
    }
    
    /**
     * @return display name with colors
     */
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', guiConfig.getConfig().getString("guis." + type + ".display-name"));
    }
    
    /**
     * @return guisize of the gui
     */
    public GUISize getSize() {
        return GUISize.valueOf(guiConfig.getConfig().getString("guis." + type + ".size"));
    }
    
    
}
