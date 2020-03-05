package jndev.gamesmaster.game;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;

import java.util.ArrayList;
import java.util.List;

/**
 * saves games to file
 */
public class GameSaver {
    
    private static ConfigManager gameConfig = Config.getConfig(ConfigType.GAME);
    
    /**
     * saves all inventory types to file
     */
    public static void saveAll() {
        List<String> games = new ArrayList<>();
        for(Game game : Game.getGames()) {
            games.add(game.getName());
        }
        gameConfig.getConfig().set("games", games);
        gameConfig.saveConfig();
    }
    
}
