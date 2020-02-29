package jndev.gamesmaster.region.inventory;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;

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
