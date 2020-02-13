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
            try {
                ConfigurationSection decorations = sec.getConfigurationSection("decorations." + i);
                ItemStack decorItem = new GUIItem(Material.getMaterial(decorations.getString("material")),
                        decorations.getString("display-name"), decorations.getStringList("lore")).toItemStack();
                gui.addDecoration(decorItem, i);
            } catch (NullPointerException e) {
                continue;
            }
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection buttons = sec.getConfigurationSection("buttons." + i);
                ItemStack buttonItem = new GUIItem(Material.getMaterial(buttons.getString("material")),
                        buttons.getString("display-name"), buttons.getStringList("lore")).toItemStack();
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
                ItemStack untoggledItem = new GUIItem(Material.getMaterial(toggles.getString("untoggled.material")),
                        toggles.getString("untoggled.display-name"), toggles.getStringList("untoggled.lore")).toItemStack();
                ItemStack toggledItem = new GUIItem(Material.getMaterial(toggles.getString("toggled.material")),
                        toggles.getString("toggled.display-name"), toggles.getStringList("toggledlore")).toItemStack();
                int finalI = i;
                GUIButton untogggled = new GUIButton() {
                    @Override
                    public boolean onLeftClick(Player player) {
                        if(toggles.getBoolean("untoggled.actions.left-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("untoggled.actions.left-click"), player);
                    }
        
                    @Override
                    public boolean onRightClick(Player player) {
                        if(toggles.getBoolean("untoggled.actions.right-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("untoggled.actions.right-click"), player);
                    }
                };
                GUIButton toggled = new GUIButton() {
                    @Override
                    public boolean onLeftClick(Player player) {
                        if(toggles.getBoolean("toggled.actions.left-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("toggled.actions.left-click"), player);
                    }
        
                    @Override
                    public boolean onRightClick(Player player) {
                        if(toggles.getBoolean("toggled.actions.right-click.toggle")) gui.toggle(finalI);
                        return runActions(toggles.getConfigurationSection("toggled.actions.right-click"), player);
                    }
                };
                gui.addToggle(untoggledItem, toggledItem, i, untogggled, toggled);
            } catch (NullPointerException e) {
                continue;
            }
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
            //todo get gui by string or guitype
        }
            return true;
    }
    
}
