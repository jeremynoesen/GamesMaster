package me.Jeremaster101.GamesMaster.Lobby.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class GUI { //todo save guis for gadgets and cosmetics per player
    
    private static HashMap<Inventory, GUI> guis = new HashMap<>();
    private static HashMap<GUIType, GUI> guiTypes = new HashMap<>();
    private Inventory inventory;
    private HashSet<Integer> decorations = new HashSet<>();
    private HashSet<Integer> toggled = new HashSet<>();
    private HashMap<Integer, HashMap<ItemStack, GUIButton>> buttons = new HashMap<>();
    private GUISize size;
    private String name;
    private GUIType type;
    
    /**
     * create a new GUI of specified size and name
     *
     * @param size inventory size
     * @param name name of GUI
     */
    public GUI(GUIType type, GUISize size, String name) {
        inventory = Bukkit.getServer().createInventory(null, size.getInteger(), name);
        this.size = size;
        this.name = name;
        this.type = type;
    }
    
    /**
     * @param inventory get GUI by inventory
     * @return GUI if one exists
     */
    public static GUI getGUI(Inventory inventory) {
        return guis.get(inventory);
    }
    
    /**
     * @param type guitype to get
     * @return gui of desired type
     */
    public static GUI getGUI(GUIType type) {
        return guiTypes.get(type);
    }
    
    /**
     * @return name of gui with colors
     */
    public String getDisplayName() {
        return name;
    }
    
    /**
     * @return name of gui without colors
     */
    public String getName() {
        return ChatColor.stripColor(name);
    }
    
    /**
     * @return slots that are decorations
     */
    public HashSet<Integer> getDecorations() {
        return decorations;
    }
    
    /**
     * @param decorations decorations to set for gui
     */
    public void setDecorations(HashSet<Integer> decorations) {
        this.decorations = decorations;
    }
    
    /**
     * @return all buttons with associated actions for the gui
     */
    public HashMap<Integer, HashMap<ItemStack, GUIButton>> getButtons() {
        return buttons;
    }
    
    /**
     * @param buttons buttons to set to the gui
     */
    public void setButtons(HashMap<Integer, HashMap<ItemStack, GUIButton>> buttons) {
        this.buttons = buttons;
    }
    
    /**
     * @return type of gui
     */
    public GUIType getType() {
        return type;
    }
    
    /**
     * get gui size
     *
     * @return guisize of the gui
     */
    public GUISize getSize() {
        return size;
    }
    
    /**
     * Open GUI inventory for a player
     *
     * @param player player to view GUI
     */
    public void open(Player player) {
        player.openInventory(inventory);
        save();
    }
    
    /**
     * saves a gui and puts it in the guis list
     */
    public void save() {
        guis.put(inventory, this);
        if (type != null) guiTypes.put(type, this);
    }
    
    /**
     * @return inventory of GUI
     */
    public Inventory getInventory() {
        save();
        return inventory;
    }
    
    /**
     * @param inventory inventory to set gui to have
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        save();
    }
    
    /**
     * add a regular button the GUI, with right and left click actions
     *
     * @param item   button icon
     * @param slot   slot to place button in GUI
     * @param button button actions
     */
    public void addButton(ItemStack item, int slot, GUIButton button) {
        HashMap<ItemStack, GUIButton> items = new HashMap<>();
        items.put(item, button);
        buttons.put(slot, items);
        inventory.setItem(slot, item);
    }
    
    /**
     * add a toggle button, which allows for two separate buttons per slot, each with right and left click actions
     *
     * @param untoggledItem   item when the toggle is in the off state
     * @param toggledItem     item when the toggle is in the on state
     * @param slot            slot to place the toggle in the GUI
     * @param untoggledButton button actions for the untoggled button
     * @param toggledButton   button actions for the toggled button
     */
    public void addToggle(ItemStack untoggledItem, ItemStack toggledItem, int slot, GUIButton untoggledButton, GUIButton toggledButton) {
        HashMap<ItemStack, GUIButton> items = new HashMap<>();
        items.put(untoggledItem, untoggledButton);
        items.put(toggledItem, toggledButton);
        buttons.put(slot, items);
        if (toggled.contains(slot)) toggled.remove(slot);
        inventory.setItem(slot, untoggledItem);
    }
    
    /**
     * toggles a toggle button between the two different buttons for the slot
     *
     * @param slot slot to toggle
     */
    public void toggle(int slot) {
        HashMap<ItemStack, GUIButton> items = buttons.get(slot);
        if (items != null) {
            for (ItemStack newItem : items.keySet()) {
                if (!newItem.equals(inventory.getItem(slot))) {
                    inventory.setItem(slot, newItem);
                    save();
                    if (toggled.contains(slot)) toggled.remove(slot);
                    else toggled.add(slot);
                    break;
                }
            }
        }
    }
    
    /**
     * @param slot slot to check if toggled
     * @return true if toggled
     */
    public boolean isToggled(int slot) {
        return toggled.contains(slot);
    }
    
    /**
     * add a actionless decoration item to the GUI
     *
     * @param item item to set as a decoration
     * @param slot slot to put the decoration in
     */
    public void addDecoration(ItemStack item, int slot) {
        decorations.add(slot);
        inventory.setItem(slot, item);
    }
    
    /**
     * check when a player clicks on in a GUI
     */
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null) {
            HashMap<ItemStack, GUIButton> items = buttons.get(e.getSlot());
            if (items != null) {
                GUIButton button = items.get(e.getCurrentItem());
                if (button != null) {
                    if (e.isLeftClick() && !e.isShiftClick()) {
                        if (button.onLeftClick(player)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                        } else {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                        }
                    } else if (e.isRightClick() && !e.isShiftClick()) {
                        if (button.onRightClick(player)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                        } else {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
                    }
                }
            } else if (decorations.contains(e.getSlot())) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 0);
            }
        }
    }
}
