package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Message {
    
    private static ConfigManager messageConfig = Configs.getConfig(ConfigType.MESSAGE);
    private static ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);
    private static ConfigManager arenaConfig = Configs.getConfig(ConfigType.ARENA);
    
    
    public static String PREFIX;
    public static String ERROR_CMD_BLOCKED;
    public static String ERROR_GAMEMODE;
    public static String ERROR_WORLD;
    public static String ERROR_NULL_BOUNDS;
    public static String ERROR_ARENA_DISABLED;
    public static String ERROR_NOT_IN_LOBBY;
    public static String ERROR_UNKNOWN_INV;
    public static String ERROR_INV_EXISTS;
    public static String ERROR_UNKNOWN_REGION;
    public static String ERROR_DEFAULT_REGION;
    public static String ERROR_DEFAULT_INV;
    public static String ERROR_GAME_LIST;
    public static String ERROR_UNKNOWN_ARENA;
    public static String SUCCESS_REGION_CREATED;
    public static String SUCCESS_SET_REGION_INV;
    public static String SUCCESS_SET_REGION_BOUNDS;
    public static String SUCCESS_SET_REGION_MODE;
    public static String SUCCESS_SET_REGION_LEAVE;
    public static String SUCCESS_REMOVED_REGION_LEAVE;
    public static String SUCCESS_SET_GAME_NAME;
    public static String SUCCESS_SET_GAME_ICON;
    public static String SUCCESS_SET_GAME_DESCRIPTION;
    public static String SUCCESS_SET_GAME_COLOR;
    public static String SUCCESS_SET_GAME_ENABLED;
    public static String SUCCESS_SET_GAME_HIDDEN;
    public static String SUCCESS_SET_GAME_PRIORITY;
    public static String SUCCESS_SET_ARENA_NAME;
    public static String SUCCESS_SET_ARENA_JOIN;
    public static String SUCCESS_SET_ARENA_HIDDEN;
    public static String SUCCESS_SET_ARENA_ENABLED;
    public static String SUCCESS_SET_ARENA_PRIORITY;
    public static String SUCCESS_REGION_REMOVED;
    public static String SUCCESS_REGION_SELECTED;
    public static String SUCCESS_ARENA_ENABLED;
    public static String SUCCESS_ARENA_DISABLED;
    public static String SUCCESS_ARENA_ADDED;
    public static String SUCCESS_ARENA_REMOVED;
    public static String SUCCESS_ARENA_UPDATED;
    public static String SUCCESS_INV_ADDED;
    public static String SUCCESS_INV_REMOVED;
    public static String ERROR_CMD_ALREADY_BLOCKED;
    public static String ERROR_CMD_NOT_BLOCKED;
    public static String SUCCESS_CMD_BLOCKED;
    public static String SUCCESS_CMD_UNBLOCKED;
    public static String SUCCESS_PWI_FIX_ENABLED;
    public static String SUCCESS_PWI_FIX_DISABLED;
    public static String ERROR_NO_REGION_NAME;
    public static String ERROR_NO_ARENA_NAME;
    public static String ERROR_NO_GAME;
    public static String ERROR_NO_GAMES;
    public static String ERROR_NO_ARENA;
    public static String ERROR_NO_GAME_COMMAND;
    public static String ERROR_NO_CMD_TO_BLOCK;
    public static String ERROR_UNKNOWN_COMMAND;
    public static String SUCCESS_RELOADED;
    public static String SUCCESS_RELOADED_ALL;
    public static String SUCCESS_SET_WORLD;
    public static String ERROR_GAME_NOT_SETUP;
    public static String ERROR_ARENA_MAX;
    public static String ERROR_REGION_EXISTS;
    public static String ERROR_ARENA_EXISTS;
    public static String ERROR_GAME_EXISTS;
    public static String ERROR_UNKNOWN_GAME;
    public static String ERROR_UNKNOWN_MATERIAL;
    public static String SUCCESS_GAME_REMOVED;
    public static String SUCCESS_GAME_ADDED;
    public static String ERROR_ARENA_ALREADY_ENABLED;
    public static String ERROR_ARENA_ALREADY_DISABLED;
    public static String LIST_BLOCKED_CMDS;
    public static String LIST_REGIONS;
    public static String LIST_INVS;
    public static String ERROR_CANT_ENABLE;
    
    public static void reloadMessages() { //todo simplify messages, combine things used often like enabled messages and such
        PREFIX = colorize(messageConfig.getConfig().getString("PREFIX"));
        ERROR_CMD_BLOCKED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_CMD_BLOCKED"));
        ERROR_GAMEMODE = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_GAMEMODE"));
        ERROR_WORLD = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_WORLD"));
        ERROR_NULL_BOUNDS = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NULL_BOUNDS"));
        ERROR_ARENA_DISABLED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_ARENA_DISABLED"));
        ERROR_NOT_IN_LOBBY = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NOT_IN_LOBBY"));
        ERROR_UNKNOWN_INV = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_INV"));
        ERROR_INV_EXISTS = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_INV_EXISTS"));
        ERROR_UNKNOWN_REGION = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_REGION"));
        ERROR_DEFAULT_REGION = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_DEFAULT_REGION"));
        ERROR_DEFAULT_INV = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_DEFAULT_INV"));
        ERROR_GAME_LIST = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_GAME_LIST"));
        ERROR_UNKNOWN_ARENA = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_ARENA"));
        SUCCESS_REGION_CREATED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_REGION_CREATED"));
        SUCCESS_SET_REGION_INV = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_REGION_INV"));
        SUCCESS_SET_REGION_BOUNDS = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_REGION_BOUNDS"));
        SUCCESS_SET_REGION_MODE = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_REGION_MODE"));
        SUCCESS_SET_REGION_LEAVE = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_REGION_LEAVE"));
        SUCCESS_REMOVED_REGION_LEAVE = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_REMOVED_REGION_LEAVE"));
        SUCCESS_SET_GAME_NAME = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_NAME"));
        SUCCESS_SET_GAME_ICON = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_ICON"));
        SUCCESS_SET_GAME_DESCRIPTION = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_DESCRIPTION"));
        SUCCESS_SET_GAME_COLOR = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_COLOR"));
        SUCCESS_SET_GAME_ENABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_ENABLED"));
        SUCCESS_SET_GAME_HIDDEN = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_HIDDEN"));
        SUCCESS_SET_GAME_PRIORITY = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_GAME_PRIORITY"));
        SUCCESS_SET_ARENA_NAME = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_ARENA_NAME"));
        SUCCESS_SET_ARENA_JOIN = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_ARENA_JOIN"));
        SUCCESS_SET_ARENA_HIDDEN = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_ARENA_HIDDEN"));
        SUCCESS_SET_ARENA_ENABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_ARENA_ENABLED"));
        SUCCESS_SET_ARENA_PRIORITY = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_ARENA_PRIORITY"));
        SUCCESS_REGION_REMOVED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_REGION_REMOVED"));
        SUCCESS_REGION_SELECTED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_REGION_SELECTED"));
        SUCCESS_ARENA_ENABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_ARENA_ENABLED"));
        SUCCESS_ARENA_DISABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_ARENA_DISABLED"));
        SUCCESS_ARENA_ADDED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_ARENA_ADDED"));
        SUCCESS_ARENA_REMOVED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_ARENA_REMOVED"));
        SUCCESS_ARENA_UPDATED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_ARENA_UPDATED"));
        SUCCESS_INV_ADDED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_INV_ADDED"));
        SUCCESS_INV_REMOVED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_INV_REMOVED"));
        ERROR_CMD_ALREADY_BLOCKED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_CMD_ALREADY_BLOCKED"));
        ERROR_CMD_NOT_BLOCKED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_CMD_NOT_BLOCKED"));
        SUCCESS_CMD_BLOCKED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_CMD_BLOCKED"));
        SUCCESS_CMD_UNBLOCKED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_CMD_UNBLOCKED"));
        SUCCESS_PWI_FIX_ENABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_PWI_FIX_ENABLED"));
        SUCCESS_PWI_FIX_DISABLED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_PWI_FIX_DISABLED"));
        ERROR_NO_REGION_NAME = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_REGION_NAME"));
        ERROR_NO_ARENA_NAME = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_ARENA_NAME"));
        ERROR_NO_GAME = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_GAME"));
        ERROR_NO_GAMES = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_GAMES"));
        ERROR_NO_ARENA = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_ARENA"));
        ERROR_NO_GAME_COMMAND = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_GAME_COMMAND"));
        ERROR_NO_CMD_TO_BLOCK = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_NO_CMD_TO_BLOCK"));
        ERROR_UNKNOWN_COMMAND = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_COMMAND"));
        SUCCESS_RELOADED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_RELOADED"));
        SUCCESS_RELOADED_ALL = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_RELOADED_ALL"));
        SUCCESS_SET_WORLD = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_SET_WORLD"));
        ERROR_GAME_NOT_SETUP = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_GAME_NOT_SETUP"));
        ERROR_ARENA_MAX = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_ARENA_MAX"));
        ERROR_REGION_EXISTS = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_REGION_EXISTS"));
        ERROR_ARENA_EXISTS = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_ARENA_EXISTS"));
        ERROR_GAME_EXISTS = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_GAME_EXISTS"));
        ERROR_UNKNOWN_GAME = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_GAME"));
        ERROR_UNKNOWN_MATERIAL = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_UNKNOWN_MATERIAL"));
        SUCCESS_GAME_REMOVED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_GAME_REMOVED"));
        SUCCESS_GAME_ADDED = PREFIX + colorize(messageConfig.getConfig().getString("SUCCESS_GAME_ADDED"));
        ERROR_ARENA_ALREADY_ENABLED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_ARENA_ALREADY_ENABLED"));
        ERROR_ARENA_ALREADY_DISABLED = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_ARENA_ALREADY_DISABLED"));
        LIST_BLOCKED_CMDS = PREFIX + colorize(messageConfig.getConfig().getString("LIST_BLOCKED_CMDS"));
        LIST_REGIONS = PREFIX + colorize(messageConfig.getConfig().getString("LIST_REGIONS"));
        LIST_INVS = PREFIX + colorize(messageConfig.getConfig().getString("LIST_INVS"));
        ERROR_CANT_ENABLE = PREFIX + colorize(messageConfig.getConfig().getString("ERROR_CANT_ENABLE"));
    }
    
    public String STARTUP = "\n\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.BLUE + " ██████╗ " + ChatColor.DARK_BLUE + "███╗   ███╗" + ChatColor.DARK_GRAY +
            "███╗" + ChatColor.WHITE + "  GamesMaster version " + GamesMaster.getInstance().getDescription().getVersion() + " " +
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

    public String[] HELP_MAIN = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-----------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-----------",
            ChatColor.GRAY + "/gamesmaster arena help" + ChatColor.WHITE + ": View arena commands",
            ChatColor.GRAY + "/gamesmaster command help" + ChatColor.WHITE + ": View command commands",
            ChatColor.GRAY + "/gamesmaster config help" + ChatColor.WHITE + ": View config commands",
            ChatColor.GRAY + "/gamesmaster inventory help" + ChatColor.WHITE + ": View inventory commands",
            ChatColor.GRAY + "/gamesmaster message help" + ChatColor.WHITE + ": View message commands",
            ChatColor.GRAY + "/gamesmaster region help" + ChatColor.WHITE + ": View region commands",
            ChatColor.GRAY + "/gamesmaster setlobby" + ChatColor.WHITE + ": Set lobby to worldedit selection",
            ChatColor.GRAY + "/gamesmaster setworld" + ChatColor.WHITE + ": Set games world to this world",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_ARENA = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------[" + ChatColor.BLUE +
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
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_ARENA_UPDATE = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Arena Update Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]------",
            ChatColor.GRAY + "/gamesmaster arena update help" + ChatColor.WHITE + " Arena updating commands",


            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_COMMAND = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Command Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster command block <cmd>" + ChatColor.WHITE + ": Block command in games world",
            ChatColor.GRAY + "/gamesmaster command list" + ChatColor.WHITE + ": List blocked commands",
            ChatColor.GRAY + "/gamesmaster command unblock <cmd>" + ChatColor.WHITE + ": Unblock command in games " +
                    "world",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_CONFIG = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Config Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster config set <key> <value>" + ChatColor.WHITE + ": Block command in games " +
                    "world",
            ChatColor.GRAY + "/gamesmaster config get <key>" + ChatColor.WHITE + ": List blocked commands",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_INVENTORY = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Inventory Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-------",
            ChatColor.GRAY + "/gamesmaster inventory add <inventory>" + ChatColor.WHITE + ": Add inventories for " +
                    "regions to use",
            ChatColor.GRAY + "/gamesmaster inventory list" + ChatColor.WHITE + ": List inventories",
            ChatColor.GRAY + "/gamesmaster inventory remove <inventory>" + ChatColor.WHITE + ": Remove inventories",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_MESSAGE = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------[" + ChatColor.BLUE +
                    "" + ChatColor.BOLD + "Games" + ChatColor.DARK_BLUE +
                    "" + ChatColor.BOLD + "Master " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Message Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
            ChatColor.GRAY + "/gamesmaster message set <MESSAGE_NAME> <message>" + ChatColor.WHITE + ": Set message",
            ChatColor.GRAY + "/gamesmaster message view <MESSAGE_NAME>" + ChatColor.WHITE + ": Preview message",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_REGION = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------[" + ChatColor.BLUE +
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
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public String[] HELP_REGION_UPDATE = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-----[" + ChatColor.BLUE +
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
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };

    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String LIST_ARENAS() {
        List<String> list = new ArrayList<>();
        for (String s : arenaConfig.getConfig().getConfigurationSection("arenas").getKeys(false)) {
            for (int i = 1; i <= 5; i++) {
                if (arenaConfig.getConfig().get(s + "." + i) != null) {
                    list.add(s + " " + i);
                }
            }
        }

        return PREFIX + colorize(messageConfig.getConfig().getString("LIST_ARENAS").replace("$ARENAS$",
                list.toString().replace(
                        "[",
                        "").replace("]", "")));
    }

    public String[] arenaInfo(String game, String arena) {
        try {
            String join = arenaConfig.getConfig().getString(game + "." + arena + ".join");
            String name = arenaConfig.getConfig().getString(game + "." + arena + ".mapname");
            boolean hidden = arenaConfig.getConfig().getBoolean(game + "." + arena + ".hidden");
            boolean enabled = arenaConfig.getConfig().getBoolean(game + "." + arena + ".enabled");
            return new String[]{
                    "",
                    PREFIX + colorize(messageConfig.getConfig().getString("ARENA_INFO_HEADER")),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_GAME").replace("$GAME$", game)),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_ARENA").replace("$ARENA$", arena)),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_NAME").replace("$NAME$", name)),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_JOIN").replace("$JOIN$", join)),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_ENABLED").replace("$ENABLED$",
                            Boolean.toString(enabled))),
                    colorize(messageConfig.getConfig().getString("ARENA_INFO_HIDDEN").replace("$HIDDEN$",
                            Boolean.toString(hidden))),
                    ""
            };
        } catch (Exception e) {
            return new String[]{ERROR_UNKNOWN_ARENA};
        }
    }

    public String[] regionInfo(String region) {
        try {
            if (!region.equals("default")) {
                Location locMin =
                        new Location(Bukkit.getWorld(GamesMaster.getInstance().getConfig().getString("games-world")),
                                Double.parseDouble(
                                        regionConfig.getConfig().getString(region + ".location.min.x")),
                                Double.parseDouble(
                                        regionConfig.getConfig().getString(region + ".location.min.y")),
                                Double.parseDouble(
                                        regionConfig.getConfig().getString(region + ".location.min.z")));
                Location locMax = new Location(Bukkit.getWorld(GamesMaster.getInstance().getConfig().getString("games-world")),
                        Double.parseDouble(
                                regionConfig.getConfig().getString(region + ".location.max.x")),
                        Double.parseDouble(
                                regionConfig.getConfig().getString(region + ".location.max.y")),
                        Double.parseDouble(
                                regionConfig.getConfig().getString(region + ".location.max.z")));

                return new String[]{
                        "",
                        PREFIX + colorize(messageConfig.getConfig().getString("REGION_INFO_HEADER")),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_NAME").replace("$NAME$", region)),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_UPPER_BOUND").replace("$LOC$", locMax.getBlockX() + ", "
                                + locMax.getBlockY() + ", " + locMax.getBlockZ())),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_LOWER_BOUND").replace("$LOC$", locMin.getBlockX() + ", "
                                + locMin.getBlockY() + ", " + locMin.getBlockZ())),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_INVENTORY").replace("$INV$",
                                regionConfig.getConfig().getString(region + ".inventory"))),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_GAMEMODE").replace("$MODE$",
                                regionConfig.getConfig().getString(region + ".gamemode").toLowerCase())),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_LEAVE").replace("$LEAVE$",
                                regionConfig.getConfig().getString(region + ".leave"))),
                        ""
                };
            } else {
                return new String[]{
                        "",
                        PREFIX + colorize(messageConfig.getConfig().getString("REGION_INFO_HEADER")),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_NAME").replace("$NAME$", region)),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_DEFAULT_BOUNDS").replace("$WORLD$",
                                GamesMaster.getInstance().getConfig().getString("games-world"))),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_INVENTORY").replace("$INV$",
                                regionConfig.getConfig().getString(region + ".inventory"))),
                        colorize(messageConfig.getConfig().getString("REGION_INFO_GAMEMODE").replace("$MODE$",
                                regionConfig.getConfig().getString(region + ".gamemode").toLowerCase())),
                        ""
                };
            }
        } catch (Exception exc) {
            return new String[]{ERROR_UNKNOWN_REGION};
        }
    }
}
