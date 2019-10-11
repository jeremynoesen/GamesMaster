package me.Jeremaster101.GamesMaster.Lobby.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class GUI {
    
    private static HashMap<Inventory, GUI> guis = new HashMap<>();
    private Inventory inventory;
    private HashSet<Integer> decorations = new HashSet<>();
    private HashSet<Integer> toggled = new HashSet<>();
    private HashMap<Integer, HashMap<ItemStack, GUIButton>> buttons = new HashMap<>();
    
    public GUI(GUISize size, String name) {//todo multi page gui and custom guis
        inventory = Bukkit.getServer().createInventory(null, size.getInteger(), name);
    }
    
    public static GUI getGUI(Inventory inventory) {
        return guis.get(inventory);
    }
    
    public void openGUI(Player player) {
        player.openInventory(inventory);
        guis.put(inventory, this);
    }
    
    public void updateGUI(Inventory inventory, GUI gui) {
        guis.put(inventory, gui);
    }
    
    public Inventory getInventory() {
        guis.put(inventory, this);
        return inventory;
    }
    
    public void addButton(ItemStack item, int slot, GUIButton button) {
        HashMap<ItemStack, GUIButton> items = new HashMap<>();
        items.put(item, button);
        buttons.put(slot, items);
        if(toggled.contains(slot)) toggled.remove(slot);
        inventory.setItem(slot, item);
    }
    
    public void addToggle(ItemStack untoggledItem, ItemStack toggledItem, int slot, GUIButton untoggledButton, GUIButton toggledButton) {
        HashMap<ItemStack, GUIButton> items = new HashMap<>();
        items.put(untoggledItem, untoggledButton);
        items.put(toggledItem, toggledButton);
        buttons.put(slot, items);
        if(toggled.contains(slot)) toggled.remove(slot);
        inventory.setItem(slot, untoggledItem);
    }
    
    public void toggle(int slot) {
        HashMap<ItemStack, GUIButton> items = buttons.get(slot);
        if (items != null) {
            for (ItemStack newItem : items.keySet()) {
                if (!newItem.equals(inventory.getItem(slot))) {
                    inventory.setItem(slot, newItem);
                    updateGUI(inventory, this);
                    if (toggled.contains(slot)) toggled.remove(slot);
                    else toggled.add(slot);
                    break;
                }
            }
        }
    }
    
    public boolean isToggled(int slot) {
        return toggled.contains(slot);
    }
    
    public void addDecoration(ItemStack item, int slot) {
        decorations.add(slot);
        if(toggled.contains(slot)) toggled.remove(slot);
        inventory.setItem(slot, item);
    }
    
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null) {
            HashMap<ItemStack, GUIButton> item = buttons.get(e.getSlot());
            if (item != null) {
                GUIButton button = item.get(e.getCurrentItem());
                if (button != null) {
                    if (e.isLeftClick() && !e.isShiftClick()) {
                        if (button.onLeftClick(player)) {
                            e.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                        } else {
                            e.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                        }
                    } else if (e.isRightClick() && !e.isShiftClick()) {
                        if (button.onRightClick(player)) {
                            e.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                        } else {
                            e.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                        }
                    } else {
                        e.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                    }
                }
            } else if (decorations.contains(e.getSlot())) {
                e.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
            }
        }
    }
}
