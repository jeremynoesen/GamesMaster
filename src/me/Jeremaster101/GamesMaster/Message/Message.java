package me.Jeremaster101.GamesMaster.Message;

import me.Jeremaster101.GamesMaster.Command.CommandConfig;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.Game.Arena.ArenaConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Message {
    
    public String PREFIX = colorize(GamesMaster.messageConfig.getConfig().getString("PREFIX"));
    public String STARTUP = "\n\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.BLUE + " ██████╗ " + ChatColor.DARK_BLUE + "███╗   ███╗" + ChatColor.DARK_GRAY +
            "███╗" + ChatColor.WHITE + "  GamesMaster version " + GamesMaster.plugin.getDescription().getVersion() + " " +
            "has " +
            "been enabled!\n" +
            ChatColor.DARK_GRAY + "██╔╝" + ChatColor.BLUE + "██╔════╝ " + ChatColor.DARK_BLUE + "████╗ ████║" + ChatColor.DARK_GRAY +
            "╚██║" + ChatColor.WHITE + "  GamesMaster is written by Jeremaster101 and\n" +
            ChatColor.DARK_GRAY + "██║ " + ChatColor.BLUE + "██║  ███╗" + ChatColor.DARK_BLUE + "██╔████╔██║" + ChatColor.DARK_GRAY +
            " " +
            "██║" + ChatColor.WHITE + "  may not be modified or redistributed without\n" +
            ChatColor.DARK_GRAY + "██║ " + ChatColor.BLUE + "██║   ██║" + ChatColor.DARK_BLUE + "██║╚██╔╝██║" + ChatColor.DARK_GRAY +
            " " +
            "██║" + ChatColor.WHITE + "  his consent. For help and support, join the\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.BLUE + "╚██████╔╝" + ChatColor.DARK_BLUE + "██║ ╚═╝ ██║" + ChatColor.DARK_GRAY +
            "███║" + ChatColor.WHITE + "  support discord group: https://discord.gg/WhmQYR\n" +
            ChatColor.DARK_GRAY + "╚══╝" + ChatColor.BLUE + " ╚═════╝ " + ChatColor.DARK_BLUE + "╚═╝     ╚═╝" + ChatColor.DARK_GRAY +
            "╚══╝" + ChatColor.WHITE + "  Thank you for choosing GamesMaster!\n";
    public String ERROR_CMD_BLOCKED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_CMD_BLOCKED"));
    public String ERROR_GAMEMODE = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_GAMEMODE"));
    public String ERROR_WORLD = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_WORLD"));
    public String ERROR_NULL_BOUNDS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NULL_BOUNDS"));
    public String ERROR_ARENA_DISABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_ARENA_DISABLED"));
    public String ERROR_NOT_IN_LOBBY = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NOT_IN_LOBBY"));
    public String ERROR_UNKNOWN_INV = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_INV"));
    public String ERROR_INV_EXISTS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_INV_EXISTS"));
    public String ERROR_UNKNOWN_REGION = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_REGION"));
    public String ERROR_DEFAULT_REGION = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_DEFAULT_REGION"));
    public String ERROR_DEFAULT_INV = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_DEFAULT_INV"));
    public String ERROR_GAME_LIST = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_GAME_LIST"));
    public String ERROR_UNKNOWN_ARENA = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_ARENA"));
    public String SUCCESS_REGION_SET = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_REGION_SET"));
    public String SUCCESS_UPDATED_REGION_INV = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "SUCCESS_UPDATED_REGION_INV"));
    public String SUCCESS_UPDATED_REGION_BOUNDS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "SUCCESS_UPDATED_REGION_BOUNDS"));
    public String SUCCESS_UPDATED_REGION_MODE = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "SUCCESS_UPDATED_REGION_MODE"));
    public String SUCCESS_UPDATED_REGION_LEAVE = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "SUCCESS_UPDATED_REGION_LEAVE"));
    public String SUCCESS_REMOVED_REGION_LEAVE = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "SUCCESS_REMOVED_REGION_LEAVE"));
    public String SUCCESS_REGION_REMOVED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_REGION_REMOVED"));
    public String SUCCESS_REGION_SELECTED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_REGION_SELECTED"));
    public String SUCCESS_ARENA_ENABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_ARENA_ENABLED"));
    public String SUCCESS_ARENA_DISABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_ARENA_DISABLED"));
    public String SUCCESS_ARENA_ADDED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_ARENA_ADDED"));
    public String SUCCESS_ARENA_REMOVED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_ARENA_REMOVED"));
    public String SUCCESS_ARENA_UPDATED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_ARENA_UPDATED"));
    public String SUCCESS_INV_ADDED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_INV_ADDED"));
    public String SUCCESS_INV_REMOVED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_INV_REMOVED"));
    public String ERROR_CMD_ALREADY_BLOCKED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_CMD_ALREADY_BLOCKED"));
    public String ERROR_CMD_NOT_BLOCKED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_CMD_NOT_BLOCKED"));
    public String SUCCESS_CMD_BLOCKED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_CMD_BLOCKED"));
    public String SUCCESS_CMD_UNBLOCKED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_CMD_UNBLOCKED"));
    public String SUCCESS_PWI_FIX_ENABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_PWI_FIX_ENABLED"));
    public String SUCCESS_PWI_FIX_DISABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_PWI_FIX_DISABLED"));
    public String ERROR_NO_REGION_NAME = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_REGION_NAME"));
    public String ERROR_NO_ARENA_NAME = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_ARENA_NAME"));
    public String ERROR_NO_GAME = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_GAME"));
    public String ERROR_NO_GAMES = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_GAMES"));
    public String ERROR_NO_ARENA = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_ARENA"));
    public String ERROR_NO_GAME_COMMAND = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_GAME_COMMAND"));
    public String ERROR_NO_CMD_TO_BLOCK = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_NO_CMD_TO_BLOCK"));
    public String ERROR_UNKNOWN_COMMAND = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_COMMAND"));
    public String SUCCESS_RELOADED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_RELOADED"));
    public String SUCCESS_SET_WORLD = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_SET_WORLD"));
    public String ERROR_GAME_NOT_SETUP = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_GAME_NOT_SETUP"));
    public String ERROR_ARENA_MAX = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_ARENA_MAX"));
    public String ERROR_REGION_EXISTS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_REGION_EXISTS"));
    public String ERROR_ARENA_EXISTS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_ARENA_EXISTS"));
    public String ERROR_GAME_EXISTS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_GAME_EXISTS"));
    public String ERROR_UNKNOWN_GAME = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_GAME"));
    public String ERROR_UNKNOWN_MATERIAL = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("ERROR_UNKNOWN_MATERIAL"));
    public String SUCCESS_GAME_REMOVED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_GAME_REMOVED"));
    public String SUCCESS_GAME_ADDED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("SUCCESS_GAME_ADDED"));
    public String ERROR_ARENA_ALREADY_ENABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "ERROR_ARENA_ALREADY_ENABLED"));
    public String ERROR_ARENA_ALREADY_DISABLED = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString(
            "ERROR_ARENA_ALREADY_DISABLED"));
    public String LIST_BLOCKED_CMDS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("LIST_BLOCKED_CMDS").replace(
            "$CMDS$", CommandConfig.getConfig()
                    .getStringList("blocked-cmds").toString().replace("[", "").replace("]", "")));
    public String LIST_REGIONS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("LIST_REGIONS").replace("$REGS$",
            RegionConfig.getConfig()
                    .getConfigurationSection("regions").getKeys(false).toString().replace("[", "").replace("]", "")));
    public String LIST_INVS = PREFIX + colorize(GamesMaster.messageConfig.getConfig().getString("LIST_INVS").replace(
            "$INVS$", InventoryConfig.getConfig()
                    .getStringList("inventories").toString().replace("[", "").replace("]", "")));
    public String UI_CHICKENWARS = ChatColor.RED + "" + ChatColor.BOLD + "ChickenWars";
    public String UI_HIDE_AND_SEEK = ChatColor.GOLD + "" + ChatColor.BOLD + "Hide and Seek";
    public String UI_SPEED_BUILDERS = ChatColor.YELLOW + "" + ChatColor.BOLD + "Speed Builders";
    public String UI_SPLEEF = ChatColor.GREEN + "" + ChatColor.BOLD + "Spleef";
    public String UI_SKYWARS = ChatColor.AQUA + "" + ChatColor.BOLD + "Skywars";
    public String UI_MURDER_MYSTERY = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Murder Mystery";
    public String UI_BLOCK_PARTY = ChatColor.BLUE + "" + ChatColor.BOLD + "Block Party";
    public String UI_HUNGER_GAMES = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Hunger Games";
    public String UI_BEDWARS = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Bedwars";
    public String UI_ELYTRA_RACE = ChatColor.GRAY + "" + ChatColor.BOLD + "Elytra Race";
    public String UI_PARKOUR = ChatColor.GRAY + "" + ChatColor.BOLD + "Parkour";
    public String UI_MAP = ChatColor.WHITE + "" + ChatColor.BOLD + "Map Region";

    public String[] HELP_MAIN = new String[]{
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n------------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]------------",
            ChatColor.GRAY + "/gamesmaster arena help" + ChatColor.WHITE + ": View arena commands",
            ChatColor.GRAY + "/gamesmaster command help" + ChatColor.WHITE + ": View command commands",
            ChatColor.GRAY + "/gamesmaster config help" + ChatColor.WHITE + ": View config commands",
            ChatColor.GRAY + "/gamesmaster inventory help" + ChatColor.WHITE + ": View inventory commands",
            ChatColor.GRAY + "/gamesmaster message help" + ChatColor.WHITE + ": View message commands",
            ChatColor.GRAY + "/gamesmaster region help" + ChatColor.WHITE + ": View region commands",
            ChatColor.GRAY + "/gamesmaster setlobby" + ChatColor.WHITE + ": Set lobby to worldedit selection",
            ChatColor.GRAY + "/gamesmaster setworld" + ChatColor.WHITE + ": Set games world to this world",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_ARENA = new String[]{
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n---------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Arena Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]---------",
            ChatColor.GRAY + "/gamesmaster arena add <game> <#> <name> <cmd or loc>" + ChatColor.WHITE + ": Add arena" +
                    " " +
                    "for specified game with arena number, arena name, command to join or location to join or 'here' " +
                    "for current location",
            ChatColor.GRAY + "/gamesmaster arena disable <game> <#>" + ChatColor.WHITE + ": Disable arena",
            ChatColor.GRAY + "/gamesmaster arena enable <game> <#>" + ChatColor.WHITE + ": Enable arena",
            ChatColor.GRAY + "/gamesmaster arena info <game> <#>" + ChatColor.WHITE + ": Arena info",
            ChatColor.GRAY + "/gamesmaster arena list" + ChatColor.WHITE + ": List arenas",
            ChatColor.GRAY + "/gamesmaster arena remove <game> <#>" + ChatColor.WHITE + ": Remove arena",
            ChatColor.GRAY + "/gamesmaster arena update help" + ChatColor.WHITE + ": Arena updating commands",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_ARENA_UPDATE = new String[]{
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Arena Update Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]------",
            ChatColor.GRAY + "/gamesmaster arena update help" + ChatColor.WHITE + " Arena updating commands",


            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_COMMAND = new String[]{

            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Command Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster command block <cmd>" + ChatColor.WHITE + ": Block command in games world",
            ChatColor.GRAY + "/gamesmaster command list" + ChatColor.WHITE + ": List blocked commands",
            ChatColor.GRAY + "/gamesmaster command unblock <cmd>" + ChatColor.WHITE + ": Unblock command in games " +
                    "world",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_CONFIG = new String[]{
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Config Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster config set <key> <value>" + ChatColor.WHITE + ": Block command in games " +
                    "world",
            ChatColor.GRAY + "/gamesmaster config get <key>" + ChatColor.WHITE + ": List blocked commands",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_INVENTORY = new String[]{

            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n-------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Inventory Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-------",
            ChatColor.GRAY + "/gamesmaster inventory add <inventory>" + ChatColor.WHITE + ": Add inventories for " +
                    "regions to use",
            ChatColor.GRAY + "/gamesmaster inventory list" + ChatColor.WHITE + ": List inventories",
            ChatColor.GRAY + "/gamesmaster inventory remove <inventory>" + ChatColor.WHITE + ": Remove inventories",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_MESSAGE = new String[]{
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Message Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster message set <MESSAGE_NAME> <message>" + ChatColor.WHITE + ": Set message",
            ChatColor.GRAY + "/gamesmaster message view <MESSAGE_NAME>" + ChatColor.WHITE + ": Preview message",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_REGION = new String[]{

            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Region Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster region info <name>" + ChatColor.WHITE + ": Region info",
            ChatColor.GRAY + "/gamesmaster region list" + ChatColor.WHITE + ": List regions",
            ChatColor.GRAY + "/gamesmaster region remove <name>" + ChatColor.WHITE + ": Remove region",
            ChatColor.GRAY + "/gamesmaster region select <name>" + ChatColor.WHITE + ": Select region with " +
                    "worldedit",
            ChatColor.GRAY + "/gamesmaster region set <name> <inv> <gamemode> <optional_leave_cmd>" + ChatColor.WHITE +
                    ": Set region with a set name, inventory, gamemode, and optional game leave command at the " +
                    "current" +
                    " cuboid worldedit selection",
            ChatColor.GRAY + "/gamesmaster region update help" + ChatColor.WHITE +
                    ": Region updating commands",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String[] HELP_REGION_UPDATE = new String[]{

            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "\n-----[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Region Update Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-----",
            ChatColor.GRAY + "/gamesmaster region update bounds <name>" + ChatColor.WHITE + ": Update region bounds",
            ChatColor.GRAY + "/gamesmaster region update gamemode <name> <gamemode>" + ChatColor.WHITE + ": Update " +
                    "region gamemode",
            ChatColor.GRAY + "/gamesmaster region update inventory <name> <inventory>" + ChatColor.WHITE + ": Update" +
                    " " +
                    "region inventory",
            ChatColor.GRAY + "/gamesmaster region update leave <name> <command>" + ChatColor.WHITE + ": Update or " +
                    "remove region leave command",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------\n"
    };

    public String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String LIST_ARENAS() {
        List<String> list = new ArrayList<>();
        for (String s : ArenaConfig.getConfig().getConfigurationSection("arenas").getKeys(false)) {
            for (int i = 1; i <= 5; i++) {
                if (ArenaConfig.getConfig().get("arenas." + s + "." + i) != null) {
                    list.add(s + " " + i);
                }
            }
        }

        return colorize(GamesMaster.messageConfig.getConfig().getString("LIST_ARENAS").replace("$ARENAS$", list.toString().replace(
                "[",
                "").replace("]", "")));
    }

    public String[] arenaInfo(String game, String arena) {//TODO add to messsages config
        try {
            String command = ArenaConfig.getConfig().getString("arenas." + game + "." + arena + ".join");
            String name = ArenaConfig.getConfig().getString("arenas." + game + "." + arena + ".mapname");
            boolean hidden = ArenaConfig.getConfig().getBoolean("arenas." + game + "." + arena + ".hidden");
            boolean enabled = ArenaConfig.getConfig().getBoolean("arenas." + game + "." + arena + ".enabled");
            return new String[]{
                    ChatColor.AQUA + "Arena info:",
            ChatColor.DARK_AQUA + "Game: " + ChatColor.WHITE + game,
            ChatColor.DARK_AQUA + "Arena: " + ChatColor.WHITE + arena,
            ChatColor.DARK_AQUA + "Map Name: " + ChatColor.WHITE + name,
            ChatColor.DARK_AQUA + "Join: " + ChatColor.WHITE + command,
            ChatColor.DARK_AQUA + "Enabled: " + ChatColor.WHITE + enabled,
                    ChatColor.DARK_AQUA + "Hidden: " + ChatColor.WHITE + hidden
            };
        } catch (Exception e) {
            return new String[]{ERROR_UNKNOWN_ARENA};
        }
    }

    public String[] regionInfo(String region) {//TODO add to messsages config
        try {
            if (!region.equals("default")) {
                Location locMin =
                        new Location(Bukkit.getWorld(GamesMaster.plugin.getConfig().getString("games-world")),
                                Double.parseDouble(
                                        RegionConfig.getConfig().getString("regions." + region + ".location.min.x")),
                                Double.parseDouble(
                                        RegionConfig.getConfig().getString("regions." + region + ".location.min.y")),
                                Double.parseDouble(
                                        RegionConfig.getConfig().getString("regions." + region + ".location.min.z")));
                Location locMax = new Location(Bukkit.getWorld(GamesMaster.plugin.getConfig().getString("games-world")),
                        Double.parseDouble(
                                RegionConfig.getConfig().getString("regions." + region + ".location.max.x")),
                        Double.parseDouble(
                                RegionConfig.getConfig().getString("regions." + region + ".location.max.y")),
                        Double.parseDouble(
                                RegionConfig.getConfig().getString("regions." + region + ".location.max.z")));

                return new String[]{
                        ChatColor.AQUA + "Region info:",
                        ChatColor.DARK_AQUA + "Name: " + ChatColor.WHITE + region,
                        ChatColor.DARK_AQUA + "Upper bound: " + ChatColor.WHITE + locMax.getBlockX() + ", "
                                + locMax.getBlockY() + ", " + locMax.getBlockZ(),
                        ChatColor.DARK_AQUA + "Lower bound: " + ChatColor.WHITE + locMin.getBlockX() + ", "
                                + locMin.getBlockY() + ", " + locMin.getBlockZ(),
                        ChatColor.DARK_AQUA + "Inventory: " + ChatColor.WHITE
                                + RegionConfig.getConfig().getString("regions." + region + ".inventory"),
                        ChatColor.DARK_AQUA + "Gamemode: " + ChatColor.WHITE
                                + RegionConfig.getConfig().getString("regions." + region + ".gamemode").toLowerCase(),
                        ChatColor.DARK_AQUA + "Leave Command: " + ChatColor.WHITE
                                + RegionConfig.getConfig().getString("regions." + region + ".leave")
                };
            } else {
                return new String[]{
                        ChatColor.AQUA + "Region info:",
                        ChatColor.DARK_AQUA + "Name: " + ChatColor.WHITE + region,
                        ChatColor.DARK_AQUA + "Bounds: " + ChatColor.WHITE +
                                GamesMaster.plugin.getConfig().getString("games-world") + " world",
                        ChatColor.DARK_AQUA + "Inventory: " + ChatColor.WHITE
                                + RegionConfig.getConfig().getString("regions." + region + ".inventory"),
                        ChatColor.DARK_AQUA + "Gamemode: " + ChatColor.WHITE
                                + RegionConfig.getConfig().getString("regions." + region + ".gamemode").toLowerCase(),
                };
            }
        } catch (Exception exc) {
            return new String[]{ERROR_UNKNOWN_REGION};
        }
    }
}
