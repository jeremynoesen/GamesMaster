package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class Arena {//todo finish this class
    
    private String game;
    private String arena;
    private Player player;
    
    public Arena(Player player, String game, String arena) {
        this.player = player;
        if (game != null && arena != null) {
            this.game = game;
            this.arena = arena;
            if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) == null) {
                ArenaConfig.getConfig().set(game + "." + arena + ".enabled", false);
                ArenaConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_GAME_ADDED.replace("$GAME$", game));
            }
        }
    }
    
    public static Arena getArena(Player player, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            return new Arena(player, game, arena);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_ARENA);
            return null;
        }
    }

    private final LobbyHandler lh = new LobbyHandler();

    public void addArena(Player p, String game, String arena, String mapname, String join, int priority,
                         boolean enabled, boolean hidden) {

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            p.sendMessage(Message.ERROR_ARENA_EXISTS);
            return;
        }

        if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
            p.sendMessage(Message.ERROR_NO_GAMES);
        } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
            p.sendMessage(Message.ERROR_GAME_LIST);
        } else {

            try {
                Integer.parseInt(arena);
                if (Integer.parseInt(arena) > 5) {
                    p.sendMessage(Message.ERROR_ARENA_MAX);
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
                return;
            }

            String command;

            if (join.equalsIgnoreCase("here")) {
                if (lh.isGamesWorld(p.getWorld())) {
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float pitch = p.getLocation().getPitch();
                    float yaw = p.getLocation().getYaw();
                    command = x + " " + y + " " + z + " " + yaw + " " + pitch;
                } else {
                    p.sendMessage(Message.ERROR_WORLD);
                    return;
                }
            } else {//todo read from alias config
                command = join.replace("_", " ")
                        .replace("skywars", "sw")
                        .replace("blockparty", "bp")
                        .replace("hideandseek", "has")
                        .replace("mgsplegg", "sp")
                        .replace("mgskywars", "sw")
                        .replace("speedbuilders", "sb")
                        .replace("hungergames", "hg")
                        .replace("survivalgames", "hg")
                        .replace("sg", "hg");
            }

            ArenaConfig.getConfig().set(game + "." + arena + ".enabled", enabled);
            ArenaConfig.getConfig().set(game + "." + arena + ".join", command);
            ArenaConfig.getConfig().set(game + "." + arena + ".mapname", mapname);
            ArenaConfig.getConfig().set(game + "." + arena + ".priority", priority);
            ArenaConfig.getConfig().set(game + "." + arena + ".hidden", hidden);
            ArenaConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_ARENA_ADDED.replace("$ARENA", game + " " + arena + " - " + mapname.replace("_", " ")));
        }
    }

    public void updateArenaName(Player p, String game, String arena, String mapname) { //todo finish

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                p.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                p.sendMessage(Message.ERROR_GAME_LIST);
            } else {

                p.sendMessage(Message.SUCCESS_UPDATED_ARENA_NAME.replace("$NAME$", mapname));
            }
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void updateArenaJoin(Player p, String game, String arena, String join) {

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                p.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                p.sendMessage(Message.ERROR_GAME_LIST);
            } else {
    
                p.sendMessage(Message.SUCCESS_UPDATED_ARENA_JOIN.replace("$JOIN$", join));
            }
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
    
    public void updateArenaPriority(Player p, String game, String arena, int priority) {//todo remove arena number replace with priority
        
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                p.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                p.sendMessage(Message.ERROR_GAME_LIST);
            } else {
    
                p.sendMessage(Message.SUCCESS_UPDATED_ARENA_PRIORITY.replace("$PRIORITY$", Integer.toString(priority)));
            }
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void updateArenaEnabled(Player p, String game, String arena, boolean enabled) {

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                p.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                p.sendMessage(Message.ERROR_GAME_LIST);
            } else {
    
                p.sendMessage(Message.SUCCESS_UPDATED_ARENA_ENABLED.replace("$ENABLED$", Boolean.toString(enabled)));
            }
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void updateArenaHidden(Player p, String game, String arena, boolean hidden) {

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            if (GameConfig.getConfig().getConfigurationSection("games") == null ||
                    GameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() == 0) {
                p.sendMessage(Message.ERROR_NO_GAMES);
            } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
                p.sendMessage(Message.ERROR_GAME_LIST);
            } else {
    
                p.sendMessage(Message.SUCCESS_UPDATED_ARENA_HIDDEN.replace("$HIDDEN$", Boolean.toString(hidden)));
            }
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }

    public void updateArena(Player p, String game, String arena, String mapname, String join, boolean hidden) {
        //todo make so dont have to type _ for space

        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) == null) {
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
            return;
        }

        if (GameConfig.getConfig().getConfigurationSection("games") == null) {
            p.sendMessage(Message.ERROR_NO_GAMES);
        } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
            p.sendMessage(Message.ERROR_GAME_LIST);
        } else {

            try {
                Integer.parseInt(arena);
                if (Integer.parseInt(arena) > 5) {
                    p.sendMessage(Message.ERROR_ARENA_MAX);
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
                return;
            }

            String command;

            if (join.equalsIgnoreCase("here")) {
                if (lh.isGamesWorld(p.getWorld())) {
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float pitch = p.getLocation().getPitch();
                    float yaw = p.getLocation().getYaw();
                    command = x + " " + y + " " + z + " " + yaw + " " + pitch;
                } else {
                    p.sendMessage(Message.ERROR_WORLD);
                    return;
                }
            } else {
                command = join.replace("_", " ")//todo read from alias config
                        .replace("skywars", "sw")
                        .replace("blockparty", "bp")
                        .replace("hideandseek", "has")
                        .replace("mgsplegg", "sp")
                        .replace("mgskywars", "sw")
                        .replace("speedbuilders", "sb")
                        .replace("hungergames", "hg")
                        .replace("survivalgames", "hg")
                        .replace("sg", "hg");
            }

            ArenaConfig.getConfig().set(game + "." + arena + ".enabled", true);
            ArenaConfig.getConfig().set(game + "." + arena + ".join", command);
            ArenaConfig.getConfig().set(game + "." + arena + ".mapname", mapname);
            if (game.equals("map") && hidden)
                ArenaConfig.getConfig().set(game + "." + arena + ".hidden", hidden);
            else ArenaConfig.getConfig().set(game + "." + arena + ".hidden", null);
            ArenaConfig.saveConfig();
            if (hidden)
                p.sendMessage(Message.SUCCESS_ARENA_UPDATED.replace("$ARENA",
                        game + " " + arena + " (hidden) - " + mapname.replace("_", " ")));
            else
                p.sendMessage(Message.SUCCESS_ARENA_UPDATED.replace("$ARENA",
                        game + " " + arena + " - " + mapname.replace(
                        "_", " ")));
        }
    }


    public void removeArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null &&
                ArenaConfig.getConfig().getConfigurationSection(game).getKeys(false).size() == 1) {
            ArenaConfig.getConfig().set(game, null);
            p.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else if (ArenaConfig.getConfig().getConfigurationSection(game + "." + arena) != null) {
            ArenaConfig.getConfig().set(game + "." + arena, null);
            p.sendMessage(Message.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else
            p.sendMessage(Message.ERROR_UNKNOWN_ARENA);
    }
}
