package jndev.gamesmaster.gui.item;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * item creation from config
 */
public class GUIItemBuilder {
    
    public static HashMap<String, ItemStack> items = new HashMap<>();
    
    /**
     * create items based on config values and store them in the hashmap
     */
    public static void craftItems() {
        ConfigManager config = Config.getConfig(ConfigType.GUI);
        ItemBuilder.buildItems(config, items);
    }
    
    /**
     * @param name item name
     * @return gui item
     */
    public static ItemStack getItem(String name) {
        return items.get(name);
    }
    
}
