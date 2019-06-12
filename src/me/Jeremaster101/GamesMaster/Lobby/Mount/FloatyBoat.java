package me.Jeremaster101.GamesMaster.Lobby.Mount;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FloatyBoat implements Listener {

    LobbyHandler lh = new LobbyHandler();

    private int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }

    @SuppressWarnings("deprecation")
    public void createBoat(Player p) {
        Boat b = p.getWorld().spawn(p.getLocation(), Boat.class);
        b.setCustomName("floatyboat");
        b.setPassenger(p);
        ArmorStand as = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
        as.setPassenger(b);
        as.setVisible(false);
        as.setSmall(true);
        as.setCollidable(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        Block bl = as.getLocation().getBlock().getRelative(x, 0, z);
                        BlockData bd = bl.getBlockData();
                        if (bl.getType() == Material.AIR) {
                            for (Player all : lh.getPlayersInLobby()) {
                                all.sendBlockChange(bl.getLocation(), Material.WATER, (byte) 1);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        all.sendBlockChange(bl.getLocation(), bd);
                                    }
                                }.runTaskLater(GamesMaster.plugin, 10 + getRandom(0, 20));
                            }
                        }
                    }
                }
                as.setVelocity(p.getLocation().getDirection().multiply(0.5));
                b.getWorld().spawnParticle(Particle.WATER_SPLASH, b.getLocation(), 40, 0, 0, 0, 30);
                if (b.isDead() || as.isDead()) this.cancel();
                if (b.getPassengers().size() < 1) {
                    this.cancel();
                    b.remove();
                    as.remove();
                }
            }
        }.runTaskTimer(GamesMaster.plugin, 2, 2);
    }

    @EventHandler
    public void onSneak(PlayerToggleFlightEvent e) {
        createBoat(e.getPlayer());
    }
}
