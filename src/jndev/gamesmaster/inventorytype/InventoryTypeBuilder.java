package jndev.gamesmaster.inventorytype;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;

/**
 * adds inventories for regions to use
 */
public class InventoryTypeBuilder {
    
    private static ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    
    /**
     * builds all inventories from config
     */
    public static void buildAll() {
        for (String name : inventoryConfig.getConfig().getStringList("inventory-types")) {
            build(name);
        }
    }
    
    /**
     * adds an inventory to the plugin
     *
     * @param name name of inventory to make
     */
    public static void build(String name) {
        new InventoryType(name);
    }
}
