package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Stormbreaker implements Listener {

    private final LobbyHandler lh = new LobbyHandler();

    private static void sendLightningPacket(Player p, Location l) {
        Class<?> light = getNMSClass("EntityLightning");
        try {
            Constructor<?> constu =
                    light
                            .getConstructor(getNMSClass("World"),
                                    double.class, double.class,
                                    double.class, boolean.class, boolean.class);
            Object wh = p.getWorld().getClass().getMethod("getHandle").invoke(p.getWorld());
            Object lighobj = constu.newInstance(wh, l.getX(), l.getY(), l.getZ(), false, false);

            Object obj =
                    getNMSClass("PacketPlayOutSpawnEntityWeather")
                            .getConstructor(getNMSClass("Entity")).newInstance(lighobj);

            sendPacket(p, obj);
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemStack stormbreaker() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Stormbreaker");
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack stormbreakerReload() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Stormbreaker");
        s.setItemMeta(sm);
        return s;
    }

    public void useLightningSmash(Player p) {

        p.setGlowing(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000, 3, true, false));
        lh.setGadget(p, stormbreakerReload());
        p.setVelocity(p.getVelocity().setY(2.5));
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 10, 0);

        for (Player all : lh.getPlayersInLobby()) {
            all.setPlayerWeather(WeatherType.DOWNFALL);
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                p.getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getLocation().add(0, 1, 0), 5, 0, 0
                        , 0, 0.33);
                if (lh.activeGadget(p, stormbreakerReload())) {
                    if (!p.isGlowing() || !lh.isInLobby(p))
                        this.cancel();
                }
            }
        }.runTaskTimer(GamesMaster.getInstance(), 2, 2);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.isInLobby(p)) {

                    if (p.getInventory().getItemInMainHand() == null || !p.getInventory().getItemInMainHand().equals(stormbreakerReload())) {
                        this.cancel();
                        p.removePotionEffect(PotionEffectType.SPEED);
                        p.setGlowing(false);

                        for (Player all : lh.getPlayersInLobby()) {
                            all.setPlayerWeather(WeatherType.CLEAR);
                        }

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (lh.isInLobby(p))
                                    if (lh.activeGadget(p, stormbreakerReload())) {
                                        lh.setGadget(p, stormbreaker());
                                    }
                            }
                        }.runTaskLater(GamesMaster.getInstance(), 200);
                    }

                    if (p.isOnGround()) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                        p.setGlowing(false);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 255, true, false));

                        for (Player all : lh.getPlayersInLobby()) {
                            sendLightningPacket(all, p.getLocation());
                        }

                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getLocation(), 30, 0, 0, 0, 0.25);
                        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation(), 50, 0, 0, 0, 0.8);

                        p.getWorld().playEffect(p.getLocation().add(0, -1, 0),
                                Effect.STEP_SOUND, p.getLocation().getBlock().getRelative(0, -1, 0).getType());
                        p.getWorld().playEffect(p.getLocation().add(1, -1, 0),
                                Effect.STEP_SOUND, p.getLocation().getBlock().getRelative(1, -1, 0).getType());
                        p.getWorld().playEffect(p.getLocation().add(-1, -1, 0),
                                Effect.STEP_SOUND, p.getLocation().getBlock().getRelative(-1, -1, 0).getType());
                        p.getWorld().playEffect(p.getLocation().add(0, -1, 1),
                                Effect.STEP_SOUND, p.getLocation().getBlock().getRelative(0, -1, 1).getType());
                        p.getWorld().playEffect(p.getLocation().add(0, -1, -1),
                                Effect.STEP_SOUND, p.getLocation().getBlock().getRelative(0, -1, -1).getType());

                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 10, 0);
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
                        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.2f, 0);

                        for (Entity near : p.getNearbyEntities(10, 10, 10)) {
                            if (near.getLocation().distanceSquared(p.getLocation()) <= 100 &&
                                    near.getLocation().distanceSquared(p.getLocation()) > 25) {
                                Location mid = new Location(p.getWorld(),
                                        (p.getLocation().getX() + near.getLocation().getX()) / 2,
                                        (p.getLocation().getY() + near.getLocation().getY()) / 2,
                                        (p.getLocation().getZ() + near.getLocation().getZ()) / 2);

                                Location playermidmid = new Location(p.getWorld(),
                                        (p.getLocation().getX() + mid.getX()) / 2,
                                        (p.getLocation().getY() + mid.getY()) / 2,
                                        (p.getLocation().getZ() + mid.getZ()) / 2);

                                Location midnearmid = new Location(p.getWorld(),
                                        (near.getLocation().getX() + mid.getX()) / 2,
                                        (near.getLocation().getY() + mid.getY()) / 2,
                                        (near.getLocation().getZ() + mid.getZ()) / 2);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (lh.isInLobby(p))
                                            p.getWorld().playEffect(playermidmid.add(0, -1, 0),
                                                    Effect.STEP_SOUND, playermidmid.getBlock().getRelative(0, -1, 0).getType());
                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, playermidmid, 10, 0, 0, 0,
                                                1);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 1);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        for (Player all : lh.getPlayersInLobby()) {
                                            //sendLightningPacket(all, mid);//todo make stormbreaker branch out to other mobs
                                        }

                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, mid, 10, 0, 0, 0, 0.25);


                                        //p.getWorld().playSound(mid, Sound.ENTITY_LIGHTNING_BOLT_IMPACT
                                        //        , 5, 1);

                                        if (lh.isInLobby(p))
                                            p.getWorld().playEffect(mid.add(0, -1, 0),
                                                    Effect.STEP_SOUND, mid.getBlock().getRelative(0, -1, 0).getType());
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 2);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (lh.isInLobby(p))
                                            p.getWorld().playEffect(midnearmid.add(0, -1, 0),
                                                    Effect.STEP_SOUND, midnearmid.getBlock().getRelative(0, -1, 0).getType());
                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, midnearmid, 10, 0, 0, 0, 0.25);

                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 3);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        for (Player all : lh.getPlayersInLobby()) {
                                            sendLightningPacket(all, near.getLocation());
                                        }

                                        p.getWorld().playSound(near.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT//todo remove, sound might be playing twice
                                                , 5, 1);

                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, near.getLocation(), 20, 0, 0, 0,
                                                1);

                                        if (lh.isInLobby(p)) {
                                            p.getWorld().playEffect(near.getLocation().add(0, -1, 0),
                                                    Effect.STEP_SOUND,
                                                    near.getLocation().getBlock().getRelative(0, -1, 0).getType());
                                            near.setVelocity(near.getLocation().subtract(mid).toVector().normalize().multiply(4).setY(0.5));
                                        }
                                        if (near instanceof Player)
                                            ((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                                                    20, 255, true,
                                                    false));
                                        near.setGlowing(true);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 4);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        near.setGlowing(false);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 6);

                            } else if (near.getLocation().distanceSquared(p.getLocation()) <= 25) {

                                Location mid = new Location(p.getWorld(),
                                        (p.getLocation().getX() + near.getLocation().getX()) / 2,
                                        (p.getLocation().getY() + near.getLocation().getY()) / 2,
                                        (p.getLocation().getZ() + near.getLocation().getZ()) / 2);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.getWorld().playEffect(mid.add(0, -1, 0),
                                                Effect.STEP_SOUND, mid.getBlock().getRelative(0, -1, 0).getType());
                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, mid, 10, 0, 0, 0,
                                                1);

                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 1);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        for (Player all : lh.getPlayersInLobby()) {
                                            sendLightningPacket(all, near.getLocation());
                                        }

                                        p.getWorld().playSound(near.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT
                                                , 5, 1);

                                        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, near.getLocation(), 20, 0, 0, 0,
                                                1);


                                        if (lh.isInLobby(p)) {

                                            p.getWorld().playEffect(near.getLocation().add(0, -1, 0),
                                                    Effect.STEP_SOUND,
                                                    near.getLocation().getBlock().getRelative(0, -1, 0).getType());
                                            near.setVelocity(near.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(6).setY(0.5));
                                        }
                                        if (near instanceof Player)
                                            ((Player) near).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                                                    20, 255, true,
                                                    false));
                                        near.setGlowing(true);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 2);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        near.setGlowing(false);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 4);
                            }
                        }

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (Player all : lh.getPlayersInLobby()) {
                                    all.setPlayerWeather(WeatherType.CLEAR);
                                }

                            }
                        }.runTaskLater(GamesMaster.getInstance(), 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (lh.isInLobby(p))
                                    if (lh.activeGadget(p, stormbreakerReload())) {
                                        lh.setGadget(p, stormbreaker());
                                    }
                            }
                        }.runTaskLater(GamesMaster.getInstance(), 200);

                        this.cancel();
                    }
                } else this.cancel();
            }
        }.runTaskTimer(GamesMaster.getInstance(), 5, 1);
    }

    public void useFly(Player p) {

        lh.setGadget(p, stormbreakerReload());
        p.setGlowing(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000, 3, true, false));
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 0);
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_THROW, 8, 0);
        final boolean[] letGo = {false};
        for (int charge = 1; charge <= 100; charge++) {
            int finalCharge = charge;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (lh.isInLobby(p)) {
                        if (p.getInventory().getItemInMainHand().equals(stormbreakerReload()) && !letGo[0]) {
                            p.setVelocity(p.getLocation().getDirection().normalize().multiply((300 - finalCharge)).multiply(0.004));
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 3,
                                    (float) (finalCharge / 100));
                            p.getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getLocation().add(0, 1, 0), 5, 0, 0
                                    , 0, 0.33);
                        } else {
                            letGo[0] = true;
                            if (lh.isInLobby(p)) {
                                p.removePotionEffect(PotionEffectType.SPEED);
                                p.setGlowing(false);
                            }
                        }
                    }
                }
            }.runTaskLater(GamesMaster.getInstance(), charge);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.isInLobby(p)) {
                    p.removePotionEffect(PotionEffectType.SPEED);
                    p.setGlowing(false);
                }
            }
        }.runTaskLater(GamesMaster.getInstance(), 101);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.isInLobby(p))
                    if (lh.activeGadget(p, stormbreakerReload())) {
                        lh.setGadget(p, stormbreaker());
                    }
            }
        }.runTaskLater(GamesMaster.getInstance(), 250);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (lh.isGamesWorld(e.getFrom())) {
            e.getPlayer().setGlowing(false);
            e.getPlayer().resetPlayerWeather();

            if (e.getPlayer().getInventory().getItem(4) != null && e.getPlayer().getInventory().getItem(4).equals(stormbreakerReload())) {
                for (Player all : lh.getPlayersInLobby()) {
                    if (lh.isInLobby(all)) {
                        all.setPlayerWeather(WeatherType.CLEAR);
                    }
                }
            }

        }
    }
}
