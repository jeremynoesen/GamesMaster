package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * class to construct items
 */
public class ItemBuilder {
    
    /**
     * build items from configs
     *
     * @param config config where items are stored
     * @param items  hashmap to put items in
     */
    public static void buildItems(ConfigManager config, HashMap<String, ItemStack> items) {
        for (String name : config.getConfig().getConfigurationSection("items").getKeys(false)) {
            Material material = Material.getMaterial(config.getConfig().getString("items." + name + ".material"));
            String displayName = config.getConfig().getString("items." + name + ".display-name");
            List<String> lore = config.getConfig().getStringList("items." + name + ".lore");
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
            ItemStack item = new ItemStack(material, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            meta.setLore(coloredLore);
            item.setItemMeta(meta);
            items.put(name, item);
        }
    }
}
