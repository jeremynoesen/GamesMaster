package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Command.CommandConfig;
//import me.Jeremaster101.GamesMaster.Command.CommandExec;
import me.Jeremaster101.GamesMaster.Command.CommandListener;
import me.Jeremaster101.GamesMaster.Command.CommandTabComplete;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIConfig;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIInteract;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIListener;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyProtect;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Message.MessageConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryFixer;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import me.Jeremaster101.GamesMaster.Region.RegionListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GamesMaster extends JavaPlugin{

    public static GamesMaster plugin;
    private final Permission adminPerms = new Permission("gamesmaster.admin");

    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();
    
        GUIConfig.saveDefaultConfig();
        LobbyConfig.saveDefaultConfig();
        GameConfig.saveDefaultConfig();
        ArenaConfig.saveDefaultConfig();
        InventoryConfig.saveDefaultConfig();
        RegionConfig.saveDefaultConfig();
        CommandConfig.saveDefaultConfig();
        MessageConfig.saveDefaultConfig();

        Message msg = new Message();

        GamesMaster.plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Misc(), plugin);
        pm.registerEvents(new RegionListener(), plugin);
        pm.registerEvents(new GUIInteract(), plugin);
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

        //getCommand("gamesmaster").setExecutor(new CommandExec());
        getCommand("gamesmaster").setTabCompleter(new CommandTabComplete());

        LobbyProtect lp = new LobbyProtect();
        lp.cleanLobby();
    }

    public void onDisable() {
        plugin = null;
    }
    
    public static GamesMaster getInstance() {
        return plugin;
    }
}

//todo particles for players

//todo add moderation tools

//todo comment methods using javadoc style

//todo add more config options

//todo resettable configs

//todo portals

//todo random join command, join and leave commands for games

//todo vault support for gagdet buying