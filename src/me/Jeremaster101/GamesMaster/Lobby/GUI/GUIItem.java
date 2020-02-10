package me.Jeremaster101.GamesMaster.Lobby.GUI;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIItem {
    
    private ItemStack item;
    
    /**
     * create an itemstack for use in a GUI
     * @param material item material
     * @param displayName display name of item
     * @param lore item lore
     */
    GUIItem(Material material, String displayName, List<String> lore) {
        item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    
    public ItemStack toItemStack() {
        return item;
    }
    
    
}
