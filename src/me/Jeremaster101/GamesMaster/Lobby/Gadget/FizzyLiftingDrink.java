package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FizzyLiftingDrink {

    private final LobbyHandler lh = new LobbyHandler();

    public ItemStack fizzyLiftingDrink() {
        ItemStack s = new ItemStack(Material.POTION, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Fizzy Lifting Drink");
        ((PotionMeta) sm).setColor(Color.fromRGB(0, 140, 255));
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack fizzyLiftingDrinkReload() {
        ItemStack s = new ItemStack(Material.GLASS_BOTTLE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Fizzy Lifting Drink");
        s.setItemMeta(sm);
        return s;
    }

    void useFizzyLiftingDrink(Player p) {
        Random random = new Random();
        lh.setGadget(p, fizzyLiftingDrinkReload());
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3, true, false));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1000, 0, true, false));
                        }
                    }.runTaskLater(GamesMaster.plugin, 100);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (p.isOnGround()) {
                                p.removePotionEffect(PotionEffectType.SLOW_FALLING);
                                this.cancel();
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (lh.activeGadget(p, fizzyLiftingDrinkReload())) {
                                            lh.setGadget(p, fizzyLiftingDrink());
                                            p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 2);
                                        }
                                    }
                                }.runTaskLater(GamesMaster.plugin, 40);
                            }
                        }
                    }.runTaskTimer(GamesMaster.plugin, 2, 1);

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
        }.runTaskTimer(GamesMaster.plugin, 2, 1);
    }
}
