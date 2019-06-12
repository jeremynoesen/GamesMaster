package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> tabList = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;

            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].toLowerCase();
            }

            if (args.length == 1 || args.length == 0) {
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args[0].equals("")) {
                        tabList.add("arena");
                        tabList.add("command");
                        tabList.add("help");
                        tabList.add("inventory");
                        tabList.add("pwifix");
                        tabList.add("region");
                        tabList.add("reload");
                        tabList.add("setworld");
                        tabList.add("setlobby");
                    }
                    if (args[0].startsWith("a")) tabList.add("arena");
                    if (args[0].startsWith("c")) tabList.add("command");
                    if (args[0].startsWith("h")) tabList.add("help");
                    if (args[0].startsWith("i")) tabList.add("inventory");
                    if (args[0].startsWith("p")) tabList.add("pwifix");
                    if (args[0].startsWith("r")) {
                        tabList.add("region");
                        tabList.add("reload");

                    }
                    if (args[0].startsWith("reg")) {
                        tabList.remove("reload");
                    }
                    if (args[0].startsWith("rel")) {
                        tabList.remove("region");
                    }
                    if (args[0].startsWith("s")) {
                        tabList.add("setlobby");
                        tabList.add("setworld");

                    }
                    if (args[0].startsWith("setl")) {
                        tabList.remove("setworld");
                    }
                    if (args[0].startsWith("setw")) {
                        tabList.remove("setlobby");
                    }
                }
            }

            if (args.length == 2) {
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args[0].equals("region")) {
                        if (args[1].equals("")) {
                            tabList.add("add");
                            tabList.add("help");
                            tabList.add("info");
                            tabList.add("list");
                            tabList.add("remove");
                            tabList.add("select");
                            tabList.add("update");
                        }
                        if (args[1].startsWith("a")) tabList.add("add");
                        if (args[1].startsWith("h")) tabList.add("help");
                        if (args[1].startsWith("i")) tabList.add("info");
                        if (args[1].startsWith("l")) tabList.add("list");
                        if (args[1].startsWith("r")) tabList.add("remove");
                        if (args[1].startsWith("s")) tabList.add("select");
                        if (args[1].startsWith("u")) tabList.add("update");
                    }

                    if (args[0].equals("arena")) {
                        if (args[1].equals("")) {
                            tabList.add("add");
                            tabList.add("disable");
                            tabList.add("enable");
                            tabList.add("help");
                            tabList.add("info");
                            tabList.add("list");
                            tabList.add("remove");
                            tabList.add("update");
                        }
                        if (args[1].startsWith("a")) tabList.add("add");
                        if (args[1].startsWith("d")) tabList.add("disable");
                        if (args[1].startsWith("e")) tabList.add("enable");
                        if (args[1].startsWith("h")) tabList.add("help");
                        if (args[1].startsWith("i")) tabList.add("info");
                        if (args[1].startsWith("l")) tabList.add("list");
                        if (args[1].startsWith("r")) tabList.add("remove");
                        if (args[1].startsWith("u")) tabList.add("update");

                    }

                    if (args[0].equals("command")) {
                        if (args[1].equals("")) {
                            tabList.add("block");
                            tabList.add("help");
                            tabList.add("list");
                            tabList.add("unblock");
                        }

                        if (args[1].startsWith("b")) tabList.add("block");
                        if (args[1].startsWith("h")) tabList.add("help");
                        if (args[1].startsWith("l")) tabList.add("list");
                        if (args[1].startsWith("u")) tabList.add("unblock");

                    }

                    if (args[0].equals("inventory")) {
                        if (args[1].equals("")) {
                            tabList.add("add");
                            tabList.add("help");
                            tabList.add("list");
                            tabList.add("remove");
                        }

                        if (args[1].startsWith("a")) tabList.add("add");
                        if (args[1].startsWith("h")) tabList.add("help");
                        if (args[1].startsWith("l")) tabList.add("list");
                        if (args[1].startsWith("r")) tabList.add("remove");

                    }
                }
            }

            if (args.length == 3) {
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args[0].equals("region")) {
                        if (args[1].equals("info") || args[1].equals("remove") ||
                                args[1].equals("select")) {
                            if (args[2].equals("")) tabList.addAll(RegionConfig.getConfig()
                                    .getConfigurationSection("regions").getKeys(false));
                            for (String regions : RegionConfig.getConfig()
                                    .getConfigurationSection("regions").getKeys(false)) {
                                if (regions.startsWith(args[2])) {
                                    tabList.add(regions);
                                }
                            }
                        } else if (args[1].equals("update")) {
                            if (args[2].equals("")) {
                                tabList.add("bounds");
                                tabList.add("gamemode");
                                tabList.add("help");
                                tabList.add("inventory");
                                tabList.add("leave");
                            }
                            if (args[2].startsWith("b")) tabList.add("bounds");
                            if (args[2].startsWith("g")) tabList.add("gamemode");
                            if (args[2].startsWith("h")) tabList.add("help");
                            if (args[2].startsWith("i")) tabList.add("inventory");
                            if (args[2].startsWith("l")) tabList.add("leave");
                        }
                    }
                    if (args[0].equals("arena")) {
                        if (args[1].equals("info") || args[1].equals("remove") ||
                                args[1].equals("enable") ||
                                args[1].equals("disable")) {
                            if (args[2].equals("")) tabList.addAll(ArenaConfig.getConfig()
                                    .getConfigurationSection("arenas").getKeys(false));
                            for (String arenas : ArenaConfig.getConfig()
                                    .getConfigurationSection("arenas").getKeys(false)) {
                                if (arenas.startsWith(args[2])) {
                                    tabList.add(arenas);
                                }
                            }
                        }
                    }
                    if (args[0].equals("command")) {
                        if (args[1].equals("unblock")) {
                            if (args[2].equals("")) tabList.addAll(CommandConfig.getConfig()
                                    .getStringList("blocked-cmds"));
                            for (String commands : CommandConfig.getConfig()
                                    .getStringList("blocked-cmds")) {
                                if (commands.startsWith(args[2])) {
                                    tabList.add(commands);
                                }
                            }
                        }
                    }
                    if (args[0].equals("inventory")) {
                        if (args[1].equals("remove")) {
                            if (args[2].equals("")) tabList.addAll(InventoryConfig.getConfig()
                                    .getStringList("inventories"));
                            for (String inventories : InventoryConfig.getConfig()
                                    .getStringList("inventories")) {
                                if (inventories.startsWith(args[2])) {
                                    tabList.add(inventories);
                                }
                            }
                        }
                    }
                }
            }

            if (args.length == 4) {
                if (args[0].equals("region") && args[1].equals("add")) {
                    if (args[3].equals("")) tabList.addAll(InventoryConfig.getConfig()
                            .getStringList("inventories"));
                    for (String inventories : InventoryConfig.getConfig()
                            .getStringList("inventories")) {
                        if (inventories.startsWith(args[3])) {
                            tabList.add(inventories);
                        }
                    }
                } else if (args[0].equals("region") && args[1].equals("update")) {
                    if (args[3].equals("")) tabList.addAll(RegionConfig.getConfig()
                            .getConfigurationSection("regions").getKeys(false));
                    for (String regions : RegionConfig.getConfig()
                            .getConfigurationSection("regions").getKeys(false)) {
                        if (regions.startsWith(args[3])) {
                            tabList.add(regions);
                        }
                    }
                }
            }

            if (args.length == 5) {
                if (args[0].equals("region") && args[1].equals("add")) {
                    if (args[4].equals("")) {
                        tabList.add("adventure");
                        tabList.add("creative");
                        tabList.add("spectator");
                        tabList.add("survival");
                    }
                    if (args[4].startsWith("a")) tabList.add("adventure");
                    if (args[4].startsWith("c")) tabList.add("creative");
                    if (args[4].startsWith("s")) {
                        tabList.add("spectator");
                        tabList.add("survival");

                    }
                    if (args[4].startsWith("sp")) {
                        tabList.remove("survival");
                    }
                    if (args[4].startsWith("su")) {
                        tabList.remove("spectator");
                    }
                }
            }
        }
        return tabList;
    }
}
