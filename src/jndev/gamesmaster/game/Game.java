package jndev.gamesmaster.game;

import java.util.Collection;
import java.util.HashMap;

/**
 * game to organize gui with
 */
public class Game {
    
    public static HashMap<String, Game> games = new HashMap<>();
    private String name;
    
    /**
     * creates a new game
     *
     * @param name name of game
     */
    public Game(String name) {
        this.name = name;
        games.put(name, this);
    }
    
    /**
     * @param name name of gane
     * @return game object of specified name
     */
    public static Game getGame(String name) {
        return games.get(name);
    }
    
    /**
     * @return all games
     */
    public static Collection<Game> getGames() {
        return games.values();
    }
    
    /**
     * @return name of game
     */
    public String getName() {
        return name;
    }
    
    /**
     * removes inventory from config and plugin
     */
    public void remove() {
        games.remove(name);
    }
    
    
}
