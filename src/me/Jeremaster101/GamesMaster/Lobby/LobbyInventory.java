package me.Jeremaster101.GamesMaster.Lobby;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIColor;
import me.Jeremaster101.GamesMaster.Lobby.GUI.OldGUIItem;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.Gadgets.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class LobbyInventory {

    private final FireworkCannon fireworkCannon = new FireworkCannon();
    private final PaintballGun paintballGun = new PaintballGun();
    private final SlimeLauncher slimeLauncher = new SlimeLauncher();
    private final GrapplingHook grapplingHook = new GrapplingHook();
    private final FizzyLiftingDrink fizzyLiftingDrink = new FizzyLiftingDrink();
    private final OldGUIItem guiitem = new OldGUIItem();
    private final Stormbreaker stormbreaker = new Stormbreaker();
    private final LobbyHandler lh = new LobbyHandler();

    private final ItemStack[] gadgets = new ItemStack[]{
            paintballGun.paintballGun(),
            paintballGun.paintballGunReload(),
            slimeLauncher.slimeLauncher(),
            slimeLauncher.slimeLauncherReload(),
            fireworkCannon.firework(),
            fireworkCannon.fireworkReload(),
            grapplingHook.grapplingHook(),
            fizzyLiftingDrink.fizzyLiftingDrink(),
            fizzyLiftingDrink.fizzyLiftingDrinkReload(),
            stormbreaker.stormbreaker(),
            stormbreaker.stormbreakerReload()
    };

    public final List<ItemStack> list = Arrays.asList(gadgets);

    private final Sound[] records = new Sound[]{
            Sound.MUSIC_DISC_13,
            Sound.MUSIC_DISC_BLOCKS,
            Sound.MUSIC_DISC_CAT,
            Sound.MUSIC_DISC_CHIRP,
            Sound.MUSIC_DISC_FAR,
            Sound.MUSIC_DISC_MALL,
            Sound.MUSIC_DISC_MELLOHI,
            Sound.MUSIC_DISC_STAL,
            Sound.MUSIC_DISC_STRAD,
            Sound.MUSIC_DISC_WAIT,
            Sound.MUSIC_DISC_WARD
    };

    public void clearLobbyInv(Player p) {
        p.setGlowing(false);
        p.resetPlayerWeather();


        if (p.getInventory().getItem(4) != null && p.getInventory().getItem(4).equals(stormbreaker.stormbreakerReload())) {
            for (Player all : lh.getPlayersInLobby()) {
                if (lh.isInLobby(all)) {
                    all.setPlayerWeather(WeatherType.CLEAR);
                }
            }
        }

        p.setExp(0f);
        for (Sound s : records) p.stopSound(s);
        if (p.getGameMode() == GameMode.SURVIVAL) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        for (PotionEffect allEffects : p.getActivePotionEffects()) {
            p.removePotionEffect(allEffects.getType());
        }

        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.5f);
        p.setExp(0.8f);
        p.getInventory().setItem(0, guiitem.filler(GUIColor.LIGHT_BLUE));
        p.getInventory().setItem(8, guiitem.filler(GUIColor.CYAN));
        if (p.getInventory().getItem(4) != null)
            p.getInventory().getItem(4).setType(Material.STONE_BUTTON);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getInventory().getItem(4) != null && p.getInventory().getItem(4).getType() == Material.STONE_BUTTON)
                    p.getInventory().getItem(4).setAmount(0);
                p.getInventory().setItem(1, guiitem.filler(GUIColor.LIGHT_BLUE));
                p.getInventory().setItem(7, guiitem.filler(GUIColor.CYAN));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 0.75f);
                p.setExp(0.6f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getInventory().setItem(1, guiitem.gameUI());
                p.getInventory().setItem(7, guiitem.cosmeticUI());
                if (p.getInventory().getItem(2) != null) p.getInventory().getItem(2).setAmount(0);
                if (p.getInventory().getItem(6) != null) p.getInventory().getItem(6).setAmount(0);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 0);
                p.setExp(0.4f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getInventory().setItem(0, guiitem.gameUI());
                p.getInventory().setItem(8, guiitem.cosmeticUI());
                if (p.getInventory().getItem(1) != null) p.getInventory().getItem(1).setAmount(0);
                if (p.getInventory().getItem(7) != null) p.getInventory().getItem(7).setAmount(0);
                p.setExp(0.2f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 3);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getInventory().getItem(0) != null) p.getInventory().getItem(0).setAmount(0);
                if (p.getInventory().getItem(8) != null) p.getInventory().getItem(8).setAmount(0);
                p.setExp(0f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 4);
    }

    public void loadLobbyInv(Player p) {

        for (PotionEffect allEffects : p.getActivePotionEffects()) {
            p.removePotionEffect(allEffects.getType());
        }

        p.getInventory().setHeldItemSlot(4);
        p.getInventory().setHeldItemSlot(4);
        p.getPlayer().giveExpLevels(-1000000000);
        p.getPlayer().setHealth(20);
        p.getPlayer().setFoodLevel(20);
        p.getPlayer().setSaturation(5);
        p.getPlayer().setExhaustion(0);
        p.getPlayer().setFireTicks(0);
        p.setFlying(false);
        p.getInventory().clear();
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        p.setExp(0.2f);

        p.getInventory().setItem(0, guiitem.gameUI());
        p.getInventory().setItem(8, guiitem.cosmeticUI());
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.5f);
                p.getInventory().setItem(0, guiitem.filler(GUIColor.LIGHT_BLUE));
                p.getInventory().setItem(8, guiitem.filler(GUIColor.CYAN));
                p.getInventory().setItem(1, guiitem.gameUI());
                p.getInventory().setItem(7, guiitem.cosmeticUI());
                p.setExp(0.4f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 2);
                p.getInventory().setItem(1, guiitem.filler(GUIColor.LIGHT_BLUE));
                p.getInventory().setItem(7, guiitem.filler(GUIColor.CYAN));
                p.getInventory().setItem(2, guiitem.gameUI());
                p.getInventory().setItem(6, guiitem.cosmeticUI());
                p.setExp(0.6f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getInventory().getItem(1) != null) p.getInventory().getItem(1).setAmount(0);
                if (p.getInventory().getItem(7) != null) p.getInventory().getItem(7).setAmount(0);
                p.setExp(0.8f);
            }
        }.runTaskLater(GamesMaster.getInstance(), 3);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getInventory().getItem(0) != null) p.getInventory().getItem(0).setAmount(0);
                if (p.getInventory().getItem(8) != null) p.getInventory().getItem(8).setAmount(0);
                p.setExp(1f);
                p.setAllowFlight(true);
            }
        }.runTaskLater(GamesMaster.getInstance(), 4);
    }
}
