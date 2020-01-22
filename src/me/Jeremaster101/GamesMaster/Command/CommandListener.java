package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Region.RegionHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandListener implements Listener {

    private final RegionHandler ah = new RegionHandler();
    private final LobbyInventory li = new LobbyInventory();
    private final LobbyHandler lh = new LobbyHandler();
    
    ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    
    
    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if (!e.getMessage().contains("/gamesmaster")) { //todo read from command alias config
            e.setMessage(e.getMessage()
                    .replace("/skywars", "/sw")
                    .replace("/blockparty", "/bp")
                    .replace("/hideandseek", "/has")
                    .replace("/mgsplegg", "/sp")
                    .replace("/mgskywars", "/sw")
                    .replace("/speedbuilders", "/sb")
                    .replace("/hungergames", "/hg")
                    .replace("/survivalgames", "/hg")
                    .replace("/sg", "/hg"));
        }//add other game commands later on

        if (e.getMessage().equals("/sp join") || e.getMessage().equals("/sw join") || e.getMessage().equals("/has join")) {
            e.setCancelled(true);
            p.sendMessage(Message.ERROR_ARENA_DISABLED);
            return;
        }

        try {
            for (String s : arenaConfig.getConfig().getConfigurationSection("arenas").getKeys(false)) {
                for (int i = 1; i <= 5; i++) {
                    if (arenaConfig.getConfig().get(s + "." + i + ".enabled") == null) continue;
                    if (arenaConfig.getConfig().get(s + "." + i + ".enabled").toString().equals("false")) {
                        if (e.getMessage().equals("/" + arenaConfig.getConfig().get(s + "." + i + ".join").toString())) {
                            e.setCancelled(true);
                            p.sendMessage(Message.ERROR_ARENA_DISABLED);
                            return;
                        }
                    } else if (!ah.isInRegion(p.getLocation(), "lobby")) {
                        if (e.getMessage().equals("/" + arenaConfig.getConfig().get(s + "." + i + ".join").toString())) {
                            if (lh.isGamesWorld(p.getWorld())) {
                                e.setCancelled(true);
                                p.sendMessage(Message.ERROR_NOT_IN_LOBBY);
                                return;
                            } else {
                                e.setCancelled(true);

                                p.performCommand("warp games");

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        li.clearLobbyInv(p);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 10);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.performCommand(e.getMessage().replace("/", ""));
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 15);
                            }
                        }
                    } else {
                        if (e.getMessage().equals("/" + arenaConfig.getConfig().get(s + "." + i + ".join").toString())) {
                            e.setCancelled(true);
                            li.clearLobbyInv(p);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.performCommand(e.getMessage().replace("/", ""));
                                }
                            }.runTaskLater(GamesMaster.getInstance(), 5);
                        }
                    }
                }
            }
        } catch (Exception ee) {
            return;
        }

        try {
            for (String blockedcmd : Configs.getConfig(ConfigType.COMMAND).getConfig().getStringList("blocked-cmds")) {
                for (String word : e.getMessage().split(" ")) {
                    if (word.equals("/" + blockedcmd) && !p.isOp() && lh.isGamesWorld(p.getWorld())) {
                        e.setCancelled(true);
                        p.sendMessage(Message.ERROR_CMD_BLOCKED);
                        break;
                    }
                }
            }
        } catch (Exception ee) {
            return;
        }
    }
}
