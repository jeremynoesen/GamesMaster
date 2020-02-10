package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import org.bukkit.configuration.ConfigurationSection;

public enum GUIType {
    MUSIC("music"), COSMETICS("cosmetics"), GAME_LIST("game-list"), GAME("game"), GADGET("gadget");
    
    public String type;
    private ConfigManager guiConfig = new ConfigManager(ConfigType.GUI);
    
    GUIType(String type) {
        this.type = type;
    }
    
    public ConfigurationSection getConfigSection() {
        return guiConfig.getConfig().getConfigurationSection("gui." + type);
    }
    
    public String getDisplayName() {
        return guiConfig.getConfig().getString("gui." + type + ".display-name");
    }
    
    public GUISize getSize() {
        return GUISize.valueOf(guiConfig.getConfig().getString("gui." + type + ".size"));
    }
    
    
}