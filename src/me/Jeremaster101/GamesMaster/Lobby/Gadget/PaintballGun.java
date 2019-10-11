package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class PaintballGun implements Listener {

    private final LobbyHandler lh = new LobbyHandler();

    public ItemStack paintballGun() {
        ItemStack s = new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Paintball Gun");
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack paintballGunReload() {
        ItemStack s = new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Paintball Gun");
        s.setItemMeta(sm);
        return s;
    }

    private int getRandomRainbowColor() {
        List<Integer> allData = Arrays.asList(14, 1, 4, 5, 11, 10, 2, 9, 3, 6);
        Random random = new Random();
        return allData.get(random.nextInt(allData.size()));
    }

    void usePaintballGun(Player p) {
        Snowball s = p.launchProjectile(Snowball.class);
        s.setCustomName("paintballGun");
        lh.setGadget(p, paintballGunReload());
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.activeGadget(p, paintballGunReload())) {
                    lh.setGadget(p, paintballGun());
                }
            }
        }.runTaskLater(GamesMaster.getInstance(), 5);
    }

    private void paintballHitBlock(Block hitBlock, Location hitLoc) {
        Location hitBlockLoc = hitBlock.getLocation().add(0.5, 0.5, 0.5);
        if (hitLoc.distance(hitBlockLoc) >= 1.4 && hitLoc.getBlock().getType() == Material.AIR) {
            hitLoc = new Location(hitLoc.getWorld(), (hitLoc.getX() + hitBlockLoc.getX()) / 2, (hitLoc.getY() +
                    hitBlockLoc.getY()) / 2, (hitLoc.getZ() + hitBlockLoc.getZ()) / 2);
        }
        int randomData = getRandomRainbowColor();
        int s;
        if (hitLoc.getBlock().getRelative(1, 0, 0).getType() == Material.AIR &&
                hitLoc.getBlock().getRelative(-1, 0, 0).getType() == Material.AIR &&
                hitLoc.getBlock().getRelative(0, 1, 0).getType() == Material.AIR &&
                hitLoc.getBlock().getRelative(0, -1, 0).getType() == Material.AIR &&
                hitLoc.getBlock().getRelative(0, 0, 1).getType() == Material.AIR &&
                hitLoc.getBlock().getRelative(0, 0, -1).getType() == Material.AIR) {
            s = 1;
        } else {
            s = 0;
        }
        int effect = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= 2) {
                        Block bl;
                        if (s == 1) {
                            bl = hitBlock.getRelative(x, y, z);
                        } else {
                            bl = hitLoc.getBlock().getRelative(x, y, z);
                        }
                        if (bl.getType() != Material.AIR &&
                                bl.getType() != Material.SIGN &&
                                bl.getType() != Material.WALL_SIGN &&
                                bl.getType() != Material.BARRIER &&
                                bl.getType() != Material.STONE_BUTTON &&
                                bl.getType() != Material.ACACIA_BUTTON &&
                                bl.getType() != Material.BIRCH_BUTTON &&
                                bl.getType() != Material.DARK_OAK_BUTTON &&
                                bl.getType() != Material.JUNGLE_BUTTON &&
                                bl.getType() != Material.OAK_BUTTON &&
                                bl.getType() != Material.SPRUCE_BUTTON &&
                                bl.getType() != Material.WATER &&
                                bl.getType() != Material.PLAYER_HEAD &&
                                bl.getType() != Material.PLAYER_WALL_HEAD &&
                                bl.getType() != Material.NETHER_PORTAL) {
                            if (effect == 0) {
                                bl.getWorld().playSound(bl.getLocation(), Sound.ENTITY_GUARDIAN_FLOP, 2,
                                        2);
                                effect++;
                            }

                            BlockData originalBlock = bl.getBlockData();
                            for (Player all : lh.getPlayersInLobby()) {
                                if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 2 || Math.abs(x) + Math.abs(y) + Math.abs(z) == 2 - s) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            all.sendBlockChange(bl.getLocation(), dataToBlock(randomData).createBlockData());
                                        }
                                    }.runTaskLater(GamesMaster.getInstance(), 1);
                                } else {
                                    all.sendBlockChange(bl.getLocation(), dataToBlock(randomData).createBlockData());
                                    all.playEffect(bl.getLocation(), Effect.STEP_SOUND, dataToBlock(randomData));
                                }

                                Random random = new Random();
                                int r = random.nextInt(6);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        all.sendBlockChange(bl.getLocation(), originalBlock);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 55 + r);
                            }
                        }
                    }
                }
            }
        }
    }

    private void paintballHitPlayer(Player p) {
        int randomData = getRandomRainbowColor();
        ItemStack helmet = new ItemStack(Material.LEGACY_CONCRETE, 1, (byte) randomData);
        p.getInventory().setHelmet(helmet);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_FLOP, 2, 2);
        for (Player all : lh.getPlayersInLobby()) {
            all.playEffect(p.getEyeLocation(), Effect.STEP_SOUND, dataToBlock(randomData));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.isInLobby(p))
                    if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().equals(helmet))
                        p.getInventory().setHelmet(new ItemStack(Material.AIR, 0));
            }
        }.runTaskLater(GamesMaster.getInstance(), 60);
    }

    @EventHandler
    public void onPaintballHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            if (e.getEntity() instanceof Snowball && e.getEntity().getCustomName() != null && e.getEntity().getCustomName()
                    .equals("paintballGun")) {
                if (e.getHitBlock() != null) {
                    paintballHitBlock(e.getHitBlock(), e.getEntity().getLocation());
                }
                if (e.getHitEntity() != null && e.getHitEntity() instanceof Player && lh.isInLobby((Player) e.getHitEntity())) {
                    Player hit = (Player) e.getHitEntity();
                    if (hit.getGameMode() != GameMode.CREATIVE)
                        paintballHitPlayer(hit);
                }
            }
        }
    }

    private Material dataToBlock(int i) {
        if (i == 0) return Material.WHITE_CONCRETE;
        if (i == 1) return Material.ORANGE_CONCRETE;
        if (i == 2) return Material.MAGENTA_CONCRETE;
        if (i == 3) return Material.LIGHT_BLUE_CONCRETE;
        if (i == 4) return Material.YELLOW_CONCRETE;
        if (i == 5) return Material.LIME_CONCRETE;
        if (i == 6) return Material.PINK_CONCRETE;
        if (i == 7) return Material.GRAY_CONCRETE;
        if (i == 8) return Material.LIGHT_GRAY_CONCRETE;
        if (i == 9) return Material.CYAN_CONCRETE;
        if (i == 10) return Material.PURPLE_CONCRETE;
        if (i == 11) return Material.BLUE_CONCRETE;
        if (i == 12) return Material.BROWN_CONCRETE;
        if (i == 13) return Material.GREEN_CONCRETE;
        if (i == 14) return Material.RED_CONCRETE;
        if (i == 15) return Material.BLACK_CONCRETE;
        return Material.WHITE_CONCRETE;
    }
}
