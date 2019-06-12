package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.*;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUIInventory {

    private final GUIItem guiItem = new GUIItem();
    private final LobbyInventory lInv = new LobbyInventory();
    private final GUIConvert guic = new GUIConvert();

    private final Firework firework = new Firework();
    private final PaintballGun paintballGun = new PaintballGun();
    private final SlimeLauncher slimeLauncher = new SlimeLauncher();
    private final GrapplingHook grapplingHook = new GrapplingHook();
    private final FizzyLiftingDrink fizzyLiftingDrink = new FizzyLiftingDrink();
    private final Stormbreaker stormbreaker = new Stormbreaker();
    private final UnlockGadget ug = new UnlockGadget();

    public Inventory cosmeticUI(Player p) {
        Inventory cosmetics = Bukkit.createInventory(p, 9, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Cosmetics");

        File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                p.getUniqueId().toString() + ".yml");
        YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);

        cosmetics.setItem(0, guiItem.filler(9));
        cosmetics.setItem(1, guiItem.filler(9));
        cosmetics.setItem(2, guiItem.filler(0));
        if (lInv.list.contains(p.getInventory().getItem(4))) {
            cosmetics.setItem(3, guiItem.gadgetActiveUI());
        } else
            cosmetics.setItem(3, guiItem.gadgetUI());
        cosmetics.setItem(4, guiItem.filler(0));
        if (cur.getBoolean("music-muted")) {
            cosmetics.setItem(5, guiItem.musicUImuted());
        } else {
            cosmetics.setItem(5, guiItem.musicUI());
        }
        cosmetics.setItem(6, guiItem.filler(0));
        cosmetics.setItem(7, guiItem.filler(9));
        cosmetics.setItem(8, guiItem.filler(9));

        return cosmetics;
    }

    Inventory gadgetUI(Player p) {
        Inventory gadgets = Bukkit.createInventory(p, 18, ChatColor.YELLOW + "" + ChatColor.BOLD + "Gadgets");

        gadgets.setItem(0, guiItem.fillerBack(4, "cosmetic"));
        gadgets.setItem(1, guiItem.filler(0));

        if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(slimeLauncher.slimeLauncher()) ||
                p.getInventory().getItem(4).equals(slimeLauncher.slimeLauncherReload()))) {
            gadgets.setItem(3, guiItem.slimeLauncherEnabled());
        } else
            gadgets.setItem(3, guiItem.slimeLauncherDisabled());
        if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(paintballGun.paintballGun()) ||
                p.getInventory().getItem(4).equals(paintballGun.paintballGunReload()))) {
            gadgets.setItem(4, guiItem.paintballGunEnabled());
        } else
            gadgets.setItem(4, guiItem.paintballGunDisabled());
        if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(firework.firework()) ||
                p.getInventory().getItem(4).equals(firework.fireworkReload()))) {
            gadgets.setItem(2, guiItem.fireworkEnabled());
        } else
            gadgets.setItem(2, guiItem.fireworkDisabled());
        if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(grapplingHook.grapplingHook()))) {
            gadgets.setItem(6, guiItem.grapplingHookEnabled());
        } else
            gadgets.setItem(6, guiItem.grapplingHookDisabled());
        if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(fizzyLiftingDrink.fizzyLiftingDrink()) ||
                p.getInventory().getItem(4).equals(fizzyLiftingDrink.fizzyLiftingDrinkReload()))) {
            gadgets.setItem(5, guiItem.fizzyLiftingDrinkEnabled());
        } else
            gadgets.setItem(5, guiItem.fizzyLiftingDrinkDisabled());

        gadgets.setItem(7, guiItem.filler(0));
        gadgets.setItem(8, guiItem.fillerBack(4, "cosmetic"));

        gadgets.setItem(9, guiItem.fillerBack(4, "cosmetic"));
        gadgets.setItem(10, guiItem.filler(4));
        gadgets.setItem(11, guiItem.filler(4));
        gadgets.setItem(12, guiItem.filler(0));
        if (p.isOp() || ug.canUnlockStormbreaker(p)) {
            if (p.getInventory().getItem(4) != null && (p.getInventory().getItem(4).equals(stormbreaker.stormbreaker()) ||
                    p.getInventory().getItem(4).equals(stormbreaker.stormbreakerReload()))) {
                gadgets.setItem(13, guiItem.stormbreakerEnabled());
            } else {
                gadgets.setItem(13, guiItem.stormbreakerDisabled());
            }
        } else {
            gadgets.setItem(13, guiItem.stormbreakerLockedOntime());
        }
        gadgets.setItem(14, guiItem.filler(0));
        gadgets.setItem(15, guiItem.filler(4));
        gadgets.setItem(16, guiItem.filler(4));
        gadgets.setItem(17, guiItem.fillerBack(4, "cosmetic"));


        return gadgets;
    }

    public Inventory gamesUI() {
        List<ItemStack> games = new ArrayList<>();

        if (GameConfig.getConfig().getConfigurationSection("games") != null)
            for (String game : GameConfig.getConfig().getConfigurationSection("games").getKeys(false)) {
                if (ArenaConfig.getConfig().get("arenas." + game) != null &&
                        ArenaConfig.getConfig().getConfigurationSection("arenas." + game).getKeys(false).size() > 0) {
                    games.add(guiItem.game(GameConfig.getConfig().getString("games." + game + ".display-name"),
                            GameConfig.getConfig().getString("games." + game + ".icon"),
                            GameConfig.getConfig().getString("games." + game + ".description")));
                }
            }

        Inventory gamesui;

        int size = games.size();

        if (size <= 7) {
            gamesui = Bukkit.createInventory(null, 9, ChatColor.AQUA + "" + ChatColor.BOLD + "Games");

            switch (size) {
                case 1:
                    gamesui.setItem(0, guiItem.filler(3));
                    gamesui.setItem(1, guiItem.filler(3));
                    gamesui.setItem(2, guiItem.filler(3));
                    gamesui.setItem(3, guiItem.filler(0));
                    gamesui.setItem(4, games.get(0));
                    gamesui.setItem(5, guiItem.filler(0));
                    gamesui.setItem(6, guiItem.filler(3));
                    gamesui.setItem(7, guiItem.filler(3));
                    gamesui.setItem(8, guiItem.filler(3));
                    break;
                case 2:
                    gamesui.setItem(0, guiItem.filler(3));
                    gamesui.setItem(1, guiItem.filler(3));
                    gamesui.setItem(2, guiItem.filler(0));
                    gamesui.setItem(3, games.get(0));
                    gamesui.setItem(4, guiItem.filler(0));
                    gamesui.setItem(5, games.get(1));
                    gamesui.setItem(6, guiItem.filler(0));
                    gamesui.setItem(7, guiItem.filler(3));
                    gamesui.setItem(8, guiItem.filler(3));
                    break;
                case 3:
                    gamesui.setItem(0, guiItem.filler(3));
                    gamesui.setItem(1, guiItem.filler(3));
                    gamesui.setItem(2, guiItem.filler(0));
                    gamesui.setItem(3, games.get(0));
                    gamesui.setItem(4, games.get(1));
                    gamesui.setItem(5, games.get(2));
                    gamesui.setItem(6, guiItem.filler(0));
                    gamesui.setItem(7, guiItem.filler(3));
                    gamesui.setItem(8, guiItem.filler(3));
                    break;
                case 4:
                    gamesui.setItem(0, guiItem.filler(3));
                    gamesui.setItem(1, guiItem.filler(0));
                    gamesui.setItem(2, games.get(0));
                    gamesui.setItem(3, games.get(1));
                    gamesui.setItem(4, guiItem.filler(0));
                    gamesui.setItem(5, games.get(2));
                    gamesui.setItem(6, games.get(3));
                    gamesui.setItem(7, guiItem.filler(0));
                    gamesui.setItem(8, guiItem.filler(3));
                    break;
                case 5:
                    gamesui.setItem(0, guiItem.filler(3));
                    gamesui.setItem(1, guiItem.filler(0));
                    gamesui.setItem(2, games.get(0));
                    gamesui.setItem(3, games.get(1));
                    gamesui.setItem(4, games.get(2));
                    gamesui.setItem(5, games.get(3));
                    gamesui.setItem(6, games.get(4));
                    gamesui.setItem(7, guiItem.filler(0));
                    gamesui.setItem(8, guiItem.filler(3));
                    break;
                case 6:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(4, guiItem.filler(0));
                    gamesui.setItem(5, games.get(3));
                    gamesui.setItem(6, games.get(4));
                    gamesui.setItem(7, games.get(5));
                    gamesui.setItem(8, guiItem.filler(0));
                    break;
                case 7:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));
                    break;
            }

        } else {
            gamesui = Bukkit.createInventory(null, 18, ChatColor.AQUA + "" + ChatColor.BOLD + "Games");

            switch (size) {
                case 8:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(3));
                    gamesui.setItem(10, guiItem.filler(3));
                    gamesui.setItem(11, guiItem.filler(3));
                    gamesui.setItem(12, guiItem.filler(0));
                    gamesui.setItem(13, games.get(7));
                    gamesui.setItem(14, guiItem.filler(0));
                    gamesui.setItem(15, guiItem.filler(3));
                    gamesui.setItem(16, guiItem.filler(3));
                    gamesui.setItem(17, guiItem.filler(3));
                    break;
                case 9:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(3));
                    gamesui.setItem(10, guiItem.filler(3));
                    gamesui.setItem(11, guiItem.filler(0));
                    gamesui.setItem(12, games.get(7));
                    gamesui.setItem(13, guiItem.filler(0));
                    gamesui.setItem(14, games.get(8));
                    gamesui.setItem(15, guiItem.filler(0));
                    gamesui.setItem(16, guiItem.filler(3));
                    gamesui.setItem(17, guiItem.filler(3));
                    break;
                case 10:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(3));
                    gamesui.setItem(10, guiItem.filler(3));
                    gamesui.setItem(11, guiItem.filler(0));
                    gamesui.setItem(12, games.get(7));
                    gamesui.setItem(13, games.get(8));
                    gamesui.setItem(14, games.get(9));
                    gamesui.setItem(15, guiItem.filler(0));
                    gamesui.setItem(16, guiItem.filler(3));
                    gamesui.setItem(17, guiItem.filler(3));
                    break;
                case 11:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(3));
                    gamesui.setItem(10, guiItem.filler(0));
                    gamesui.setItem(11, games.get(7));
                    gamesui.setItem(12, games.get(8));
                    gamesui.setItem(13, guiItem.filler(0));
                    gamesui.setItem(14, games.get(9));
                    gamesui.setItem(15, games.get(10));
                    gamesui.setItem(16, guiItem.filler(0));
                    gamesui.setItem(17, guiItem.filler(3));
                    break;
                case 12:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(3));
                    gamesui.setItem(10, guiItem.filler(0));
                    gamesui.setItem(11, games.get(7));
                    gamesui.setItem(12, games.get(8));
                    gamesui.setItem(13, games.get(9));
                    gamesui.setItem(14, games.get(10));
                    gamesui.setItem(15, games.get(11));
                    gamesui.setItem(16, guiItem.filler(0));
                    gamesui.setItem(17, guiItem.filler(3));
                    break;
                case 13:
                    gamesui.setItem(0, guiItem.filler(0));
                    gamesui.setItem(1, games.get(0));
                    gamesui.setItem(2, games.get(1));
                    gamesui.setItem(3, games.get(2));
                    gamesui.setItem(3, games.get(3));
                    gamesui.setItem(5, games.get(4));
                    gamesui.setItem(6, games.get(5));
                    gamesui.setItem(7, games.get(6));
                    gamesui.setItem(8, guiItem.filler(0));

                    gamesui.setItem(9, guiItem.filler(0));
                    gamesui.setItem(10, games.get(7));
                    gamesui.setItem(11, games.get(8));
                    gamesui.setItem(12, games.get(9));
                    gamesui.setItem(13, guiItem.filler(0));
                    gamesui.setItem(14, games.get(10));
                    gamesui.setItem(15, games.get(11));
                    gamesui.setItem(16, games.get(12));
                    gamesui.setItem(17, guiItem.filler(0));
                    break;

            }

        }

        return gamesui;
    }

    private boolean enabled(String game, int arena) {
        return ArenaConfig.getConfig().get("arenas." + game + "." + arena + ".enabled").toString().equals("true");
    }

    private String mapName(String game, int arena) {
        return ArenaConfig.getConfig().get("arenas." + game + "." + arena + ".mapname").toString();
    }

    Inventory gameUI(int color, String game, String invName) {
        Inventory inv = Bukkit.createInventory(null, 9, invName);
        inv.setItem(0, guiItem.fillerBack(color, "game"));
        inv.setItem(8, guiItem.fillerBack(color, "game"));

        List<ItemStack> arenas = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + i) != null) {
                if (enabled(game, i) && ArenaConfig.getConfig().get("arenas." + game + "." + i + ".hidden") == null) {
                    arenas.add(guiItem.arenaEnabled(i, mapName(game, i)));
                } else {
                    arenas.add(guiItem.arenaDisabled(i, mapName(game, i)));
                }
            }
        }

        int size = arenas.size();

        switch (size) {
            case 1:
                inv.setItem(1, guiItem.filler(color));
                inv.setItem(2, guiItem.filler(color));
                inv.setItem(3, guiItem.filler(0));
                inv.setItem(4, arenas.get(0));
                inv.setItem(5, guiItem.filler(0));
                inv.setItem(6, guiItem.filler(color));
                inv.setItem(7, guiItem.filler(color));
                break;
            case 2:
                inv.setItem(1, guiItem.filler(color));
                inv.setItem(2, guiItem.filler(0));
                inv.setItem(3, arenas.get(0));
                inv.setItem(4, guiItem.filler(0));
                inv.setItem(5, arenas.get(1));
                inv.setItem(6, guiItem.filler(0));
                inv.setItem(7, guiItem.filler(color));
                break;
            case 3:
                inv.setItem(1, guiItem.filler(color));
                inv.setItem(2, guiItem.filler(0));
                inv.setItem(3, arenas.get(0));
                inv.setItem(4, arenas.get(1));
                inv.setItem(5, arenas.get(2));
                inv.setItem(6, guiItem.filler(0));
                inv.setItem(7, guiItem.filler(color));
                break;
            case 4:
                inv.setItem(1, guiItem.filler(0));
                inv.setItem(2, arenas.get(0));
                inv.setItem(3, arenas.get(1));
                inv.setItem(4, guiItem.filler(0));
                inv.setItem(5, arenas.get(2));
                inv.setItem(6, arenas.get(3));
                inv.setItem(7, guiItem.filler(0));
                break;
            case 5:
                inv.setItem(1, guiItem.filler(0));
                inv.setItem(2, arenas.get(0));
                inv.setItem(3, arenas.get(1));
                inv.setItem(4, arenas.get(2));
                inv.setItem(5, arenas.get(3));
                inv.setItem(6, arenas.get(4));
                inv.setItem(7, guiItem.filler(0));
                break;
        }
        return inv;
    }

    Inventory musicUI() {
        Inventory music = Bukkit.createInventory(null, 27, ChatColor.GOLD + "" + ChatColor.BOLD + "Music");

        music.setItem(0, guiItem.fillerBack(1, "cosmetic"));
        music.setItem(1, guiItem.filler(1));
        music.setItem(2, guiItem.filler(0));
        music.setItem(9, guiItem.filler(1));
        music.setItem(10, guiItem.filler(0));
        music.setItem(18, guiItem.fillerBack(1, "cosmetic"));
        music.setItem(19, guiItem.filler(1));
        music.setItem(20, guiItem.filler(0));
        music.setItem(8, guiItem.fillerBack(1, "cosmetic"));
        music.setItem(7, guiItem.filler(1));
        music.setItem(6, guiItem.filler(0));
        music.setItem(17, guiItem.filler(1));
        music.setItem(16, guiItem.filler(0));
        music.setItem(26, guiItem.fillerBack(1, "cosmetic"));
        music.setItem(25, guiItem.filler(1));
        music.setItem(24, guiItem.filler(0));

        music.setItem(3, guiItem.discChirp());
        music.setItem(4, guiItem.discBlocks());
        music.setItem(5, guiItem.disc13());
        music.setItem(11, guiItem.discFar());
        music.setItem(12, guiItem.discCat());
        music.setItem(13, guiItem.discWard());
        music.setItem(14, guiItem.discWait());
        music.setItem(15, guiItem.discMall());
        music.setItem(21, guiItem.discMellohi());
        music.setItem(22, guiItem.discStrad());
        music.setItem(23, guiItem.discStal());


        return music;
    }
}
