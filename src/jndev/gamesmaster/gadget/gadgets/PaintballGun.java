package jndev.gamesmaster.gadget.gadgets;

import jndev.gamesmaster.GamesMaster;
import jndev.gamesmaster.gadget.GadgetItem;
import jndev.gamesmaster.lobby.LobbyHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * paintball gun gadget
 */
@SuppressWarnings("deprecation")
public class PaintballGun implements Listener {
    
    private static final LobbyHandler lh = new LobbyHandler();
    
    /**
     * get random colored rainbow concrete block
     */
    private static Material getRandomColorBlock() {
        List<Integer> allData = Arrays.asList(14, 1, 4, 5, 11, 10, 2, 9, 3, 6);
        Random random = new Random();
        int i = allData.get(random.nextInt(allData.size()));
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
    
    /**
     * shoots a paintball
     *
     * @param p player to use paintball gun
     */
    public static void use(Player p) {
        Snowball s = p.launchProjectile(Snowball.class);
        s.setCustomName("paintballGun");
        lh.setGadget(p, GadgetItem.PAINTBALL_GUN_RELOAD.getItem());
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.activeGadget(p, GadgetItem.PAINTBALL_GUN_RELOAD.getItem())) {
                    lh.setGadget(p, GadgetItem.PAINTBALL_GUN.getItem());
                }
            }
        }.runTaskLater(GamesMaster.getInstance(), 5);
    }
    
    /**
     * plays paint splat effect if a block is hit
     *
     * @param hitBlock block hit
     * @param hitLoc location hit
     */
    private static void paintballHitBlock(Block hitBlock, Location hitLoc) {
        Location hitBlockLoc = hitBlock.getLocation().add(0.5, 0.5, 0.5);
        if (hitLoc.distance(hitBlockLoc) >= 1.4 && hitLoc.getBlock().getType() == Material.AIR) {
            hitLoc = new Location(hitLoc.getWorld(), (hitLoc.getX() + hitBlockLoc.getX()) / 2, (hitLoc.getY() +
                    hitBlockLoc.getY()) / 2, (hitLoc.getZ() + hitBlockLoc.getZ()) / 2);
        }
        Material random = getRandomColorBlock();
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
                        if (bl.getType() != Material.AIR &&//todo config for types of blocks to ignore
                                bl.getType() != Material.LEGACY_SIGN &&
                                bl.getType() != Material.LEGACY_WALL_SIGN &&
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
                                            all.sendBlockChange(bl.getLocation(), random.createBlockData());
                                        }
                                    }.runTaskLater(GamesMaster.getInstance(), 1);
                                } else {
                                    all.sendBlockChange(bl.getLocation(), random.createBlockData());
                                    all.playEffect(bl.getLocation(), Effect.STEP_SOUND, random);
                                }
                                
                                Random rand = new Random();
                                int rInt = rand.nextInt(6);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        all.sendBlockChange(bl.getLocation(), originalBlock);
                                    }
                                }.runTaskLater(GamesMaster.getInstance(), 55 + rInt);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * set helmet of player to a colored block
     *
     * @param p player who was hit
     */
    private static void paintballHitPlayer(Player p) {
        Material random = getRandomColorBlock();
        ItemStack helmet = new ItemStack(random, 1);
        ItemStack oldHelmet = p.getInventory().getHelmet();
        p.getInventory().setHelmet(helmet);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_FLOP, 2, 2);
        for (Player all : lh.getPlayersInLobby()) {
            all.playEffect(p.getEyeLocation(), Effect.STEP_SOUND, random);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.isInLobby(p))
                    if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().equals(helmet))
                        p.getInventory().setHelmet(oldHelmet);
            }
        }.runTaskLater(GamesMaster.getInstance(), 60);
    }
    
    /**
     * listens for when the paintball hits something
     *
     * @param e projectile hit event
     */
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
}
