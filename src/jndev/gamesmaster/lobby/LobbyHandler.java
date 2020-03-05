package jndev.gamesmaster.lobby;

import jndev.gamesmaster.GamesMaster;
import jndev.gamesmaster.player.GMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LobbyHandler {//todo move some of these to other classes
    
    public static ArrayList<Player> getPlayersInLobby() {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        for (Player all : gamesWorld().getPlayers()) {
            GMPlayer gmp = GMPlayer.getGMPlayer(all);
            if (gmp.getLobbyHandler().isInLobby()) {
                playerArrayList.add(all);
            }
        }
        return playerArrayList;
    }
    
    public static boolean isGamesWorld(World w) { //todo multi world support
        return w.equals(gamesWorld());
    }
    
    public static World gamesWorld() {
        return Bukkit.getWorld(GamesMaster.getInstance().getConfig().get("games-world").toString());
    }
    
}
