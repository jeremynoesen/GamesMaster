package me.Jeremaster101.GamesMaster.Player;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * custom player object for gamesmaster, handles per player data and such
 */
public class GMPlayer {
    
    private static HashMap<Player, GMPlayer> gmplayers = new HashMap<>();
    private PlayerData data;
    private PlayerPreferences preferences;
    private PlayerGUI gui;
    private Player player;
    private String currentRegion;
    
    /**
     * create new instances of classes that handle player options, guis, and preferences
     *
     * @param player player to create GMPlayer object
     */
    public GMPlayer(Player player) {
        this.player = player;
        data = new PlayerData(player);
        preferences = new PlayerPreferences(player);
        gui = new PlayerGUI(player);
        currentRegion = data.getDataFile().getString("current-region");
    }
    
    /**
     * @param player player to get GMPlayer for
     * @return GMPlayer from the player
     */
    public static GMPlayer getPlayer(Player player) {
        return gmplayers.get(player);
    }
    
    /**
     * @return instance of PlayerData for the player
     */
    public PlayerData getData() {
        return data;
    }
    
    /**
     * @return instance of PlayerPreferences for the player
     */
    public PlayerPreferences getPreferences() {
        return preferences;
    }
    
    /**
     * @return instance of PlayerGUI for the player
     */
    public PlayerGUI getGUI() {
        return gui;
    }
    
    /**
     * @return name of current region the player is in
     */
    public String getCurrentRegion() {
        return currentRegion;
    }
    
    /**
     * @param region name of region to set as current
     */
    public void setCurrentRegion(String region) {
        currentRegion = region;
        data.getDataFile().set("current-region", region);
    }
    
}
