package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.Lobby.Game.GameConfig;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

public class ArenaCreation {

    private final Message msg = new Message();
    private final LobbyHandler lh = new LobbyHandler();

    public void addArena(Player p, String game, String arena, String mapname, String join, boolean hidden) {

        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) != null) {
            p.sendMessage(msg.ERROR_ARENA_EXISTS);
            return;
        }

        if (GameConfig.getConfig().getConfigurationSection("games") == null) {
            p.sendMessage(msg.ERROR_NO_GAMES);
        } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
            p.sendMessage(msg.ERROR_GAME_LIST);
        } else {

            try {
                Integer.parseInt(arena);
                if (Integer.parseInt(arena) > 5) {
                    p.sendMessage(msg.ERROR_ARENA_MAX);
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
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
                    p.sendMessage(msg.ERROR_WORLD);
                    return;
                }
            } else {
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

            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".enabled", true);
            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".join", command);
            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".mapname", mapname);
            if (game.equals("map") && hidden)
                ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".hidden", hidden);
            else ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".hidden", null);
            ArenaConfig.saveConfig();
            if (hidden)
                p.sendMessage(msg.SUCCESS_ARENA_ADDED.replace("$ARENA",
                        game + " " + arena + " (hidden) - " + mapname.replace("_", " ")));
            else
                p.sendMessage(msg.SUCCESS_ARENA_ADDED.replace("$ARENA", game + " " + arena + " - " + mapname.replace(
                        "_", " ")));
        }
    }

    public void updateArenaName(Player p, String game, String arena, String mapname) { //todo finish

    }

    public void updateArenaJoin(Player p, String game, String arena, String join) {

    }

    public void updateArenaHidden(Player p, String game, String arena, boolean hidden) {

    }

    public void updateArena(Player p, String game, String arena, String mapname, String join, boolean hidden) {
        //todo make so dont have to type _ for space

        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) == null) {
            p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
            return;
        }

        if (GameConfig.getConfig().getConfigurationSection("games") == null) {
            p.sendMessage(msg.ERROR_NO_GAMES);
        } else if (!GameConfig.getConfig().getConfigurationSection("games").getKeys(false).contains(game)) {
            p.sendMessage(msg.ERROR_GAME_LIST);
        } else {

            try {
                Integer.parseInt(arena);
                if (Integer.parseInt(arena) > 5) {
                    p.sendMessage(msg.ERROR_ARENA_MAX);
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
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
                    p.sendMessage(msg.ERROR_WORLD);
                    return;
                }
            } else {
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

            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".enabled", true);
            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".join", command);
            ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".mapname", mapname);
            if (game.equals("map") && hidden)
                ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".hidden", hidden);
            else ArenaConfig.getConfig().set("arenas." + game + "." + arena + ".hidden", null);
            ArenaConfig.saveConfig();
            if (hidden)
                p.sendMessage(msg.SUCCESS_ARENA_UPDATED.replace("$ARENA",
                        game + " " + arena + " (hidden) - " + mapname.replace("_", " ")));
            else
                p.sendMessage(msg.SUCCESS_ARENA_UPDATED.replace("$ARENA", game + " " + arena + " - " + mapname.replace(
                        "_", " ")));
        }
    }


    public void removeArena(Player p, String game, String arena) {
        if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) != null &&
                ArenaConfig.getConfig().getConfigurationSection("arenas." + game).getKeys(false).size() == 1) {
            ArenaConfig.getConfig().set("arenas." + game, null);
            p.sendMessage(msg.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else if (ArenaConfig.getConfig().getConfigurationSection("arenas." + game + "." + arena) != null) {
            ArenaConfig.getConfig().set("arenas." + game + "." + arena, null);
            p.sendMessage(msg.SUCCESS_ARENA_REMOVED.replace("$ARENA", game + " " + arena));
            ArenaConfig.saveConfig();
        } else
            p.sendMessage(msg.ERROR_UNKNOWN_ARENA);
    }
}
