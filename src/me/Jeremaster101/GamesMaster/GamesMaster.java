package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Command.CommandConfig;
import me.Jeremaster101.GamesMaster.Command.CommandExec;
import me.Jeremaster101.GamesMaster.Command.CommandListener;
import me.Jeremaster101.GamesMaster.Command.CommandTabComplete;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIInteract;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GamesMaster extends JavaPlugin{

    public static GamesMaster plugin;
    private final Permission adminPerms = new Permission("gamemaster.admin");
    private final Permission mapPerms = new Permission("gamemaster.map");

    private Message msg = null;

    public static MessageConfig messageConfig = new MessageConfig();

    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        InventoryConfig.initialize();
        RegionConfig.initialize();
        LobbyConfig.initialize();
        GameConfig.initialize();
        ArenaConfig.initialize();
        CommandConfig.initialize();
        messageConfig.saveDefaultConfig();

        msg = new Message();


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
        //pm.registerEvents(new AngryBird(), plugin);
        //pm.registerEvents(new FloatyBoat(), plugin);
        pm.registerEvents(new Stormbreaker(), plugin);
        pm.addPermission(adminPerms);
        pm.addPermission(mapPerms);
        getCommand("gamesmaster").setExecutor(new CommandExec());
        getCommand("gamesmaster").setTabCompleter(new CommandTabComplete());

        LobbyProtect lp = new LobbyProtect();
        lp.cleanLobby();

        GamesMaster.plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);

        //pm.registerEvents(new Testing(), plugin);

    }

    public void onDisable() {
        plugin = null;
    }
}
