package me.Jeremaster101.GamesMaster.Region;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Set;

public class RegionHandler {

    private final InventoryHandler ih = new InventoryHandler();
    private final LobbyHandler lh = new LobbyHandler();
    private final Message msg = new Message();

    void fixGamemode(Player p) {
        File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                p.getUniqueId().toString() + ".yml");
        YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
        if ((cur.get("current") != null) && p.getWorld().getName().equals(GamesMaster.plugin.getConfig().get("games-world").toString())) {
            GameMode mode = GameMode.valueOf((RegionConfig.getConfig()
                    .getString("regions." + cur.get("current").toString() + ".gamemode")));

            p.setGameMode(mode);

            if (mode.equals(GameMode.SURVIVAL)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                    }
                }.runTaskLater(GamesMaster.plugin, 1);
            }
        }
    }

    public boolean isInRegion(Location l, String rg) {
        try {
            double maxx = RegionConfig.getConfig().getDouble("regions." + rg + ".location.max.x");
            double maxy = RegionConfig.getConfig().getDouble("regions." + rg + ".location.max.y");
            double maxz = RegionConfig.getConfig().getDouble("regions." + rg + ".location.max.z");
            double minx = RegionConfig.getConfig().getDouble("regions." + rg + ".location.min.x");
            double miny = RegionConfig.getConfig().getDouble("regions." + rg + ".location.min.y");
            double minz = RegionConfig.getConfig().getDouble("regions." + rg + ".location.min.z");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();

            return (l.getWorld().getName().equals(GamesMaster.plugin.getConfig().get("games-world").toString())) &&
                    (tox <= maxx) && (tox >= minx) && (toy <= maxy) && (toy >= miny) && (toz <= maxz) && (toz >= minz);
        } catch (Exception e) {
            return false;
        }
    }

    public String getRegion(Player player) {

        Set<String> regs = RegionConfig.getConfig().getConfigurationSection("regions")
                .getKeys(false);
        if (lh.isGamesWorld(player.getWorld())) {
            int end = 0;
            for (String rg : regs) {
                if (regs.size() > 0) {
                    if (isInRegion(player.getLocation(), rg)) {
                        return rg;
                    } else
                        end++;
                    if (end == regs.size()) {
                        return "default";
                    }
                }
            }
        }
        return "default";
    }

    @SuppressWarnings("deprecation")
    void loadRegion(Player p, String rg) {
        File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                p.getUniqueId().toString() + ".yml");
        YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
        Set<String> regs = RegionConfig.getConfig().getConfigurationSection("regions").getKeys(false);
        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");

        if (regs.size() > 0)
            if (cur.get("current") == null || !regs.contains(cur.get("current").toString())) {
                if (invs != null && invs.contains(RegionConfig.getConfig()
                        .get("regions." + rg + ".inventory").toString()))
                    ih.loadInv(p, rg);
            } else if (!cur.get("current").toString().equals(rg)) {

                if (invs != null && invs.contains(RegionConfig.getConfig().get("regions." + cur.get("current").toString()
                        + ".inventory").toString()) && !RegionConfig.getConfig().get("regions." + cur.get("current")
                        .toString() + ".inventory").toString().equals("none"))
                    ih.saveInv(p, cur.get("current").toString());

                if (invs != null && invs.contains(RegionConfig.getConfig().get("regions." + rg + ".inventory").toString()))
                    ih.loadInv(p, rg);
            }
    }

    public void selectRegion(Player player, String region) {

        try {
            if (region.equals("default")) {
                player.sendMessage(msg.ERROR_DEFAULT_REGION.replace("$ACTION$", "select"));
                return;
            }

            BlockVector3 min = BlockVector3.at(RegionConfig.getConfig().getDouble("regions." + region + ".location.min.x"),
                    RegionConfig.getConfig().getDouble("regions." + region + ".location.min.y"),
                    RegionConfig.getConfig().getDouble("regions." + region + ".location.min.z"));
            BlockVector3 max = BlockVector3.at(RegionConfig.getConfig().getDouble("regions." + region + ".location.max.x"),
                    RegionConfig.getConfig().getDouble("regions." + region + ".location.max.y"),
                    RegionConfig.getConfig().getDouble("regions." + region + ".location.max.z"));

            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(weplayer.getWorld()).selectPrimary(max, null);
            ls.getRegionSelector(weplayer.getWorld()).selectSecondary(min, null);

            player.sendMessage(msg.SUCCESS_REGION_SELECTED.replace("$REGION$", region));
        } catch (Exception e) {
            player.sendMessage(msg.ERROR_UNKNOWN_REGION.replace("$REGION$", region));
        }
    }
}
