package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIType;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.Gadget;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.GadgetItem;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.GadgetItemBuilder;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import org.bukkit.Location;
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
        inventory = new PlayerInventory(player);
        region = new PlayerRegion(player);
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
     * update the saved player preferences
     *
     * @param preferences player preferences
     */
    public void savePreferences(PlayerPreferences preferences) {
        this.preferences = preferences;
        gmplayers.put(player, this);
    }
    
    public PlayerInventory getInventory() {
        return inventory;
    }
    
    public PlayerRegion getRegionHandler() {
        return region;
    }
    
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
    
    /**
     * @return true if the player is in the lobby
     */
    public boolean isInLobby() {
        Location l = player.getLocation();
        
        try {
            ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
            double maxx = regionConfig.getConfig().getDouble("lobby.location.max.x");
            double maxy = regionConfig.getConfig().getDouble("lobby.location.max.y");
            double maxz = regionConfig.getConfig().getDouble("lobby.location.max.z");
            double minx = regionConfig.getConfig().getDouble("lobby.location.min.x");
            double miny = regionConfig.getConfig().getDouble("lobby.location.min.y");
            double minz = regionConfig.getConfig().getDouble("lobby.location.min.z");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (LobbyHandler.isGamesWorld(l.getWorld()) &&
                    (tox <= maxx) && (tox >= minx) && (toy <= maxy) && (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * @return current gadget the player has
     */
    public GadgetItem getGadgetItem() {
        if (player.getInventory().getItem(LobbyInventory.getGadgetSlot()) != null)
            return GadgetItem.valueOf(GadgetItemBuilder.getName(player.getInventory().getItem(LobbyInventory.getGadgetSlot())));
        return null;
    }
    
    /**
     * set the active gadget for the player
     *
     * @param item gadget to set
     */
    public void setGadgetItem(GadgetItem item) {
        player.getInventory().setItem(LobbyInventory.getGadgetSlot(), item.getItem());
        player.updateInventory();
    }
    
}
