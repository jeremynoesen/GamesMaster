package jndev.gamesmaster.game;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;

/**
 * builds games from file
 */
public class GameBuilder {
    
    private static ConfigManager gameConfig = Config.getConfig(ConfigType.GAME);
    
    /**
     * builds all games from config
     */
    public static void buildAll() {
        for (String name : gameConfig.getConfig().getStringList("games")) {
            build(name);
        }
    }
    
    /**
     * adds an game to the plugin
     *
     * @param name name of game to make
     */
    public static void build(String name) {
        new Game(name);
    }
    
}
