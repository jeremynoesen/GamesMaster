package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public enum GUIType {
    MUSIC("music"), COSMETICS("cosmetics"), GAME_LIST("game-list"), GAME("game"), GADGET("gadget");
    
    public String type;
    private ConfigManager guiConfig = new ConfigManager(ConfigType.GUI);
    
    GUIType(String type) {
        this.type = type;
    }
    
    public ConfigurationSection getConfigSection() {
        return guiConfig.getConfig().getConfigurationSection("guis." + type);
    }
    
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', guiConfig.getConfig().getString("guis." + type + ".display-name"));
    }
    
    public GUISize getSize() {
        return GUISize.valueOf(guiConfig.getConfig().getString("guis." + type + ".size"));
    }
    
    
}
