package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class Arena {//todo finish this class
    
    private final LobbyHandler lh = new LobbyHandler();
    private String game;
    private String arena;
    private Player player;
    
    private static ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    private static ConfigManager gameConfig = Configs.getConfig(ConfigType.GAME);
    
    
    public Arena(Player player, String game, String arena) {
        this.player = player;
        if (game != null && arena != null) {
            this.game = game;
            this.arena = arena;
            if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) == null) {
                arenaConfig.getConfig().set(game + "." + arena + ".enabled", false);
                arenaConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_GAME_ADDED.replace("$GAME$", game));
            }
        }
    }
    
    public static Arena getArena(Player player, String game, String arena) {
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            return new Arena(player, game, arena);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
            return null;
        }
    }
    
    boolean exists() {
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (gameConfig.getConfig().getConfigurationSection("games") == null ||
                    gameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                player.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!gameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                player.sendMessage(Message.ERROR_GAME_LIST);
            } else {
                
                return true;
            }
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
        return false;
    }
    
    public void addArena(Player p, String game, String arena, String mapname, String join, int priority,
                         boolean enabled, boolean hidden) {
        
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            p.sendMessage(Message.ERROR_ARENA_EXISTS);
            return;
        }
        
        if (gameConfig.getConfig().getConfigurationSection("games") == null ||
                gameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
            p.sendMessage(Message.ERROR_NO_GAMES);
        } else if (!gameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
            p.sendMessage(Message.ERROR_GAME_LIST);
        } else {
            
            
            String command;
            
            if (join.equalsIgnoreCase("here")) {
                if (lh.isGamesWorld(p.getWorld())) {
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float pitch = p.getLocation().getPitch();
                    float yaw = p.getLocation().getYaw();
                    command = x + " " + y + " " + z + " " + yaw + " " + pitch;
                } else {
                    p.sendMessage(Message.ERROR_WORLD);
                    return;
                }
            } else {//todo read from alias config
                command = join.replace("_", " ")
                        .replace("skywars", "sw")
                        .replace("blockparty", "bp")
                        .replace("hideandseek", "has")
                        .replace("mgsplegg", "sp")
                        .replace("mgskywars", "sw")
                        .replace("speedbuilders", "sb")
                        .replace("hungergames", "hg")
                        .replace("survivalgames", "hg")
                        .replace("sg", "hg");
            }
            
            arenaConfig.getConfig().set(game + "." + arena + ".enabled", enabled);
            arenaConfig.getConfig().set(game + "." + arena + ".join", command);
            arenaConfig.getConfig().set(game + "." + arena + ".mapname", mapname);
            arenaConfig.getConfig().set(game + "." + arena + ".priority", priority);
            arenaConfig.getConfig().set(game + "." + arena + ".hidden", hidden);
            arenaConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_ARENA_ADDED.replace("$ARENA", game + " " + arena + " - " + mapname.replace("_", " ")));
        }
    }
    
    public void setName(Player p, String game, String arena, String mapname) { //todo finish
        
        if (exists()) {
            //finish
            p.sendMessage(Message.SUCCESS_UPDATED_ARENA_NAME.replace("$NAME$", mapname));
        }
    }
    
    public void setJoin(String join) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_UPDATED_ARENA_JOIN.replace("$JOIN$", join));
        }
    }
    
    public void setPriority(int priority) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_UPDATED_ARENA_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
        }
    }
    
    public void setEnabled(boolean enabled) { //todo only when all conditions are met
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_UPDATED_ARENA_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
        }
    }
    
    public void setHidden(boolean hidden) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_UPDATED_ARENA_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
        }
    }
    
    public void remove() {
        if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null &&
                arenaConfig.getConfig().getConfigurationSection(game).getKeys(false).size() == 1) {
            arenaConfig.getConfig().set(game, null);
            player.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            arenaConfig.saveConfig();
        } else if (arenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            arenaConfig.getConfig().set(game + "." + arena, null);
            player.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            arenaConfig.saveConfig();
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
}
