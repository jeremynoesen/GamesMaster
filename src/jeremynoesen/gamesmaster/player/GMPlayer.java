package jeremynoesen.gamesmaster.player;

import jeremynoesen.gamesmaster.inventorytype.InventoryType;
import jeremynoesen.gamesmaster.lobby.LobbyInventory;
import jeremynoesen.gamesmaster.region.Region;
import jeremynoesen.gamesmaster.gadget.GadgetItem;
import jeremynoesen.gamesmaster.gadget.GadgetItemBuilder;
import jeremynoesen.gamesmaster.gui.GUI;
import jeremynoesen.gamesmaster.gui.GUIBuilder;
import jeremynoesen.gamesmaster.gui.GUIType;
import jeremynoesen.gamesmaster.inventory.Inventory;
import jeremynoesen.gamesmaster.inventory.InventoryBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

/**
 * custom player object for gamesmaster, handles per player data, preferences, and guis
 */
public class GMPlayer {
    
    private static HashMap<Player, GMPlayer> gmplayers = new HashMap<>();
    private PlayerFile data;
    private PlayerPreferences preferences;
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
        
        data = new PlayerFile(player);
        preferences = new PlayerPreferences(player);
        region = new PlayerRegion(player);
        lobby = new PlayerLobby(player);
        
        GUIBuilder.buildAll(player);
        InventoryBuilder.buildAll(player);
        
        gmplayers.put(player, this);
    }
    
    /**
     * @param player player to get GMPlayer for
     * @return GMPlayer from the player
     */
    public static GMPlayer getGMPlayer(Player player) {
        return gmplayers.get(player);
    }
    
    /**
     * @return all gmplayers
     */
    public static Collection<GMPlayer> getGMPlayers() {
        return gmplayers.values();
    }
    
    /**
     * @return bukkit player of the gmplayer
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * @return instance of PlayerData for the player
     */
    public PlayerFile getPlayerData() {
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
     * @return true if the player is in the lobby
     */
    public boolean isInLobby() {
        Location l = player.getLocation();
        return Region.getRegion("lobby").containsLocation(l);
    }
    
    /**
     * @param inventoryType inventory to get
     * @return inventory of specified type
     */
    public Inventory getInventory(InventoryType inventoryType) {
        return Inventory.getInventory(player, inventoryType);
    }
    
    /**
     * @param inventoryType type to put inventory as
     */
    public void addInventory(InventoryType inventoryType) {
        new Inventory(inventoryType, player);
    }
    
    /**
     * @return region handler for the player
     */
    public PlayerRegion getRegionHandler() {
        return region;
    }
    
    /**
     * open the gui of specified type
     */
    public void openGUI(GUIType type) {
        GUI.getGUI(type, player).open();
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
