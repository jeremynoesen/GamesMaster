package jndev.gamesmaster.inventorytype;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;

import java.util.ArrayList;
import java.util.List;

/**
 * save inventory types to file
 */
public class InventoryTypeSaver {
    
    private static ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    
    /**
     * saves all inventory types to file
     */
    public static void saveAll() {
        List<String> inventoryTypes = new ArrayList<>();
        for (InventoryType type : InventoryType.getInventoryTypes()) {
            inventoryTypes.add(type.getName());
        }
        inventoryConfig.getConfig().set("inventory-types", inventoryTypes);
        inventoryConfig.saveConfig();
    }
    
}
