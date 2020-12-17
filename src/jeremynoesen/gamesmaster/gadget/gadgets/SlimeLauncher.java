package jeremynoesen.gamesmaster.gadget.gadgets;

import jeremynoesen.gamesmaster.GamesMaster;
import jeremynoesen.gamesmaster.gadget.GadgetItem;
import jeremynoesen.gamesmaster.lobby.LobbyHandler;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * slime launcher gadget
 */
public class SlimeLauncher {
    
    private static LobbyHandler lh = new LobbyHandler();
    
    /**
     * launches a slime and handles its physics and effects
     *
     * @param p player to use slime launcher
     */
    public static void use(Player p) {
        lh.setGadget(p, GadgetItem.SLIME_LAUNCHER_RELOAD.getItem());
        Slime s = p.getLocation().getWorld()
                .spawn(p.getEyeLocation().add(p.getLocation().getDirection()).subtract(0, 0.25, 0), Slime.class);
        s.setSize(1);
        Location loc = s.getLocation();
        loc.setDirection(p.getLocation().toVector());
        s.teleport(loc);
        s.setInvulnerable(true);
        s.setCollidable(false);
        s.setVelocity(p.getLocation().getDirection().multiply(1.75).add(new Vector(0, 0.3, 0)));
        s.getWorld().playSound(s.getLocation(), Sound.ENTITY_SLIME_JUMP, 2, 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                s.setFallDistance(0);
                for (Entity pl : s.getNearbyEntities(0.1, 0.1, 0.1)) {
                    if (pl != p && pl instanceof Player && ((Player) pl).getGameMode() != GameMode.CREATIVE) {
                        pl.setVelocity(s.getVelocity().setY(0.66));
                        s.getWorld().spawnParticle(Particle.SLIME,
                                s.getLocation().add(0, s.getHeight() / 2, 0), 40, 0, 0, 0, 30);
                        s.getWorld().playSound(s.getLocation(), Sound.ENTITY_SLIME_SQUISH, 2, 2);
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                        s.remove();
                        this.cancel();
                    } else break;
                }
                if (s.getLocation().subtract(0, 0.1, 0).getBlock().getType() != Material.AIR ||
                        s.getLocation().add(0, s.getHeight() + 0.1, 0).getBlock().getType() != Material.AIR ||
                        s.getLocation().subtract((s.getWidth() / 2) + 0.1, 0, 0).getBlock().getType() != Material.AIR ||
                        s.getLocation().add((s.getWidth() / 2) + 0.1, 0, 0).getBlock().getType() != Material.AIR ||
                        s.getLocation().subtract(0, 0, (s.getWidth() / 2) + 0.1).getBlock().getType() != Material.AIR ||
                        s.getLocation().add(0, 0, (s.getWidth() / 2) + 0.1).getBlock().getType() != Material.AIR) {
                    s.getWorld().spawnParticle(Particle.SLIME,
                            s.getLocation().add(0, s.getHeight() / 2, 0), 40, 0, 0, 0, 30);
                    s.getWorld().playSound(s.getLocation(), Sound.ENTITY_SLIME_SQUISH, 2, 2);
                    s.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(GamesMaster.getInstance(), 0, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.activeGadget(p, GadgetItem.SLIME_LAUNCHER_RELOAD.getItem())) {
                    lh.setGadget(p, GadgetItem.SLIME_LAUNCHER.getItem());
                }
            }
        }.runTaskLater(GamesMaster.getInstance(), 30);
    }
}
