package jndev.gamesmaster.region;

import jndev.gamesmaster.GamesMaster;
import jndev.gamesmaster.lobby.LobbyHandler;
import jndev.gamesmaster.player.GMPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * class that is responsible for automating region loading
 */
public class RegionListener implements Listener {
    
    /**
     * load inventory and gamemode on join
     *
     * @param e PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        new BukkitRunnable() {
            public void run() {
                if (LobbyHandler.isGamesWorld(p.getWorld())) {
                    gmp.getRegionHandler().fixGamemode();
                    //todo teleport to games spawn (set in a config)
                }
                if (gmp.getLobbyHandler().isInLobby())
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            //todo load lobby inv
                        }
                    }.runTaskLater(GamesMaster.getInstance(), 5);
            }
        }.runTaskLater(GamesMaster.getInstance(), 10);
    }
    
    /**
     * load and save inventories on teleport
     *
     * @param e PlayerTeleportEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();
        GMPlayer gmplayer = GMPlayer.getGMPlayer(p);
        Region region = gmplayer.getRegionHandler().getCurrentRegion();
        Region loaded = gmplayer.getRegionHandler().getLoadedRegion();
        
        if (LobbyHandler.isGamesWorld(to.getWorld())) {
            
            new BukkitRunnable() {
                public void run() {
                    
                    if (loaded != null && !loaded.getName().equals("lobby") && region.getName().equals("lobby")) {
                        //todo load lobby items
                    } else {
                        gmplayer.getRegionHandler().loadRegion(region);
                    }
                }
                
            }.runTaskLater(GamesMaster.getInstance(), 3);
        }
        
        if (LobbyHandler.isGamesWorld(from.getWorld())) {
            
            new BukkitRunnable() {
                public void run() {
                    
                    String curleave = "null";
                    String arleave = "null";
                    if (loaded != null && loaded.getLeave() != null)
                        curleave = loaded.getLeave();
                    if (region.getLeave() != null)
                        arleave = region.getLeave();
                    
                    if (!curleave.equals(arleave) && !curleave.equals("null")) {
                        if (!LobbyHandler.isGamesWorld(to.getWorld())) {
                            
                            p.performCommand(curleave);
                            
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.teleport(to);
                                }
                            }.runTaskLater(GamesMaster.getInstance(), 10);
                        } else {
                            String finalCurleave = curleave;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.performCommand(finalCurleave);
                                }
                            }.runTaskLater(GamesMaster.getInstance(), 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.teleport(to);
                                }
                            }.runTaskLater(GamesMaster.getInstance(), 20);
                        }
                    }
                }
            }.runTaskLater(GamesMaster.getInstance(), 1);
        }
    }
}

//todo allow worlds to be included in gamesmaster