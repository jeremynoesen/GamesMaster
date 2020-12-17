package jeremynoesen.gamesmaster.arena;

import jeremynoesen.gamesmaster.Message;
import jeremynoesen.gamesmaster.game.Game;
import jeremynoesen.gamesmaster.lobby.LobbyHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Arena {
    
    public static HashMap<Game, HashMap<String, Arena>> arenas = new HashMap<>();
    private Game game;
    private String name;
    
    public Arena(Game game, String name) {
        this.game = game;
        this.name = name;
    }
    
    public static Arena getArena(Game game, String name) {
        return arenas.get(game).get(name);
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
    
    public void setMapName(String mapname) {
    
    }
    
    public void setJoin(String join) {

    }
    
    public void setEnabled(boolean enabled) { //todo only when all conditions are met
    
    }
    
    public void remove() {
        arenas.get(game).remove(name);
    }
}
