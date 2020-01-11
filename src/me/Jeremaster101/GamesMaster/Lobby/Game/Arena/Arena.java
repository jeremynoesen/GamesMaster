package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class Arena {//todo finish this class
    
    private final LobbyHandler lh = new LobbyHandler();
    private String game;
    private String arena;
    private Player player;
    
    public Arena(Player player, String game, String arena) {
        this.player = player;
        if (game != null && arena != null) {
            this.game = game;
            this.arena = arena;
            if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) == null) {
                ArenaConfig.getConfig().set(game + "." + arena + ".enabled", false);
                ArenaConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_GAME_ADDED.replace("$GAME$", game));
            }
        }
    }
    
    public static Arena getArena(Player player, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            return new Arena(player, game, arena);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
            return null;
        }
    }
    
    boolean exists() {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                player.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
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
        
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            p.sendMessage(Message.ERROR_ARENA_EXISTS);
            return;
        }
        
        if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
            p.sendMessage(Message.ERROR_NO_GAMES);
        } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
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
            
            ArenaConfig.getConfig().set(game + "." + arena + ".enabled", enabled);
            ArenaConfig.getConfig().set(game + "." + arena + ".join", command);
            ArenaConfig.getConfig().set(game + "." + arena + ".mapname", mapname);
            ArenaConfig.getConfig().set(game + "." + arena + ".priority", priority);
            ArenaConfig.getConfig().set(game + "." + arena + ".hidden", hidden);
            ArenaConfig.saveConfig();
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
    
    public void setPriority(int priority) {//todo remove arena number replace with priority
        
        if (exists()) {
            //finish
            player.sendMessage(Message.SUCCESS_UPDATED_ARENA_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
        }
    }
    
    public void setEnabled(boolean enabled) {
        
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
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null &&
                ArenaConfig.getConfig().getConfigurationSection(game).getKeys(false).size() == 1) {
            ArenaConfig.getConfig().set(game, null);
            player.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            ArenaConfig.getConfig().set(game + "." + arena, null);
            player.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
}
