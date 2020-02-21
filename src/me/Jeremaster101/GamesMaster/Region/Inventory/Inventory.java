package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * inventories used for regions
 */
public class Inventory {
    
    private static HashMap<String, Inventory> inventories;
    private static ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    private String name;
    
    /**
     * adds a new inventory to the list of inventories
     *
     * @param name inventory name
     */
    public Inventory(String name) {
        this.name = name;
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(name)) {
            invs.add(name);
            inventoryConfig.getConfig().set("inventories", invs);
            inventoryConfig.saveConfig();
            inventories.put(name, this);
        }
    }
    
    /**
     * gets inventory by name
     *
     * @param name inventory name
     * @return inventory
     */
    public static Inventory getInventory(String name) {
        return inventories.get(name);
    }
    
    /**
     * @return all inventories
     */
    public static Collection<Inventory> getInventories() {
        return inventories.values();
    }
    
    /**
     * @return name of inventory
     */
    public String getName() {
        return name;
    }
    
    /**
     * removes inventory from config and plugin
     */
    public void remove() {
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        invs.remove(name);
        inventoryConfig.getConfig().set("inventories", invs);
        inventoryConfig.saveConfig();
        inventories.put(name, null);
        
        for (Player allOn : Bukkit.getOnlinePlayers()) {
            GMPlayer gmplayer = GMPlayer.getPlayer(allOn);
            if (gmplayer.getRegionHandler().getLoadedRegion() != null) {
                gmplayer.getPlayerData().getDataFile().set("inventories." + name, null);
                gmplayer.getPlayerData().savePlayerData();
            }
        }
        
        for (OfflinePlayer allOff : Bukkit.getOfflinePlayers()) {
            GMPlayer gmplayer = GMPlayer.getPlayer(allOff.getPlayer());
            if (gmplayer.getRegionHandler().getLoadedRegion() != null) {
                gmplayer.getPlayerData().getDataFile().set("inventories." + name, null);
                gmplayer.getPlayerData().savePlayerData();
            }
        }
    }
    
}
