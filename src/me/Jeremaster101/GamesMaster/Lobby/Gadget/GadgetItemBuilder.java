package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * gadget item builder
 */
public class GadgetItemBuilder {
    
    public static HashMap<String, ItemStack> items = new HashMap<>();
    
    /**
     * create items based on config values and store them in the hashmap
     */
    public static void craftItems() {
        ConfigManager config = Config.getConfig(ConfigType.GADGET);
        ItemBuilder.buildItems(config, items);
    }
    
    /**
     * @param name item name
     * @return gui item
     */
    public static ItemStack getItem(String name) {
        return items.get(name);
    }
    
    /**
     * @param gadget item to get name of
     * @return name if the item is a gadget, null if not
     */
    public static String getName(ItemStack gadget) {
        for(String name : items.keySet()) {
            if(items.get(name).equals(gadget)) return name;
        }
        return null;
    }

}
