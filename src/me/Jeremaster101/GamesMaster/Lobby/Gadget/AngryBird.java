package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class AngryBird implements Listener {

    private int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }

    @SuppressWarnings("deprecation")
    public void splitBlueBird(Player p) {

    }

    @SuppressWarnings("deprecation")
    public void launchBlueBird(Player p) {
        Arrow a = p.launchProjectile(Arrow.class);
        Parrot c = p.getWorld().spawn(p.getEyeLocation(),
                Parrot.class);
        c.setVariant(Parrot.Variant.CYAN);
        c.setInvulnerable(true);
        a.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        c.teleport(c.getLocation().setDirection(p.getLocation().getDirection()));
        a.setPassenger(c);
        a.setCustomName("bluebirdbig");

        //hide arrow

        //make own method so players can click to separate
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!c.isDead()) {
                    c.getWorld().createExplosion(c.getLocation(), 0, false);
                    for (int i = 0; i < 3; i++) {
                        Parrot c1 = c.getWorld().spawn(c.getLocation(), Parrot.class);
                        Arrow a1 = c.launchProjectile(Arrow.class);
                        c1.setBaby();
                        c1.setInvulnerable(true);
                        c1.teleport(c.getLocation().setDirection(c.getLocation().getDirection()));
                        a1.setPassenger(c1);
                        a1.setVelocity(c.getVelocity().add(new Vector(getRandom(-10, 10), getRandom(-10, 10), getRandom(-10,
                                0)).multiply(0.1)));
                        a1.setCustomName("bluebirdsmall");
                        a1.setShooter(p);
                        a1.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                        //hide arrow
                    }
                    c.remove();
                    a.remove();
                }
            }
        }.runTaskLater(GamesMaster.plugin, 20);
    }

    @EventHandler
    public void onProjHit(ProjectileHitEvent e) {

    }

    @EventHandler
    public void onTest(PlayerToggleSneakEvent e) {
        launchBlueBird(e.getPlayer());
    }
}
