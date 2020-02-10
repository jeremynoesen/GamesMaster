package me.Jeremaster101.GamesMaster.Region;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.GUI.OldGUIItem;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.GMPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RegionListener implements Listener {


    private final RegionHandler rg = new RegionHandler();
    private final LobbyInventory li = new LobbyInventory();
    private final OldGUIItem guiitem = new OldGUIItem();
    private final LobbyHandler lh = new LobbyHandler();
    
    private ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            public void run() {
                if (lh.isGamesWorld(p.getWorld())) {
                    rg.fixGamemode(p);
                    //todo teleport to games spawn (set in a config)
                }
                if (rg.isInRegion(p.getLocation(), "lobby"))
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            li.loadLobbyInv(p);
                        }
                    }.runTaskLater(GamesMaster.getInstance(), 5);
            }
        }.runTaskLater(GamesMaster.getInstance(), 10);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();

        if (lh.isGamesWorld(to.getWorld())) {
    
            GMPlayer gmplayer = GMPlayer.getPlayerData(p);
    
            new BukkitRunnable() {
                public void run() {

                    String region = rg.getRegion(p);

                                if (gmplayer.getCurrentRegion() != null) {
                                    if (!region.equals("lobby") && gmplayer.getCurrentRegion().equals("lobby")) {
                                        if ((p.getInventory().getItem(2) != null && p.getInventory().getItem(2)
                                                .equals(guiitem.gameUI())) ||
                                                (p.getInventory().getItem(6) != null && p.getInventory().getItem(6)
                                                        .equals(guiitem.cosmeticUI()))) {
                                            li.clearLobbyInv(p);
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    rg.loadRegion(p, region);
                                                }
                                            }.runTaskLater(GamesMaster.getInstance(), 5);
                                        } else
                                            rg.loadRegion(p, region);
                                    } else
                                        rg.loadRegion(p, region);

                                    if (to.getWorld() != from.getWorld()) {
                                        rg.fixGamemode(p);
                                        if (rg.equals("lobby") && gmplayer.getCurrentRegion().equals("lobby"))
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    li.loadLobbyInv(p);
                                                }
                                            }.runTaskLater(GamesMaster.getInstance(), 3);
                                    }

                                } else {
                                    rg.loadRegion(p, region);
                                }
                            }

            }.runTaskLater(GamesMaster.getInstance(), 3);
        }

        if (lh.isGamesWorld(from.getWorld())) {
    
            GMPlayer gmplayer = GMPlayer.getPlayerData(p);
    
            new BukkitRunnable() {
                public void run() {
                    String region = rg.getRegion(p);

                    String curleave = "null";
                    String arleave = "null";
                    if (gmplayer.getCurrentRegion() != null && regionConfig.getConfig().get(
                            gmplayer.getCurrentRegion() + ".leave") != null)
                        curleave = regionConfig.getConfig().get(gmplayer.getCurrentRegion() +
                                ".leave").toString();
                    if (regionConfig.getConfig().get(region + ".leave") != null)
                        arleave = regionConfig.getConfig().get(region + ".leave").toString();

                    if (!curleave.equals(arleave) && !curleave.equals("null")) {
                        if (!lh.isGamesWorld(to.getWorld())) {

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