package me.Jeremaster101.GamesMaster.Lobby;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LobbyHandler {
    
    private static ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);
    
    int gadgetSlot = 4;

    public ArrayList<Player> getPlayersInLobby() {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        for (Player all : Bukkit.getWorld(GamesMaster.getInstance().getConfig().get("games-world").toString()).getPlayers()) {
            if (isInLobby(all)) {
                playerArrayList.add(all);
            }
        }
        return playerArrayList;
    }

    public boolean isInLobby(Player player) {
        Location l = player.getLocation();

        try {
            double maxx = regionConfig.getConfig().getDouble("lobby.location.max.x");
            double maxy = regionConfig.getConfig().getDouble("lobby.location.max.y");
            double maxz = regionConfig.getConfig().getDouble("lobby.location.max.z");
            double minx = regionConfig.getConfig().getDouble("lobby.location.min.x");
            double miny = regionConfig.getConfig().getDouble("lobby.location.min.y");
            double minz = regionConfig.getConfig().getDouble("lobby.location.min.z");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();

            return (l.getWorld().getName().equals(GamesMaster.getInstance().getConfig().get("games-world").toString())) &&
                    (tox <= maxx) && (tox >= minx) && (toy <= maxy) && (toy >= miny) && (toz <= maxz) && (toz >= minz);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGamesWorld(World w) {
        return w.getName().equals(GamesMaster.getInstance().getConfig().get("games-world").toString());
    }

    public World gamesWorld() {
        return Bukkit.getWorld(GamesMaster.getInstance().getConfig().get("games-world").toString());
    }

    public boolean activeGadget(Player p, ItemStack gadget) {
        return p.getInventory().getItem(gadgetSlot) != null && p.getInventory().getItem(gadgetSlot).equals(gadget);
    }

    public void setGadget(Player p, ItemStack gadget) {
        p.getInventory().setItem(gadgetSlot, gadget);
        p.updateInventory();
    }
}
