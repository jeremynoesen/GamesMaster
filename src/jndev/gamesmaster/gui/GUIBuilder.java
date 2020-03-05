package jndev.gamesmaster.gui;

import jndev.gamesmaster.Message;
import jndev.gamesmaster.gui.item.GUIItemBuilder;
import jndev.gamesmaster.player.GMPlayer;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * class used to create the guis
 */
public class GUIBuilder { //todo gui to customize gui in game
    //todo assign button actions to items in config to simplify config and make customizing easier
    
    public static void buildAll(Player player) {
        for(GUIType type : GUIType.values()) {
            build(type, player);
        }
    }
    
    /**
     * @param type type to build
     * @param player player to perform actions
     */
    public static void build(GUIType type, Player player) {
        GUI gui = new GUI(type, type.getSize(), type.getDisplayName(), player);
        
        ConfigurationSection sec = type.getConfigSection();
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection decorations = sec.getConfigurationSection("decorations." + i);
                ItemStack decorItem = GUIItemBuilder.getItem(decorations.getString("item"));
                gui.addDecoration(decorItem, i);
            } catch (NullPointerException e) {
                continue;
            }
        }
        
        for (int i = 0; i < gui.getSize().getInteger() - 1; i++) {
            try {
                ConfigurationSection buttons = sec.getConfigurationSection("buttons." + i);
                ItemStack buttonItem = GUIItemBuilder.getItem(buttons.getString("item"));
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
                ItemStack untoggledItem = GUIItemBuilder.getItem(toggles.getString("untoggled.item"));
                ItemStack toggledItem = GUIItemBuilder.getItem(toggles.getString("toggled.item"));
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
                if(player != null) updateToggle(toggles.getConfigurationSection("untoggled.actions"), player, gui, finalI);
            } catch (NullPointerException e) {
                continue;
            }
        }
    }
    
    /**
     * helper method to perform the button actions in the build method
     *
     * @param section config section for gui
     * @param player  player to perform actions
     */
    private static boolean runActions(ConfigurationSection section, Player player) {
        
        if (section == null) return false;
        
        GMPlayer gmplayer = GMPlayer.getGMPlayer(player);
        
        if (section.getString("command") != null) player.performCommand(section.getString("command"));
        
        if (section.getString("gui") != null) gmplayer.openGUI(GUIType.valueOf(section.getString("gui")));
        
        if (section.getString("teleport") != null) {
            String[] component = section.getString("teleport").split(" ");
            player.teleport(new Location(Bukkit.getWorld(component[0]),
                    Float.parseFloat(component[1]),
                    Float.parseFloat(component[2]),
                    Float.parseFloat(component[3]),
                    Float.parseFloat(component[4]),
                    Float.parseFloat(component[5])));
        }
        
        if(section.getString("preference") != null) {
            String[] component = section.getString("preference").split(" ");
            String preference = component[0];
            boolean enabled = Boolean.parseBoolean(component[1]);
            //todo finish
            

        }
        
        if(section.getString("gadget") != null) {
        
        }
        
        if(section.getString("music") != null) {
        
        }
        
        return true;
    }
    
    private static void updateToggle(ConfigurationSection section, Player player, GUI gui, int slot) {
        ConfigurationSection left = section.getConfigurationSection("left-click");
        ConfigurationSection right = section.getConfigurationSection("right-click");
        GMPlayer gmplayer = GMPlayer.getGMPlayer(player);
        checkPreferences(gui, slot, left, gmplayer);
        checkPreferences(gui, slot, right, gmplayer);
        //todo add check for locked gadgets and music
    
    }
    
    private static void checkPreferences(GUI gui, int slot, ConfigurationSection actionButton, GMPlayer gmplayer) {
        if(actionButton.getString("preference") != null) {
            String[] component = actionButton.getString("preference").split(" ");
            String preference = component[0];
            boolean enabled = Boolean.parseBoolean(component[1]);
        
            if(preference.equals("effects-visible")) {
                if(enabled) {
                    if(!gmplayer.getPreferences().areEffectsVisible()) gui.toggle(slot);
                } else {
                    if(gmplayer.getPreferences().areEffectsVisible()) gui.toggle(slot);
                }
            }
    
            if(preference.equals("public-music")) {
                if(enabled) {
                    if(!gmplayer.getPreferences().isMusicPublic()) gui.toggle(slot);
                } else {
                    if(gmplayer.getPreferences().isMusicPublic()) gui.toggle(slot);
                }
            }
    
            if(preference.equals("gadget-interaction")) {
                if(enabled) {
                    if(!gmplayer.getPreferences().canGadgetInteract()) gui.toggle(slot);
                } else {
                    if(gmplayer.getPreferences().canGadgetInteract()) gui.toggle(slot);
                }
            }
    
            if(preference.equals("players-visible")) {
                if(enabled) {
                    if(!gmplayer.getPreferences().arePlayersVisible()) gui.toggle(slot);
                } else {
                    if(gmplayer.getPreferences().areEffectsVisible()) gui.toggle(slot);
                }
            }
        }
    }
    
    private static ItemStack game(String displayname, String icon, String description) { //todo use for making game ui
        ItemStack s = new ItemStack(Material.getMaterial(icon.toUpperCase()));
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(Message.colorize(displayname));
        ArrayList<String> lore = new ArrayList<>();
        String wrapped = WordUtils.wrap(description, 24, "\n", true);
        lore.add("");
        for (String line : wrapped.split("\n")) lore.add(ChatColor.WHITE + line);
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to join");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }
    
}
