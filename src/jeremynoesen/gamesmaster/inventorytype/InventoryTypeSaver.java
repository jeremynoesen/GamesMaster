package jeremynoesen.gamesmaster.inventorytype;

import jeremynoesen.gamesmaster.config.Config;
import jeremynoesen.gamesmaster.config.ConfigManager;
import jeremynoesen.gamesmaster.config.ConfigType;

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
