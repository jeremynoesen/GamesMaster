package jeremynoesen.gamesmaster.lobby;

import jeremynoesen.gamesmaster.GamesMaster;
import jeremynoesen.gamesmaster.player.GMPlayer;
import jeremynoesen.gamesmaster.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyProtect implements Listener {
    
    private final Sound[] records = new Sound[]{
            Sound.MUSIC_DISC_13,
            Sound.MUSIC_DISC_BLOCKS,
            Sound.MUSIC_DISC_CAT,
            Sound.MUSIC_DISC_CHIRP,
            Sound.MUSIC_DISC_FAR,
            Sound.MUSIC_DISC_MALL,
            Sound.MUSIC_DISC_MELLOHI,
            Sound.MUSIC_DISC_STAL,
            Sound.MUSIC_DISC_STRAD,
            Sound.MUSIC_DISC_WAIT,
            Sound.MUSIC_DISC_WARD
    };
    
    public void cleanLobby() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    for (Entity e : LobbyHandler.gamesWorld().getEntities()) {
                        if (Region.getRegion("lobby").containsLocation(e.getLocation()) &&
                                !(e instanceof Player) && !(e instanceof ArmorStand) && !(e instanceof ItemFrame))
                            e.remove();
                        if (Region.getRegion("lobby").containsLocation(e.getLocation()) && e instanceof Player)
                            for (Sound s : records) ((Player) e).stopSound(s);
                    }
                } catch (Exception ignored) {
                }
                Bukkit.getScheduler().cancelTasks(GamesMaster.getInstance());
            }
        }.runTaskLater(GamesMaster.getInstance(), 2);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        if (e.getClickedBlock() != null && gmp.getLobbyHandler().isInLobby()) {
            if (a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) {
                if (e.getClickedBlock() != null && p.getGameMode() != GameMode.CREATIVE)
                    e.setCancelled(true);
            }
            if (a == Action.LEFT_CLICK_BLOCK) {
                if (p.getGameMode() != GameMode.CREATIVE)
                    e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onHandSwap(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        if (gmp.getLobbyHandler().isInLobby()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        if (gmp.getLobbyHandler().isInLobby()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            GMPlayer gmp = GMPlayer.getGMPlayer(p);
            if (gmp.getLobbyHandler().isInLobby()) {
                e.setCancelled(true);
            }
        }
    }
}
