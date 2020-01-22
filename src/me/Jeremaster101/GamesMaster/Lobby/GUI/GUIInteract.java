package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.Message;
import me.Jeremaster101.GamesMaster.GMPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIInteract implements Listener {//todo NOTE: THIS CLASS IS BEING REPLACED WITH THE NEW GUI SYSTEM

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


    private final Firework firework = new Firework();
    private final PaintballGun paintballGun = new PaintballGun();
    private final SlimeLauncher slimeLauncher = new SlimeLauncher();
    private final GUIItem guiItem = new GUIItem();
    private final GUIInventory guiInv = new GUIInventory();
    private final GrapplingHook grapplingHook = new GrapplingHook();
    private final FizzyLiftingDrink fizzyLiftingDrink = new FizzyLiftingDrink();
    private final LobbyInventory li = new LobbyInventory();
    private final Stormbreaker stormbreaker = new Stormbreaker();
    private final LobbyHandler lh = new LobbyHandler();
    
    private ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    private ConfigManager gameConfig = Configs.getConfig(ConfigType.GAME);
    
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInvInteract(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClick().isCreativeAction()) return;
        if (lh.isInLobby(p)) {
            if (e.getClick().isKeyboardClick()) {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                return;
            }
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.isShiftClick()) {
                    e.setCancelled(true);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                    return;
                }

                if (e.getCurrentItem().equals(guiItem.gameUI())) {
                    e.setCancelled(true);
                    if (arenaConfig.getConfig().getConfigurationSection("arenas") != null &&
                            arenaConfig.getConfig().getConfigurationSection("arenas").getKeys(false).size() > 0) {
                        p.openInventory(guiInv.gameUI());
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 2, 2);
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 2, 2);
                        p.sendMessage(Message.ERROR_NO_GAMES);
                    }
                }
                if (e.getCurrentItem().equals(guiItem.cosmeticUI())) {
                    e.setCancelled(true);
                    p.openInventory(guiInv.cosmeticUI(p));
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                }

                for (GUIColor color : GUIColor.values()) {
                    if (e.getCurrentItem().equals(guiItem.filler(color))) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        e.setCancelled(true);
                        break;
                    }
                    if (e.getCurrentItem().equals(guiItem.fillerBack(color, "game"))) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        p.openInventory(guiInv.gameUI());
                        e.setCancelled(true);
                        break;
                    }
                    if (e.getCurrentItem().equals(guiItem.fillerBack(color, "cosmetic"))) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        p.openInventory(guiInv.cosmeticUI(p));
                        e.setCancelled(true);
                        break;
                    }
                }
                for (int arenas = 1; arenas <= 5; arenas++) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(guiItem.arenaEnabled(arenas, "")
                            .getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        try {
                            String[] cmd = arenaConfig.getConfig().get(/*guim.invNameToGame(e.getInventory()
                                    .getName()) + */"." + arenas + ".join").toString().split(" ");

                            String xs = cmd[0];
                            String ys = cmd[1];
                            String zs = cmd[2];
                            String pitchs = cmd[3];
                            String yaws = cmd[4];

                            double x = Double.parseDouble(xs);
                            double y = Double.parseDouble(ys);
                            double z = Double.parseDouble(zs);
                            float pitch = Float.parseFloat(pitchs);
                            float yaw = Float.parseFloat(yaws);

                            p.teleport(new Location(lh.gamesWorld(), x, y, z, pitch, yaw));
                        } catch (Exception e1) {
                            li.clearLobbyInv(p);
                            int finalArenas = arenas;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    //p.performCommand(arenaConfig.getConfig().get(guim.invNameToGame(e
                                    // .getInventory()
                                    //        .getName()) + "." + finalArenas + ".join").toString());
                                }
                            }.runTaskLater(GamesMaster.getInstance(), 5);
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(guiItem.arenaDisabled(arenas, "")
                            .getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        p.sendMessage(Message.ERROR_ARENA_DISABLED);
                    }
                }

                if (e.getInventory().getName().equals(guiInv.gameUI().getName())) {
                    e.setCancelled(true);

                    if (gameConfig.getConfig().get("games") != null && gameConfig.getConfig().getConfigurationSection(
                            "games").getKeys(false).size() > 0) {
                        for (String section : gameConfig.getConfig().getConfigurationSection("games").getKeys(false)) {
                            if (gameConfig.getConfig().getString(section + ".icon") != null &&
                                    e.getCurrentItem().getType().equals(Material.getMaterial(
                                            gameConfig.getConfig().getString(section + ".icon")))) {

                                //open game gui

                            }
                        }
                    }

                } else if (e.getInventory().getName().equals(guiInv.gadgetUI(p).getName())) {
                    e.setCancelled(true);

                    if (e.getCurrentItem().equals(guiItem.fireworkEnabled()) ||
                            e.getCurrentItem().equals(guiItem.slimeLauncherEnabled()) ||
                            e.getCurrentItem().equals(guiItem.paintballGunEnabled()) ||
                            e.getCurrentItem().equals(guiItem.fizzyLiftingDrinkEnabled()) ||
                            e.getCurrentItem().equals(guiItem.grapplingHookEnabled()) ||
                            e.getCurrentItem().equals(guiItem.stormbreakerEnabled())) {
                        p.getInventory().getItem(4).setAmount(0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        p.closeInventory();
                    }

                    if (e.getCurrentItem().equals(guiItem.slimeLauncherDisabled())) {
                        p.getInventory().setItem(4, slimeLauncher.slimeLauncher());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.paintballGunDisabled())) {
                        p.getInventory().setItem(4, paintballGun.paintballGun());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.fireworkDisabled())) {
                        p.getInventory().setItem(4, firework.firework());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.grapplingHookDisabled())) {
                        p.getInventory().setItem(4, grapplingHook.grapplingHook());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.fizzyLiftingDrinkDisabled())) {
                        p.getInventory().setItem(4, fizzyLiftingDrink.fizzyLiftingDrink());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.stormbreakerDisabled())) {
                        p.getInventory().setItem(4, stormbreaker.stormbreaker());
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                    if (e.getCurrentItem().equals(guiItem.stormbreakerLockedOntime()) ||
                            e.getCurrentItem().equals(guiItem.stormbreakerLockedEconomy())) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                    }
                } else if (e.getInventory().getName().equals(guiInv.cosmeticUI(p).getName())) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().equals(guiItem.gadgetUI()) || e.getCurrentItem().equals(guiItem.gadgetActiveUI())) {
                        if (e.isLeftClick()) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            p.openInventory(guiInv.gadgetUI(p));
                        } else if (e.isRightClick()) {
                            if (p.getInventory().getItem(4) != null) {
                                p.getInventory().getItem(4).setAmount(0);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                                p.closeInventory();
                            } else {
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                            }
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                    }
                    if (e.getCurrentItem().equals(guiItem.musicUI())) {
                        if (e.isLeftClick()) {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            p.openInventory(guiInv.musicUI());
                        } else if (e.isRightClick()) {
                            GMPlayer gmplayer = GMPlayer.getPlayerData(p);
                            for (Sound s : records) p.stopSound(s);
                            gmplayer.setMusicMuted(true);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            p.closeInventory();
                            gmplayer.savePlayerData();
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                    }
                    if (e.getCurrentItem().equals(guiItem.musicUImuted())) {
                        if (e.isRightClick()) {
                            GMPlayer gmplayer = GMPlayer.getPlayerData(p);
                            gmplayer.setMusicMuted(false);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            p.closeInventory();
                            gmplayer.savePlayerData();
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                    }
                } else if (e.getInventory().getName().equals(guiInv.musicUI().getName())) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().getType().equals(Material.MUSIC_DISC_13) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_BLOCKS) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_CAT) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_CHIRP) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_FAR) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_MALL) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_MELLOHI) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_STAL) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_STRAD) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_WAIT) ||
                            e.getCurrentItem().getType().equals(Material.MUSIC_DISC_WARD)) {
                        for (Player all : lh.getPlayersInLobby()) {
                            GMPlayer gmplayer = GMPlayer.getPlayerData(all);
                            if (!gmplayer.isMusicMuted()) {
                                for (Sound s : records) all.stopSound(s);
                                all.sendTitle("", ChatColor.WHITE + "" + ChatColor.BOLD + "Now playing: " +
                                        e.getCurrentItem().getItemMeta().getDisplayName(), 0, 20, 20);
                                all.playSound(all.getLocation(),
                                        Sound.valueOf(e.getCurrentItem().getType().toString()), 20, 1);
                            }
                        }
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        p.closeInventory();
                    }
                } else if (e.getWhoClicked().getGameMode() == GameMode.SURVIVAL)
                    e.setCancelled(true);
            }
        }
    }
}