package jeremynoesen.gamesmaster.gadget.gadgets;

import jeremynoesen.gamesmaster.GamesMaster;
import jeremynoesen.gamesmaster.gadget.GadgetItem;
import jeremynoesen.gamesmaster.lobby.LobbyHandler;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * fizzy lifting drink gadget
 */
public class FizzyLiftingDrink {
    
    private static final LobbyHandler lh = new LobbyHandler();
    
    /**
     * use the fizzy lifting drink gadget
     *
     * @param p player to use it
     */
    public static void use(Player p) {
        Random random = new Random();
        lh.setGadget(p, GadgetItem.FIZZY_LIFTING_DRINK_RELOAD.getItem());
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3, true, false));
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1000, 0, true, false));
            }
        }.runTaskLater(GamesMaster.getInstance(), 100);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnGround()) {
                    p.removePotionEffect(PotionEffectType.SLOW_FALLING);
                    this.cancel();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (lh.activeGadget(p, GadgetItem.FIZZY_LIFTING_DRINK_RELOAD.getItem())) {
                                lh.setGadget(p, GadgetItem.FIZZY_LIFTING_DRINK.getItem());
                                p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 2);
                            }
                        }
                    }.runTaskLater(GamesMaster.getInstance(), 40);
                }
            }
        }.runTaskTimer(GamesMaster.getInstance(), 2, 1);
        
        new BukkitRunnable() {
            int end = 0;
            
            @Override
            public void run() {
                p.getWorld().spawnParticle(Particle.BUBBLE_POP, p.getLocation().subtract(0, 0.25, 0), 6, 0.35, 0.25,
                        0.35, 0);
                end++;
                if (p.isOnGround()) this.cancel();
                
                if (!lh.isInLobby(p)) {
                    this.cancel();
                    p.removePotionEffect(PotionEffectType.LEVITATION);
                }
            }
        }.runTaskTimer(GamesMaster.getInstance(), 2, 1);
    }
}
