package me.Jeremaster101.GamesMaster.Region;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIItem;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class RegionListener implements Listener {


    private final RegionHandler rg = new RegionHandler();
    private final LobbyInventory li = new LobbyInventory();
    private final GUIItem guiitem = new GUIItem();
    private final LobbyHandler lh = new LobbyHandler();

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            public void run() {
                if (lh.isGamesWorld(p.getWorld()))
                    rg.fixGamemode(p);
                if (rg.isInRegion(p.getLocation(), "lobby"))
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            li.loadLobbyInv(p);
                        }
                    }.runTaskLater(GamesMaster.plugin, 5);
            }
        }.runTaskLater(GamesMaster.plugin, 10);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();

        if (lh.isGamesWorld(to.getWorld())) {

            File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                    p.getUniqueId().toString() + ".yml");
            YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);

            new BukkitRunnable() {
                public void run() {

                    String region = rg.getRegion(p);

                                if (cur.get("current") != null) {
                                    if (!region.equals("lobby") && cur.get("current").toString().equals("lobby")) {
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
                                            }.runTaskLater(GamesMaster.plugin, 5);
                                        } else
                                            rg.loadRegion(p, region);
                                    } else
                                        rg.loadRegion(p, region);

                                    if (to.getWorld() != from.getWorld()) {
                                        rg.fixGamemode(p);
                                        if (rg.equals("lobby") && cur.get("current").toString().equals("lobby"))
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    li.loadLobbyInv(p);
                                                }
                                            }.runTaskLater(GamesMaster.plugin, 3);
                                    }

                                } else {
                                    rg.loadRegion(p, region);
                                }
                            }

            }.runTaskLater(GamesMaster.plugin, 3);
        }

        if (lh.isGamesWorld(from.getWorld())) {

            File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                    p.getUniqueId().toString() + ".yml");
            YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);

            new BukkitRunnable() {
                public void run() {
                    String region = rg.getRegion(p);

                    String curleave = "no command";
                    String arleave = "no command";
                    if (cur.get("current") != null && RegionConfig.getConfig().get("regions." + cur.get("current")
                            .toString() + ".leave") != null)
                        curleave = RegionConfig.getConfig().get("regions." + cur.get("current").toString() +
                                ".leave").toString();
                    if (RegionConfig.getConfig().get("regions." + region + ".leave") != null)
                        arleave = RegionConfig.getConfig().get("regions." + region + ".leave").toString();

                    if (!curleave.equals(arleave) && !curleave.equals("no command")) {
                        if (!lh.isGamesWorld(to.getWorld())) {

                            p.performCommand(curleave);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.teleport(to);
                                }
                            }.runTaskLater(GamesMaster.plugin, 10);
                        } else {
                            String finalCurleave = curleave;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.performCommand(finalCurleave);
                                }
                            }.runTaskLater(GamesMaster.plugin, 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.teleport(to);
                                }
                            }.runTaskLater(GamesMaster.plugin, 20);
                        }
                    }
                }
            }.runTaskLater(GamesMaster.plugin, 1);
        }
    }
}
