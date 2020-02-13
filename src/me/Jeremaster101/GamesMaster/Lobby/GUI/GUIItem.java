package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIItem {
    
    public static HashMap<String, ItemStack> items = new HashMap<>();
    
    /**
     * create items based on config values and store them in the hashmap
     */
    public static void craftItems() {
        ConfigManager config = Configs.getConfig(ConfigType.GUI);
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
    
    /**
     * @param name item name
     * @return gui item
     */
    public static ItemStack getItem(String name) {
        return items.get(name);
    }
    
}
