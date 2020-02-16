package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIType;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * custom player object for gamesmaster, handles per player data, preferences, and guis
 */
public class GMPlayer {
    
    private static HashMap<Player, GMPlayer> gmplayers = new HashMap<>();
    private PlayerData data;
    private PlayerPreferences preferences;
    private PlayerGUI gui;
    private Player player;
    private String currentRegion;
    
    /**
     * create new instances of classes that handle player options, guis, and preferences. also load current region
     *
     * @param player player to create GMPlayer object
     */
    public GMPlayer(Player player) {
        this.player = player;
        data = new PlayerData(player);
        preferences = new PlayerPreferences(player);
        gui = new PlayerGUI(player);
        currentRegion = data.getDataFile().getString("current-region");
        gmplayers.put(player, this);
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
     * update the saved player preferences
     *
     * @param preferences player preferences
     */
    public void setPreferences(PlayerPreferences preferences) {
        this.preferences = preferences;
        gmplayers.put(player, this);
    }
    
    /**
     * open the gui of specified type
     */
    public void openGUI(GUIType type) {
        gui.open(type);
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
        data.savePlayerData();
        gmplayers.put(player, this);
    }
    
}
