package jeremynoesen.gamesmaster;

import jeremynoesen.gamesmaster.command.CommandExec;
import jeremynoesen.gamesmaster.command.CommandListener;
import jeremynoesen.gamesmaster.command.CommandTabComplete;
import jeremynoesen.gamesmaster.inventory.InventoryFixer;
import jeremynoesen.gamesmaster.lobby.LobbyProtect;
import jeremynoesen.gamesmaster.region.RegionListener;
import jeremynoesen.gamesmaster.config.Config;
import jeremynoesen.gamesmaster.config.ConfigType;
import jeremynoesen.gamesmaster.gui.item.GUIItemBuilder;
import jeremynoesen.gamesmaster.gadget.gadgets.Stormbreaker;
import jeremynoesen.gamesmaster.gadget.UseGadget;
import jeremynoesen.gamesmaster.gadget.gadgets.DoubleJump;
import jeremynoesen.gamesmaster.gui.GUIBuilder;
import jeremynoesen.gamesmaster.gui.GUIListener;
import jeremynoesen.gamesmaster.gui.GUIType;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import jeremynoesen.gamesmaster.gadget.gadgets.GrapplingHook;
import jeremynoesen.gamesmaster.gadget.gadgets.PaintballGun;
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
        
        Config.getConfig(ConfigType.GUI).saveDefaultConfig();
        Config.getConfig(ConfigType.LOBBY).saveDefaultConfig();
        Config.getConfig(ConfigType.GAME).saveDefaultConfig();
        Config.getConfig(ConfigType.ARENA).saveDefaultConfig();
        Config.getConfig(ConfigType.INVENTORY).saveDefaultConfig();
        Config.getConfig(ConfigType.REGION).saveDefaultConfig();
        Config.getConfig(ConfigType.COMMAND).saveDefaultConfig();
        Config.getConfig(ConfigType.MESSAGE).saveDefaultConfig();
        
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
        pm.registerEvents(new Testing(), plugin);
        
        pm.addPermission(adminPerms);
        
        getCommand("gamesmaster").setExecutor(new CommandExec());
        getCommand("gamesmaster").setTabCompleter(new CommandTabComplete());
        
        LobbyProtect lp = new LobbyProtect();
        lp.cleanLobby();
    
        //testing
        GUIItemBuilder.craftItems();
        //GUIBuilder.buildAll();
        GUIBuilder.buildPublicGUI(GUIType.COSMETICS); //todo possibly build all GUIs in one method
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