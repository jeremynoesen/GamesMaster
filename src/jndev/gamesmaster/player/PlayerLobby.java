package jndev.gamesmaster.player;

import jndev.gamesmaster.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLobby {
    
    private Player player;
    
    public PlayerLobby(Player player) {
        this.player = player;
    }
    
    /**
     * @return true if the player is in the lobby
     */
    public boolean isInLobby() {
        Location l = player.getLocation();
        return Region.getRegion("lobby").containsLocation(l);
    }
    
}
