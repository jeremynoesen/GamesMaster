/*package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIConfig;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaHandler;
import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.Game.GameBuilder;
import me.Jeremaster101.GamesMaster.Lobby.LobbyConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Lobby.LobbyProtect;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Message.MessageConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryCreation;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import me.Jeremaster101.GamesMaster.Region.RegionBuilder;
import me.Jeremaster101.GamesMaster.Region.RegionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExec implements CommandExecutor {//todo clean up or redo this class since commands are changing so much
    
    private final RegionBuilder rc = new RegionBuilder();
    // needed
    private final RegionHandler rh = new RegionHandler();
    private final InventoryCreation ic = new InventoryCreation();
    private final LobbyProtect lp = new LobbyProtect();
    //private final ArenaBuilder ac = new ArenaBuilder();
    private final ArenaHandler ah = new ArenaHandler();
    private final CommandHandler ch = new CommandHandler();
    private final LobbyHandler lh = new LobbyHandler();
    
    private final Message msg = new Message();
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {//todo change command order to something like logblock does it
        
        if (commandLabel.equalsIgnoreCase("gamesmaster")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                GameBuilder.getGame(player, "test").setEnabled(true);
    
                if (player.hasPermission("gamesmaster.admin")) {
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("help")) {
                            player.sendMessage(msg.HELP_MAIN);
                        } else if (args[0].equalsIgnoreCase("region")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length >= 6) {
                                        StringBuilder leave = new StringBuilder();
                                        for (int l = 5; l <= args.length - 1; l++) {
                                            if (l < args.length - 1)
                                                leave.append(args[l]).append(" ");
                                            else
                                                leave.append(args[l]);
                                        }
                                        rc.addRegion(player, args[2], args[3], args[4], leave.toString());
                                    } else if (args.length == 5) {
                                        rc.addRegion(player, args[2], args[3], args[4], null);
                                    } else if (args.length == 4) {
                                        player.sendMessage(Message.ERROR_GAMEMODE);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_UNKNOWN_INV);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_REGION_NAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("update")) {
                                    
                                    if (args.length > 2) {
                                        if (args[2].equalsIgnoreCase("bounds")) {
                                            if (args.length == 4) {
                                                rc.updateRegionBounds(player, args[3]);
                                            } else if (args.length == 3) {
                                                player.sendMessage(Message.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("inventory")) {
                                            if (args.length == 5) {
                                                rc.updateRegionInv(player, args[3], args[4]);
                                            } else if (args.length == 4) {
                                                player.sendMessage(Message.ERROR_UNKNOWN_INV);
                                            } else if (args.length == 3) {
                                                player.sendMessage(Message.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("gamemode")) {
                                            if (args.length == 5) {
                                                rc.updateRegionMode(player, args[3], args[4]);
                                            } else if (args.length == 4) {
                                                player.sendMessage(Message.ERROR_GAMEMODE);
                                            } else if (args.length == 3) {
                                                player.sendMessage(Message.ERROR_NO_REGION_NAME);
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
                                                player.sendMessage(Message.ERROR_NO_REGION_NAME);
                                            }
                                        } else if (args[2].equalsIgnoreCase("help")) {
                                            player.sendMessage(msg.HELP_REGION_UPDATE);
                                        } else player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
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
                                                player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "remove"));
                                        } else
                                            player.sendMessage(Message.ERROR_WORLD);
                                    }
                                } else if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(Message.LIST_REGIONS.replace("$REGS$", RegionConfig.getConfig()
                                            .getConfigurationSection("regions").getKeys(false).toString()
                                            .replace("[", "").replace("]", "")));
                                } else if (args[1].equalsIgnoreCase("info")) {
                                    try {
                                        player.sendMessage(msg.regionInfo(args[2]));
                                    } catch (Exception e) {
                                        if (lh.isGamesWorld(player.getWorld())) {
                                            String region = rh.getRegion(player);
                                            player.sendMessage(msg.regionInfo(region));
                                        } else
                                            player.sendMessage(Message.ERROR_WORLD);
                                    }
                                } else if (args[1].equalsIgnoreCase("select")) {
                                    if (lh.isGamesWorld(player.getWorld())) {
                                        
                                        String region = rh.getRegion(player);
                                        
                                        if (!region.equals("default")) {
                                            rh.selectRegion(player, region);
                                        } else
                                            player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "select"));
                                    } else
                                        player.sendMessage(Message.ERROR_WORLD);
                                    
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_REGION);
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("inventory")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length >= 3) {
                                        ic.addInventories(args[2], player);
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_INV);
                                    }
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (args.length >= 3) {
                                        ic.deleteInventories(args[2], player);
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_INV);
                                    }
                                } else if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(Message.LIST_INVS.replace("$INVS$", InventoryConfig.getConfig()
                                            .getStringList("inventories").toString().replace("[", "").replace("]", "")));
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_INVENTORY);
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("arena")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(msg.LIST_ARENAS());
                                } else if (args[1].equalsIgnoreCase("enable")) {
                                    if (args.length >= 4) {
                                        ah.enableArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("disable")) {
                                    if (args.length >= 4) {
                                        ah.disableArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length == 7) {
                                        try {
                                            //ac.addArena(player, args[2], args[3], args[4], args[5],
                                            //        Boolean.parseBoolean(args[6]));
                                        } catch (Exception e) {
                                            //ac.addArena(player, args[2], args[3], args[4], args[5], false);
                                        }
                                    } else if (args.length == 6) {
                                        //ac.addArena(player, args[2], args[3], args[4], args[5], false);
                                    } else if (args.length == 5) {
                                        player.sendMessage(Message.ERROR_NO_GAME_COMMAND);
                                    } else if (args.length == 4) {
                                        player.sendMessage(Message.ERROR_NO_ARENA_NAME);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else if (args.length == 2) {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("update")) { //todo separate into multiple commands
                                    if (args.length == 7) {
                                        try {
                                            //ac.updateArena(player, args[2], args[3], args[4], args[5],
                                            //       Boolean.parseBoolean(args[6]));
                                        } catch (Exception e) {
                                            //ac.updateArena(player, args[2], args[3], args[4], args[5], false);
                                        }
                                    } else if (args.length == 6) {
                                        //ac.updateArena(player, args[2], args[3], args[4], args[5], false);
                                    } else if (args.length == 5) {
                                        player.sendMessage(Message.ERROR_NO_GAME_COMMAND);
                                    } else if (args.length == 4) {
                                        player.sendMessage(Message.ERROR_NO_ARENA_NAME);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else if (args.length == 2) {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (args.length >= 4) {
                                        //ac.removeArena(player, args[2], args[3]);
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("info")) {
                                    if (args.length >= 4) {
                                        player.sendMessage(msg.arenaInfo(args[2], args[3]));
                                    } else if (args.length == 3) {
                                        player.sendMessage(Message.ERROR_NO_ARENA);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_GAME);
                                    }
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_ARENA);
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("command")) {
                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage(Message.LIST_BLOCKED_CMDS.replace("$CMDS$", CommandConfig.getConfig()
                                            .getStringList("blocked-cmds").toString().replace("[", "").replace("]", "")));
                                } else if (args[1].equalsIgnoreCase("block")) {
                                    if (args.length == 3) {
                                        ch.blockCmd(player, args[2]);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_CMD_TO_BLOCK);
                                    }
                                } else if (args[1].equalsIgnoreCase("unblock")) {
                                    if (args.length == 3) {
                                        ch.unblockCmd(player, args[2]);
                                    } else {
                                        player.sendMessage(Message.ERROR_NO_CMD_TO_BLOCK);
                                    }
                                } else if (args[1].equalsIgnoreCase("help")) {
                                    player.sendMessage(msg.HELP_COMMAND);
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                }
                            } else {
                                player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("game")) {
                            if(args.length > 1) {
                                
                                //todo game commands
                            }
                            
                        } else if (args[0].equalsIgnoreCase("pwifix")) { //todo separate into enable/disable commands
                            if (args.length == 1) {
                                if (GamesMaster.getInstance().getConfig().getBoolean("pwi-gamemode-inv-fix")) {
                                    GamesMaster.getInstance().getConfig().set("pwi-gamemode-inv-fix", false);
                                    GamesMaster.getInstance().saveConfig();
                                    player.sendMessage(Message.SUCCESS_PWI_FIX_DISABLED);
                                } else {
                                    GamesMaster.getInstance().getConfig().set("pwi-gamemode-inv-fix", true);
                                    GamesMaster.getInstance().saveConfig();
                                    player.sendMessage(Message.SUCCESS_PWI_FIX_ENABLED);
                                }
                            } else {
                                player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            }
                        } else if (args[0].equalsIgnoreCase("setlobby")) {
                            rc.addRegion(player, "lobby", "none", "0", null);
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            if (args.length == 2) {
                                
                                if (args[1].equalsIgnoreCase("all")) {
                                    
                                    GamesMaster.getInstance().reloadConfig();
                                    
                                    ArenaConfig.reloadConfig();
                                    GameConfig.reloadConfig();
                                    LobbyConfig.reloadConfig();
                                    InventoryConfig.reloadConfig();
                                    RegionConfig.reloadConfig();
                                    CommandConfig.reloadConfig();
                                    MessageConfig.reloadConfig();
                                    
                                    lp.cleanLobby();
                                    
                                    player.sendMessage(Message.SUCCESS_RELOADED_ALL);
                                    
                                } else if (args[1].equalsIgnoreCase("arena")) {
                                    ArenaConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "arena"));
                                    
                                } else if (args[1].equalsIgnoreCase("game")) {
                                    GameConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "game"));
                                    
                                } else if (args[1].equalsIgnoreCase("lobby")) {
                                    lp.cleanLobby();
                                    LobbyConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "lobby"));
                                    
                                } else if (args[1].equalsIgnoreCase("message")) {
                                    MessageConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "message"));
                                    
                                } else if (args[1].equalsIgnoreCase("inventory")) {
                                    InventoryConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "inventory"));
                                    
                                } else if (args[1].equalsIgnoreCase("region")) {
                                    RegionConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "region"));
                                    
                                } else if (args[1].equalsIgnoreCase("command")) {
                                    CommandConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "command"));
    
                                } else if (args[1].equalsIgnoreCase("gui")) {
                                    GUIConfig.reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "GUI"));
    
                                } else if (args[1].equalsIgnoreCase("main")) {
                                    GamesMaster.getInstance().reloadConfig();
                                    player.sendMessage(Message.SUCCESS_RELOADED.replace("$CONFIG$", "main"));
                                    
                                } else player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                                
                            } else if (args.length == 1) player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                            
                        } else if (args[0].equalsIgnoreCase("setworld")) {
                            GamesMaster.getInstance().getConfig().set("games-world", player.getWorld().getName());
                            player.sendMessage(Message.SUCCESS_SET_WORLD.replace("$WORLD$", player.getWorld().getName()));
                        } else {
                            player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                        }
                    } else {
                        player.sendMessage(Message.ERROR_UNKNOWN_COMMAND);
                    }
                }
            } else
                sender.sendMessage("You must execute GamesMaster commands as a player!");
        }
        return true;
    }
}

 */