package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.util.Vector;

public class Misc implements Listener {//todo make config vals

    LobbyHandler lh = new LobbyHandler();

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent e) {
        if (lh.isGamesWorld(e.getEgg().getWorld())) {
            e.setHatching(false);
        }
    }

    @EventHandler
    public void projectileHit(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity player = e.getEntity();
        if (lh.isGamesWorld(damager.getWorld()) && (player instanceof Player) && (damager instanceof Snowball || damager instanceof Egg)) {
            if (!lh.isInLobby((Player) player)) {
                ((Player) player).damage(0.01D);
                player.setVelocity(damager.getVelocity().multiply(0.4D).add(new Vector(0, 0.25, 0)));
            }
        }
    }
}
