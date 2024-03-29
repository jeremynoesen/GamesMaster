package jeremynoesen.gamesmaster.config;

/**
 * initialize all configs
 */
public class Config {
    
    private static ConfigManager gui = new ConfigManager(ConfigType.GUI);
    private static ConfigManager game = new ConfigManager(ConfigType.GAME);
    private static ConfigManager arena = new ConfigManager(ConfigType.ARENA);
    private static ConfigManager lobby = new ConfigManager(ConfigType.LOBBY);
    private static ConfigManager region = new ConfigManager(ConfigType.REGION);
    private static ConfigManager command = new ConfigManager(ConfigType.COMMAND);
    private static ConfigManager message = new ConfigManager(ConfigType.MESSAGE);
    private static ConfigManager inventory = new ConfigManager(ConfigType.INVENTORY);
    private static ConfigManager gadget = new ConfigManager(ConfigType.GADGET);
    
    /**
     * @param type config type
     * @return config manager for the selected type
     */
    public static ConfigManager getConfig(ConfigType type) {
        
        switch (type) {
            case GUI:
                return gui;
                
            case GAME:
                return game;
            
            case ARENA:
                return arena;
            
            case LOBBY:
                return lobby;
            
            case REGION:
                return region;
            
            case COMMAND:
                return command;
            
            case MESSAGE:
                return message;
            
            case INVENTORY:
                return inventory;
                
            case GADGET:
                return gadget;
            
        }
        
        return null;
    }
    
}
