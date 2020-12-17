package jeremynoesen.gamesmaster.inventorytype;

import jeremynoesen.gamesmaster.config.Config;
import jeremynoesen.gamesmaster.config.ConfigManager;
import jeremynoesen.gamesmaster.config.ConfigType;

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
