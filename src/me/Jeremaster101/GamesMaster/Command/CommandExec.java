package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaCreation;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaHandler;
import me.Jeremaster101.GamesMaster.Lobby.Game.GameCreation;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyProtect;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Message.MessageConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryCreation;
import me.Jeremaster101.GamesMaster.Region.RegionCreation;
import me.Jeremaster101.GamesMaster.Region.RegionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExec implements CommandExecutor {

    private final RegionCreation rc = new RegionCreation();
    private final RegionHandler rh = new RegionHandler();
    private final InventoryCreation ic = new InventoryCreation();
    private final LobbyProtect lp = new LobbyProtect();
    private final ArenaCreation ac = new ArenaCreation();
    private final ArenaHandler ah = new ArenaHandler();
    private final CommandHandler ch = new CommandHandler();
    private final LobbyHandler lh = new LobbyHandler();
    private final GameCreation gc = new GameCreation();

    private final Message msg = new Message();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("gamesmaster")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("help")) {
                            player.sendMessage(msg.HELP_MAIN);
                        } else if (args[0].equalsIgnoreCase("region")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length >= 6) {
                                        rc.addRegion(player, args[2], args[3], args[4], args[5]);
                                    } else if (args.length == 5) {
                                        rc.addRegion(player, args[2], args[3], args[4], null);
                                    } else if (args.length == 4) {
                                        player.sendMessage(msg.ERROR_GAMEMODE);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_UNKNOWN_INV);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_REGION_NAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("update")) {

                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("bounds")) {
                                            if (args.length == 4) {
                                                rc.updateRegionBounds(player, args[3]);
                                            } else if (args.length == 3) {
                                                player.sendMessage(msg.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("inventory")) {
                                            if (args.length == 5) {
                                                rc.updateRegionInv(player, args[3], args[4]);
                                            } else if (args.length == 4) {
                                                player.sendMessage(msg.ERROR_UNKNOWN_INV);
                                            } else if (args.length == 3) {
                                                player.sendMessage(msg.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("gamemode")) {
                                            if (args.length == 5) {
                                                rc.updateRegionMode(player, args[3], args[4]);
                                            } else if (args.length == 4) {
                                                player.sendMessage(msg.ERROR_GAMEMODE);
                                            } else if (args.length == 3) {
                                                player.sendMessage(msg.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("leave")) {
                                            if (args.length >= 5) {
                                                StringBuilder leave = new StringBuilder();
                                                for (int l = 4; l <= args.length - 1; l++) {
                                                    if (l < args.length - 1)
                                                        leave.append(args[l]).append(" ");
                                                    else
                                                        leave.append(args[l]);
                                                }
                                                rc.updateRegionLeave(player, args[3], leave.toString());
                                            } else if (args.length == 4) {
                                                rc.updateRegionLeave(player, args[3], null);
                                            } else if (args.length == 3) {
                                                player.sendMessage(msg.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("help")) {
                                            player.sendMessage(msg.HELP_REGION_UPDATE);
                                        } else player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                    } else {
                                        player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                    }

                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    try {
                                        rc.removeRegion(player, args[2]);
                                    } catch (Exception e) {
                                        if (lh.isGamesWorld(player.getWorld())) {
                                            String region = rh.getRegion(player);

                                            if (!region.equals("default")) {
                                                rc.removeRegion(player, region);
                                            } else
                                                player.sendMessage(msg.ERROR_DEFAULT_REGION.replace("$ACTION$", "remove"));
                                        } else
                                            player.sendMessage(msg.ERROR_WORLD);
                                    }
                                } else if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(msg.LIST_REGIONS);
                                } else if (args[1].equalsIgnoreCase("info")) {
                                    try {
                                        player.sendMessage(msg.regionInfo(args[2]));
                                    } catch (Exception e) {
                                        if (lh.isGamesWorld(player.getWorld())) {
                                            String region = rh.getRegion(player);
                                            player.sendMessage(msg.regionInfo(region));
                                        } else
                                            player.sendMessage(msg.ERROR_WORLD);
                                    }
                                } else if (args[1].equalsIgnoreCase("select")) {
                                    if (lh.isGamesWorld(player.getWorld())) {

                                        String region = rh.getRegion(player);

                                        if (!region.equals("default")) {
                                            rh.selectRegion(player, region);
                                        } else
                                            player.sendMessage(msg.ERROR_DEFAULT_REGION.replace("$ACTION$", "select"));
                                    } else
                                        player.sendMessage(msg.ERROR_WORLD);

                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_REGION);
                                } else {
                                    player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("inventory")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length >= 3) {
                                        ic.addInventories(args[2], player);
                                    } else {
                                        player.sendMessage(msg.ERROR_UNKNOWN_INV);
                                    }
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (args.length >= 3) {
                                        ic.deleteInventories(args[2], player);
                                    } else {
                                        player.sendMessage(msg.ERROR_UNKNOWN_INV);
                                    }
                                } else if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(msg.LIST_INVS);
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_INVENTORY);
                                } else {
                                    player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("arena")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(msg.LIST_ARENAS());
                                } else if (args[1].equalsIgnoreCase("enable")) {
                                    if (args.length >= 4) {
                                        ah.enableArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("disable")) {
                                    if (args.length >= 4) {
                                        ah.disableArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length == 7) {
                                        try {
                                            ac.addArena(player, args[2], args[3], args[4], args[5], Boolean.parseBoolean(args[6]));
                                        } catch (Exception e) {
                                            ac.addArena(player, args[2], args[3], args[4], args[5], false);
                                        }
                                    } else if (args.length == 6) {
                                        ac.addArena(player, args[2], args[3], args[4], args[5], false);
                                    } else if (args.length == 5) {
                                        player.sendMessage(msg.ERROR_NO_GAME_COMMAND);
                                    } else if (args.length == 4) {
                                        player.sendMessage(msg.ERROR_NO_ARENA_NAME);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else if (args.length == 2) {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("update")) { //todo separate into multiple commands
                                    if (args.length == 7) {
                                        try {
                                            ac.updateArena(player, args[2], args[3], args[4], args[5],
                                                    Boolean.parseBoolean(args[6]));
                                        } catch (Exception e) {
                                            ac.updateArena(player, args[2], args[3], args[4], args[5], false);
                                        }
                                    } else if (args.length == 6) {
                                        ac.updateArena(player, args[2], args[3], args[4], args[5], false);
                                    } else if (args.length == 5) {
                                        player.sendMessage(msg.ERROR_NO_GAME_COMMAND);
                                    } else if (args.length == 4) {
                                        player.sendMessage(msg.ERROR_NO_ARENA_NAME);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else if (args.length == 2) {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (args.length >= 4) {
                                        ac.removeArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("info")) {
                                    if (args.length >= 4) {
                                        player.sendMessage(msg.arenaInfo(args[2], args[3]));
                                    } else if (args.length == 3) {
                                        player.sendMessage(msg.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_ARENA);
                                } else {
                                    player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("command")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(msg.LIST_BLOCKED_CMDS);
                                } else if (args[1].equalsIgnoreCase("block")) {
                                    if (args.length == 3) {
                                        ch.blockCmd(player, args[2]);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_CMD_TO_BLOCK);
                                    }
                                } else if (args[1].equalsIgnoreCase("unblock")) {
                                    if (args.length == 3) {
                                        ch.unblockCmd(player, args[2]);
                                    } else {
                                        player.sendMessage(msg.ERROR_NO_CMD_TO_BLOCK);
                                    }
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_COMMAND);
                                } else {
                                    player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("pwifix")) { //todo separate into enable/disable commands
                            if (args.length == 1) {
                                if (InventoryConfig.getConfig().getBoolean("pwi-gamemode-inv-fix")) {
                                    InventoryConfig.getConfig().set("pwi-gamemode-inv-fix", false);
                                    InventoryConfig.saveConfig();
                                    player.sendMessage(msg.SUCCESS_PWI_FIX_DISABLED);
                                } else {
                                    InventoryConfig.getConfig().set("pwi-gamemode-inv-fix", true);
                                    InventoryConfig.saveConfig();
                                    player.sendMessage(msg.SUCCESS_PWI_FIX_ENABLED);
                                }
                            } else {
                                player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("setlobby")) {
                            rc.addRegion(player, "lobby", "none", "0", null);
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            if (args.length == 2) {
                                if (args[1].equalsIgnoreCase("all")) {

                                } else if (args[1].equalsIgnoreCase("arena")) {

                                } else if (args[1].equalsIgnoreCase("game")) {

                                } else if (args[1].equalsIgnoreCase("lobby")) {

                                } else if (args[1].equalsIgnoreCase("message")) {

                                } else if (args[1].equalsIgnoreCase("inventory")) {

                                } else if (args[1].equalsIgnoreCase("region")) {

                                } else if (args[1].equalsIgnoreCase("command")) {

                                } else if (args[1].equalsIgnoreCase("main")) {
                                    GamesMaster.plugin.reloadConfig();
                                }

                            }
                            player.sendMessage(msg.SUCCESS_RELOADED);
                            GamesMaster.plugin.reloadConfig();//todo reload other configs
                            GamesMaster.messageConfig.reloadConfig();
                            lp.cleanLobby();
                        } else if (args[0].equalsIgnoreCase("setworld")) {
                            GamesMaster.plugin.getConfig().set("games-world", player.getWorld().getName());
                            player.sendMessage(msg.SUCCESS_SET_WORLD.replace("$WORLD$", player.getWorld().getName()));
                        } else {
                            player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                        }
                    } else {
                        player.sendMessage(msg.ERROR_UNKNOWN_COMMAND);
                    }
                }
            } else
                sender.sendMessage("You must execute GamesMaster commands as a player!");
        }
        return true;
    }
}
