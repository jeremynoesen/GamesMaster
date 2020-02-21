package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * this class is used to combat PWI gamemode inventory issues
 */
public class InventoryFixer implements Listener {
    
    private static ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    
    /**
     * will refresh inventory on gamemode change
     *
     * @param e PlayerGameModeChangeEvent
     */
    @EventHandler
    public void onModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getPlayer(p);
        if (GamesMaster.getInstance().getConfig().getBoolean("pwi-gamemode-inv-fix")) {
            if (LobbyHandler.isGamesWorld(p.getWorld())) {
                gmp.getInventory(null).reload();
            }
        }
    }
    
    /**
     * ensures the previous inventory is saved or loaded before entering or leaving the games world
     *
     * @param e PlayerTeleportEvent
     */
    @EventHandler
    public void onLeaveGamesWorld(PlayerTeleportEvent e) {
        if (GamesMaster.getInstance().getConfig().getBoolean("pwi-gamemode-inv-fix")) {
            Player p = e.getPlayer();
            Location from = e.getFrom();
            Location to = e.getTo();
            GameMode mode = GameMode.valueOf((regionConfig.getConfig().getString("default.gamemode")));

            if (LobbyHandler.isGamesWorld(to.getWorld()) && to.getWorld() != from.getWorld()) {
                p.setGameMode(GameMode.valueOf((regionConfig.getConfig().getString("default.gamemode"))));
            }

            if (LobbyHandler.isGamesWorld(from.getWorld()) &&
                    from.getWorld() != to.getWorld() && p.getGameMode() != mode) {
                e.setCancelled(true);
                p.setGameMode(mode);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(to);
                    }
                }.runTaskLater(GamesMaster.getInstance(), 2);
            }
        }
    }
}
