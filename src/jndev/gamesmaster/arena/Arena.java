package jndev.gamesmaster.arena;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.Message;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.game.Game;
import jndev.gamesmaster.lobby.LobbyHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Arena {//todo finish this class
    
    public static HashMap<String, Arena> arenas = new HashMap<>();
    private static ConfigManager arenaConfig = Config.getConfig(ConfigType.ARENA);
    private static ConfigManager gameConfig = Config.getConfig(ConfigType.GAME);
    private String game;
    private String arena;
    
    public Arena(Game game, String arenaName) {
    
    }
    
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
                if (LobbyHandler.isGamesWorld(p.getWorld())) {
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
            } else {
                //todo read from alias config
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
    
    public void setName(Player p, String game, String arena, String mapname) {
        
        if (exists()) {
            //finish
            p.sendMessage(Message.SUCCESS_SET_ARENA_NAME.replace("$NAME$", mapname));
        }
    }
    
    public void setJoin(String join) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_SET_ARENA_JOIN.replace("$JOIN$", join));
        }
    }
    
    public void setPriority(int priority) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_SET_ARENA_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
        }
    }
    
    public void setEnabled(boolean enabled) { //todo only when all conditions are met
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_SET_ARENA_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
        }
    }
    
    public void setHidden(boolean hidden) {
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_SET_ARENA_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
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
