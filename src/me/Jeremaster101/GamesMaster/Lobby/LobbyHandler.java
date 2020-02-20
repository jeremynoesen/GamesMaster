package me.Jeremaster101.GamesMaster.Lobby;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LobbyHandler {
    
    public static ArrayList<Player> getPlayersInLobby() {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        for (Player all : gamesWorld().getPlayers()) {
            GMPlayer gmp = GMPlayer.getPlayer(all);
            if (gmp.isInLobby()) {
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
