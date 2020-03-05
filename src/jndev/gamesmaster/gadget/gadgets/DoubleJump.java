package jndev.gamesmaster.gadget.gadgets;

import jndev.gamesmaster.lobby.LobbyHandler;
import jndev.gamesmaster.player.GMPlayer;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class DoubleJump implements Listener {

    LobbyHandler lh = new LobbyHandler();

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        if (gmp.isInLobby()) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(Math.abs(p.getLocation().getDirection()
                        .multiply(0.9).add(new Vector(0, 0.1, 0)).getY())));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
                p.setExp(0f);
                p.getWorld().spawnParticle(Particle.END_ROD, p.getLocation(), 40, 0, 0, 0, 0.5);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        if (gmp.isInLobby()) {
            p.setFallDistance(0);
            if (p.isOnGround()) {
                p.setAllowFlight(true);
                p.setExp(1.0f);
            }
        }
    }
}
