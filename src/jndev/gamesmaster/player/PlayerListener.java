package jndev.gamesmaster.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (GMPlayer.getPlayer(e.getPlayer()) == null)
            new GMPlayer(e.getPlayer());
    }
    
}
