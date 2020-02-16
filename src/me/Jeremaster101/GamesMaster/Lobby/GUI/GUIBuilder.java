package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Player.PlayerGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * class used to create the guis
 */
public class GUIBuilder { //todo gui to customize gui in game
    
    /**
     * builds all GUIs at once
     */
    public static void buildAll() {
        for(GUIType type : GUIType.values()) build(type);
    }
    
    /**
     * create the guis based on the config values and saves it
     *
     * @param type type of gui to build
     */
    public static void build(GUIType type) {
        GUI gui = new GUI(type, type.getSize(), type.getDisplayName());
        
        ConfigurationSection sec = type.getConfigSection();
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection decorations = sec.getConfigurationSection("decorations." + i);
                ItemStack decorItem = GUIItem.getItem(decorations.getString("item"));
                gui.addDecoration(decorItem, i);
            } catch (NullPointerException e) {
                continue;
            }
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection buttons = sec.getConfigurationSection("buttons." + i);
                ItemStack buttonItem = GUIItem.getItem(buttons.getString("item"));
                GUIButton button = new GUIButton() {
                    @Override
                    public boolean onLeftClick(Player player) {
                        return runActions(buttons.getConfigurationSection("actions.left-click"), player);
                    }
                    
                    @Override
                    public boolean onRightClick(Player player) {
                        return runActions(buttons.getConfigurationSection("actions.right-click"), player);
                    }
                };
                gui.addButton(buttonItem, i, button);
            } catch (NullPointerException e) {
                continue;
            }
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection toggles = sec.getConfigurationSection("toggles." + i);
                ItemStack untoggledItem = GUIItem.getItem(toggles.getString("untoggled.item"));
                ItemStack toggledItem = GUIItem.getItem(toggles.getString("toggled.item"));
                int finalI = i;
                GUIButton untogggled = new GUIButton() {
                    @Override
                    public boolean onLeftClick(Player player) {
                        if (toggles.getBoolean("untoggled.actions.left-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("untoggled.actions.left-click"), player);
                    }
                    
                    @Override
                    public boolean onRightClick(Player player) {
                        if (toggles.getBoolean("untoggled.actions.right-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("untoggled.actions.right-click"), player);
                    }
                };
                GUIButton toggled = new GUIButton() {
                    @Override
                    public boolean onLeftClick(Player player) {
                        if (toggles.getBoolean("toggled.actions.left-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("toggled.actions.left-click"), player);
                    }
                    
                    @Override
                    public boolean onRightClick(Player player) {
                        if (toggles.getBoolean("toggled.actions.right-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("toggled.actions.right-click"), player);
                    }
                };
                gui.addToggle(untoggledItem, toggledItem, i, untogggled, toggled);
            } catch (NullPointerException e) {
                continue;
            }
        }
        gui.save();
    }
    
    /**
     * helper method to perform the button actions in the build method
     *
     * @param section config section for gui
     * @param player  player to perform actions
     */
    private static boolean runActions(ConfigurationSection section, Player player) {//todo actions for music, gadgets, etc.
        
        if (section == null) return false;
        
        if (section.getString("command") != null) player.performCommand(section.getString("command"));
        
        if (section.getString("teleport") != null) {
            String[] component = section.getString("teleport").split(" ");
            player.teleport(new Location(Bukkit.getWorld(component[0]),
                    Float.parseFloat(component[1]),
                    Float.parseFloat(component[2]),
                    Float.parseFloat(component[3]),
                    Float.parseFloat(component[4]),
                    Float.parseFloat(component[5])));
        }
        
        if (section.getString("gui") != null) {
            PlayerGUI pgui = new PlayerGUI(player);
            pgui.open(GUIType.valueOf(section.getString("gui")));
        }
        return true;
    }
    
}
