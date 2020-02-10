package me.Jeremaster101.GamesMaster.Lobby.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * class used to create the guis
 */
public class GUIBuilder { //todo test this using a test class
    
    /**
     * create the guis based on the config values and saves it
     *
     * @param type type of gui to build
     */
    public static void build(GUIType type) {
        GUI gui = new GUI(type, type.getSize(), type.getDisplayName());
        ConfigurationSection sec = type.getConfigSection();
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            ConfigurationSection decorations = sec.getConfigurationSection("decorations." + i);
            if (decorations == null) continue;
            ItemStack decorItem = new GUIItem(Material.valueOf(decorations.getString("material")),
                    decorations.getString("display-name"), sec.getStringList("lore")).toItemStack();
            gui.addDecoration(decorItem, i);
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            ConfigurationSection buttons = sec.getConfigurationSection("buttons." + i);
            if (buttons == null) continue;
            ItemStack buttonItem = new GUIItem(Material.valueOf(buttons.getString("material")),
                    buttons.getString("display-name"), sec.getStringList("lore")).toItemStack();
            GUIButton button = new GUIButton() {
                @Override
                public boolean onLeftClick(Player player) {
                    return runActions(buttons, player);
                }
                
                @Override
                public boolean onRightClick(Player player) {
                    return runActions(buttons, player);
                }
            };
            gui.addButton(buttonItem, i, button);
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            ConfigurationSection toggles = sec.getConfigurationSection("toggles." + i);
            if (toggles == null) continue;
            ItemStack untoggledItem = new GUIItem(Material.valueOf(toggles.getString("untoggled.material")),
                    toggles.getString("untoggled.display-name"), sec.getStringList("untoggled.lore")).toItemStack();
            ItemStack toggledItem = new GUIItem(Material.valueOf(toggles.getString("toggled.material")),
                    toggles.getString("toggled.display-name"), sec.getStringList("toggledlore")).toItemStack();
            GUIButton untogggled = new GUIButton() {
                @Override
                public boolean onLeftClick(Player player) {
                    return runActions(toggles, player);
                }
                
                @Override
                public boolean onRightClick(Player player) {
                    return runActions(toggles, player);
                }
            };
            GUIButton toggled = new GUIButton() {
                @Override
                public boolean onLeftClick(Player player) {
                    return runActions(toggles, player);
                }
                
                @Override
                public boolean onRightClick(Player player) {
                    return runActions(toggles, player);
                }
            };
            gui.addToggle(untoggledItem, toggledItem, i, untogggled, toggled);
        }
        //todo gui to customize gui in game
        gui.save();
    }
    
    /**
     * helper method to perform the button actions in the build method
     *
     * @param section config section for gui
     * @param player  player to perform actions
     */
    private static boolean runActions(ConfigurationSection section, Player player) {
        ConfigurationSection actions = section.getConfigurationSection("actions");
        String command = actions.getString("command");
        String loc = actions.getString("teleport");
        String gui = actions.getString("gui");
        boolean hightPitchClick = actions.getBoolean("high-pitch");
        
        if (command != null) player.performCommand(command);
        
        if (loc != null) {
            String[] component = loc.split(" ");
            player.teleport(new Location(Bukkit.getWorld(component[0]),
                    Float.parseFloat(component[1]),
                    Float.parseFloat(component[2]),
                    Float.parseFloat(component[3]),
                    Float.parseFloat(component[4]),
                    Float.parseFloat(component[5])));
        }
        
        if (gui != null) {
            //todo get gui by string or guitype
        }
        
        return hightPitchClick;
    }
    
}
