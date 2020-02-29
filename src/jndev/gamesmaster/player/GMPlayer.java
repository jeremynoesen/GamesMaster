package jndev.gamesmaster.player;

import jndev.gamesmaster.region.inventory.Inventory;
import jndev.gamesmaster.lobby.gui.GUIType;
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
    private PlayerInventory inventory;
    private PlayerRegion region;
    private Player player;
    private PlayerLobby lobby;
    
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
        region = new PlayerRegion(player);
        lobby = new PlayerLobby(player);
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
    public PlayerData getPlayerData() {
        return data;
    }
    
    /**
     * @return instance of PlayerPreferences for the player
     */
    public PlayerPreferences getPreferences() {
        return preferences;
    }
    
    /**
     * @return lobby handler for the player
     */
    public PlayerLobby getLobbyHandler() {
        return lobby;
    }
    
    /**
     * update the saved player preferences
     *
     * @param preferences player preferences
     */
    public void savePreferences(PlayerPreferences preferences) {
        this.preferences = preferences;
        gmplayers.put(player, this);
    }
    
    /**
     * @param inventory inventory to get
     * @return inventory of specified type
     */
    public PlayerInventory getInventory(Inventory inventory) {
        return PlayerInventory.getPlayerInventory(player, inventory);
    }
    
    /**
     * @return region handler for the player
     */
    public PlayerRegion getRegionHandler() {
        return region;
    }
    
    /**
     * update player region data
     *
     * @param region PlayerRegion object
     */
    public void updateRegionHandler(PlayerRegion region) {
        this.region = region;
        gmplayers.put(player, this);
    }
    
    /**
     * open the gui of specified type
     */
    public void openGUI(GUIType type) {
        gui.open(type);
    }
    
}
