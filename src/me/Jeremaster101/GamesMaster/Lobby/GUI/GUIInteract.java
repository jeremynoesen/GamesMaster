package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class GUIInteract implements Listener {

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
    private final GUIConvert guim = new GUIConvert();
    private final GrapplingHook grapplingHook = new GrapplingHook();
    private final FizzyLiftingDrink fizzyLiftingDrink = new FizzyLiftingDrink();
    private final LobbyInventory li = new LobbyInventory();
    private final Stormbreaker stormbreaker = new Stormbreaker();
    private final LobbyHandler lh = new LobbyHandler();

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

                if(e.getCurrentItem().equals(guiItem.gameUI())) {
                    e.setCancelled(true);
                    if (ArenaConfig.getConfig().getConfigurationSection("arenas") != null &&
                            ArenaConfig.getConfig().getConfigurationSection("arenas").getKeys(false).size() > 0) {
                        p.openInventory(guiInv.gamesUI());
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 2, 2);
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 2, 2);
                        p.sendMessage(Message.ERROR_NO_GAMES);
                    }
                }
                if(e.getCurrentItem().equals(guiItem.cosmeticUI())) {
                    e.setCancelled(true);
                    p.openInventory(guiInv.cosmeticUI(p));
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                }

                for (int fillerType = 0; fillerType <= 15; fillerType++) {
                    if (e.getCurrentItem().equals(guiItem.filler(fillerType))) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        e.setCancelled(true);
                        break;
                    }
                    if (e.getCurrentItem().equals(guiItem.fillerBack(fillerType, "game"))) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        p.openInventory(guiInv.gamesUI());
                        e.setCancelled(true);
                        break;
                    }
                    if (e.getCurrentItem().equals(guiItem.fillerBack(fillerType, "cosmetic"))) {
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
                            String[] cmd = ArenaConfig.getConfig().get("arenas." + guim.invNameToGame(e.getInventory()
                                    .getName()) + "." + arenas + ".join").toString().split(" ");

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
                                    p.performCommand(ArenaConfig.getConfig().get("arenas." + guim.invNameToGame(e.getInventory()
                                            .getName()) + "." + finalArenas + ".join").toString());
                                }
                            }.runTaskLater(GamesMaster.plugin, 5);
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(guiItem.arenaDisabled(arenas, "")
                            .getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        p.sendMessage(Message.ERROR_ARENA_DISABLED);
                    }
                }

                if (e.getInventory().getName().equals(guiInv.gamesUI().getName())) {
                    e.setCancelled(true);

                    /*if (e.getCurrentItem().equals(guiItem.bp()) ||
                            e.getCurrentItem().equals(guiItem.sp()) ||
                            e.getCurrentItem().equals(guiItem.sb()) ||
                            e.getCurrentItem().equals(guiItem.sw()) ||
                            e.getCurrentItem().equals(guiItem.hg()) ||
                            e.getCurrentItem().equals(guiItem.has()) ||
                            e.getCurrentItem().equals(guiItem.elytra()) ||
                            e.getCurrentItem().equals(guiItem.mm()) ||
                            e.getCurrentItem().equals(guiItem.cw()) ||
                            e.getCurrentItem().equals(guiItem.parkour()) ||
                            e.getCurrentItem().equals(guiItem.bw())) {
                        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + guim.invNameToGame(
                                e.getCurrentItem().getItemMeta().getDisplayName())) != null &&
                                ArenaConfig.getConfig().getConfigurationSection("arenas." + guim.invNameToGame(
                                        e.getCurrentItem().getItemMeta().getDisplayName())).getKeys(false).size() > 0) {
                            p.openInventory(guiInv.gameUI(guim.gameToColorInt(guim.invNameToGame(e.getCurrentItem()
                                            .getItemMeta().getDisplayName())),
                                    guim.invNameToGame(e.getCurrentItem().getItemMeta().getDisplayName()),
                                    e.getCurrentItem().getItemMeta().getDisplayName()));
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                            p.sendMessage(Message.ERROR_GAME_NOT_SETUP);
                        }
                    }*/

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
                            File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                                    p.getUniqueId().toString() + ".yml");
                            YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
                            for (Sound s : records) p.stopSound(s);
                            cur.set("music-muted", true);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            p.closeInventory();
                            try {
                                cur.save(playerInvConfigFile);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                    }
                    if (e.getCurrentItem().equals(guiItem.musicUImuted())) {
                        if (e.isRightClick()) {
                            File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                                    p.getUniqueId().toString() + ".yml");
                            YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
                            cur.set("music-muted", false);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                            p.closeInventory();
                            try {
                                cur.save(playerInvConfigFile);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
                            File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                                    all.getUniqueId().toString() + ".yml");
                            YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
                            if ((cur.get("music-muted") == null || !cur.getBoolean("music-muted"))) {
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