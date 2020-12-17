package jeremynoesen.gamesmaster.command;

import jeremynoesen.gamesmaster.lobby.LobbyHandler;
import jeremynoesen.gamesmaster.lobby.LobbyInventory;
import jeremynoesen.gamesmaster.config.Config;
import jeremynoesen.gamesmaster.config.ConfigManager;
import jeremynoesen.gamesmaster.config.ConfigType;
import jeremynoesen.gamesmaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Region.RegionHandler;
import jeremynoesen.gamesmaster.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandListener implements Listener {

    private final RegionHandler ah = new RegionHandler();
    private final LobbyInventory li = new LobbyInventory();
    private final LobbyHandler lh = new LobbyHandler();
    
    ConfigManager arenaConfig = Config.getConfig(ConfigType.ARENA);
    
    
    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if (!e.getMessage().contains("/gamesmaster")) { //todo read from command alias config
            //todo replace alias
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
            for (String blockedcmd : Config.getConfig(ConfigType.COMMAND).getConfig().getStringList("blocked-cmds")) {
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
