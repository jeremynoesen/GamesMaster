package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    
    ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    ConfigManager inventoryConfig = Configs.getConfig(ConfigType.INVENTORY);
    ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);
    ConfigManager commandConfig = Configs.getConfig(ConfigType.COMMAND);
    
    
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {//todo redo due to command rewrite
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

                    if(args[0].equals("reload")) {
                        if (args[1].equals("")) {
                            tabList.add("all");
                            tabList.add("arena");
                            tabList.add("command");
                            tabList.add("game");
                            tabList.add("inventory");
                            tabList.add("lobby");
                            tabList.add("main");
                            tabList.add("message");
                            tabList.add("region");
                        }

                        if (args[1].startsWith("c")) tabList.add("command");
                        if (args[1].startsWith("g")) tabList.add("game");
                        if (args[1].startsWith("i")) tabList.add("inventory");
                        if (args[1].startsWith("l")) tabList.add("lobby");
                        if (args[1].startsWith("r")) tabList.add("region");

                        if (args[1].startsWith("a")) {
                            tabList.add("all");
                            tabList.add("arena");

                        }
                        if (args[1].startsWith("al")) {
                            tabList.remove("arena");
                        }
                        if (args[1].startsWith("ar")) {
                            tabList.remove("all");
                        }

                        if (args[1].startsWith("m")) {
                            tabList.add("main");
                            tabList.add("message");

                        }
                        if (args[1].startsWith("ma")) {
                            tabList.remove("message");
                        }
                        if (args[1].startsWith("me")) {
                            tabList.remove("main");
                        }

                    }
                }
            }

            if (args.length == 3) {
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args[0].equals("region")) {
                        if (args[1].equals("info") || args[1].equals("remove") ||
                                args[1].equals("select")) {
                            if (args[2].equals("")) tabList.addAll(regionConfig.getConfig()
                                    .getConfigurationSection("regions").getKeys(false));
                            for (String regions : regionConfig.getConfig()
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
                            if (args[2].equals("")) tabList.addAll(arenaConfig.getConfig()
                                    .getConfigurationSection("arenas").getKeys(false));
                            for (String arenas : arenaConfig.getConfig()
                                    .getConfigurationSection("arenas").getKeys(false)) {
                                if (arenas.startsWith(args[2])) {
                                    tabList.add(arenas);
                                }
                            }
                        }
                    }
                    if (args[0].equals("command")) {
                        if (args[1].equals("unblock")) {
                            if (args[2].equals("")) tabList.addAll(commandConfig.getConfig()
                                    .getStringList("blocked-cmds"));
                            for (String commands : commandConfig.getConfig()
                                    .getStringList("blocked-cmds")) {
                                if (commands.startsWith(args[2])) {
                                    tabList.add(commands);
                                }
                            }
                        }
                    }
                    if (args[0].equals("inventory")) {
                        if (args[1].equals("remove")) {
                            if (args[2].equals("")) tabList.addAll(inventoryConfig.getConfig()
                                    .getStringList("inventories"));
                            for (String inventories : inventoryConfig.getConfig()
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
                    if (args[3].equals("")) tabList.addAll(inventoryConfig.getConfig()
                            .getStringList("inventories"));
                    for (String inventories : inventoryConfig.getConfig()
                            .getStringList("inventories")) {
                        if (inventories.startsWith(args[3])) {
                            tabList.add(inventories);
                        }
                    }
                } else if (args[0].equals("region") && args[1].equals("update")) {
                    if (args[3].equals("")) tabList.addAll(regionConfig.getConfig()
                            .getConfigurationSection("regions").getKeys(false));
                    for (String regions : regionConfig.getConfig()
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
