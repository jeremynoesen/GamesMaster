package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryFixer implements Listener { //this class is to combat PerWorldInventory issues

    private final InventoryHandler ih = new InventoryHandler();
    private final LobbyHandler lh = new LobbyHandler();

    @EventHandler
    public void onModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (GamesMaster.getInstance().getConfig().getBoolean("pwi-gamemode-inv-fix")) {
            if (lh.isGamesWorld(p.getWorld())) {
                ih.copyInv(p);
            }
        }
    }

    @EventHandler
    public void onLeaveGamesWorld(PlayerTeleportEvent e) {
        if (GamesMaster.getInstance().getConfig().getBoolean("pwi-gamemode-inv-fix")) {
            Player p = e.getPlayer();
            Location from = e.getFrom();
            Location to = e.getTo();
            GameMode mode = GameMode.valueOf((RegionConfig.getConfig().getString("default.gamemode")));

            if (lh.isGamesWorld(to.getWorld()) && to.getWorld() != from.getWorld()) {
                p.setGameMode(GameMode.valueOf((RegionConfig.getConfig().getString("default.gamemode"))));
            }

            if (lh.isGamesWorld(from.getWorld()) &&
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
