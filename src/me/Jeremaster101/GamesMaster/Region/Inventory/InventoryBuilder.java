package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;

import java.util.List;

/**
 * adds inventories for regions to use
 */
public class InventoryBuilder {
    
    private static ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    
    /**
     * builds all inventories from config
     */
    public static void buildAll() {
        for (String name : inventoryConfig.getConfig().getStringList("inventories")) {
            build(name);
        }
    }
    
    /**
     * adds an inventory to the plugin
     *
     * @param name name of inventory to make
     */
    public static void build(String name) {
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(name))
            new Inventory(name);
    }
}
