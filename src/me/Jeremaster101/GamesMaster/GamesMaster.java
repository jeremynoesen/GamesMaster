package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Command.CommandExec;
import me.Jeremaster101.GamesMaster.Command.CommandListener;
import me.Jeremaster101.GamesMaster.Command.CommandTabComplete;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIBuilder;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIItem;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIListener;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIType;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import me.Jeremaster101.GamesMaster.Lobby.LobbyProtect;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryFixer;
import me.Jeremaster101.GamesMaster.Region.RegionListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GamesMaster extends JavaPlugin {
    
    public static GamesMaster plugin;
    private final Permission adminPerms = new Permission("gamesmaster.admin"); //todo more permissions
    
    public static GamesMaster getInstance() {
        return plugin;
    }
    
    /**
     * register plugin events, make items, build guis, save default configs, register permissions, and load messages
     */
    public void onEnable() {
        plugin = this;
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        Configs.getConfig(ConfigType.GUI).saveDefaultConfig();
        Configs.getConfig(ConfigType.LOBBY).saveDefaultConfig();
        Configs.getConfig(ConfigType.GAME).saveDefaultConfig();
        Configs.getConfig(ConfigType.ARENA).saveDefaultConfig();
        Configs.getConfig(ConfigType.INVENTORY).saveDefaultConfig();
        Configs.getConfig(ConfigType.REGION).saveDefaultConfig();
        Configs.getConfig(ConfigType.COMMAND).saveDefaultConfig();
        Configs.getConfig(ConfigType.MESSAGE).saveDefaultConfig();
        
        Message.reloadMessages();
        
        Message msg = new Message();
        
        GamesMaster.plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);
        
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(new Misc(), plugin);
        pm.registerEvents(new RegionListener(), plugin);
        pm.registerEvents(new CommandListener(), plugin);
        pm.registerEvents(new PaintballGun(), plugin);
        pm.registerEvents(new DoubleJump(), plugin);
        pm.registerEvents(new UseGadget(), plugin);
        pm.registerEvents(new LobbyProtect(), plugin);
        pm.registerEvents(new GrapplingHook(), plugin);
        pm.registerEvents(new InventoryFixer(), plugin);
        pm.registerEvents(new Stormbreaker(), plugin);
        pm.registerEvents(new GUIListener(), plugin);
        //pm.registerEvents(new AngryBird(), plugin); //todo add more gadgets
        //pm.registerEvents(new FloatyBoat(), plugin); //todo add mounts
        pm.registerEvents(new Testing(), plugin);
        
        pm.addPermission(adminPerms);
        
        getCommand("gamesmaster").setExecutor(new CommandExec());
        getCommand("gamesmaster").setTabCompleter(new CommandTabComplete());
        
        LobbyProtect lp = new LobbyProtect();
        lp.cleanLobby();
    
        //testing
        GUIItem.craftItems();
        //GUIBuilder.buildAll();
        GUIBuilder.build(GUIType.COSMETICS); //todo possibly build all GUIs in one method
    }
    
    /**
     * disable plugin
     */
    public void onDisable() {
        plugin = null;
    }
}

//todo particles for players

//todo add moderation tools ! ! !

//todo comment methods using javadoc style

//todo add more config options

//todo resettable configs

//todo portals

//todo random join command, join and leave commands for games

//todo vault support for gagdet buying

//todo put things static again to avoid making multiple instances of unchanging classes