package jeremynoesen.gamesmaster.gadget.gadgets;

import jeremynoesen.gamesmaster.GamesMaster;
import jeremynoesen.gamesmaster.gadget.GadgetItem;
import jeremynoesen.gamesmaster.lobby.LobbyHandler;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * grappling hook gadget
 */
public class GrapplingHook implements Listener {
    
    private final LobbyHandler lh = new LobbyHandler();
    
    /**
     * listens for when a player shoots the grappling hook
     *
     * @param e projectile launch event
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player && e.getEntity() instanceof FishHook) {
            Player p = (Player) e.getEntity().getShooter();
            FishHook f = (FishHook) e.getEntity();
            if (p.getInventory().getItemInMainHand().equals(GadgetItem.GRAPPLING_HOOK.getItem())) {
                f.setBounce(false);
                Arrow a = p.launchProjectile(Arrow.class);
                a.setVelocity(a.getVelocity().multiply(0.75));
                a.setCustomName("grapplingHook");
                a.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                a.setPassenger(f);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (f.isDead()) {
                            a.remove();
                            this.cancel();
                        }
                        if (a.isDead()) {
                            f.remove();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(GamesMaster.getInstance(), 0, 1);
            }
        }
    }
    
    /**
     * listens for when the hook hits an object
     *
     * @param e projectile hit event
     */
    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow && e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals("grapplingHook")
                && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player && e.getHitEntity() == null) {
            Player p = (Player) e.getEntity().getShooter();
            
            for (Player all : lh.getPlayersInLobby()) {
                all.stopSound(Sound.ENTITY_ARROW_HIT);
            }
            
            if (e.getHitEntity() == null && e.getHitBlock().getType() == Material.BARRIER) {
                e.getEntity().remove();
                return;
            }
            
            new BukkitRunnable() {
                int i = 0;
                
                @Override
                public void run() {
                    
                    if (p.getWorld().equals(e.getEntity().getWorld())) {
                        i++;
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_TNT_PRIMED, 0.35f, 2);
                        p.setVelocity(e.getEntity().getLocation().subtract(p.getEyeLocation()).toVector().normalize());
                        if ((p.getLocation().add(0, 1, 0).distance(e.getEntity().getLocation()) <= 2) || i > 60 || e.getEntity().isDead()) {
                            this.cancel();
                            if (!e.getEntity().isDead()) e.getEntity().remove();
                        }
                    } else if (!p.getWorld().equals(e.getEntity().getWorld())) {
                        this.cancel();
                        if (!e.getEntity().isDead()) e.getEntity().remove();
                    }
                }
            }.runTaskTimer(GamesMaster.getInstance(), 0, 1);
            p.getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_TRIDENT_HIT_GROUND, 3, 1.5f);
        }
    }
    
    /**
     * prevent hook from harming players
     *
     * @param e entity damage by entity event
     */
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getDamager().getCustomName() != null && e.getDamager().getCustomName().equals("grapplingHook")) {
            e.setCancelled(true);
            e.getDamager().getWorld().playSound(e.getDamager().getLocation(), Sound.ITEM_TRIDENT_HIT_GROUND, 2, 1);
        }
    }
}
