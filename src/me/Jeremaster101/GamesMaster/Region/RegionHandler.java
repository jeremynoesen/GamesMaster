package me.Jeremaster101.GamesMaster.Region;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message;
import me.Jeremaster101.GamesMaster.GMPlayer;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Set;

public class RegionHandler {//todo implement these methods into player class
    
    private final InventoryHandler ih = new InventoryHandler();
    private final LobbyHandler lh = new LobbyHandler();
    
    private ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);
    private ConfigManager inventoryConfig = Configs.getConfig(ConfigType.INVENTORY);
    
    void fixGamemode(Player p) {
        
        GMPlayer gmplayer = GMPlayer.getPlayerData(p);
        if (gmplayer.getCurrentRegion() != null && p.getWorld().getName().equals(GamesMaster.getInstance().getConfig().get("games-world").toString())) {
            GameMode mode = GameMode.valueOf((regionConfig.getConfig()
                    .getString(gmplayer.getCurrentRegion() + ".gamemode")));
            
            p.setGameMode(mode);
            
            if (mode.equals(GameMode.SURVIVAL)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                    }
                }.runTaskLater(GamesMaster.getInstance(), 1);
            }
        }
    }
    
    public boolean isInRegion(Location l, String rg) {
        
        try {
            double maxx = regionConfig.getConfig().getDouble(rg + ".location.max.x");
            double maxy = regionConfig.getConfig().getDouble(rg + ".location.max.y");
            double maxz = regionConfig.getConfig().getDouble(rg + ".location.max.z");
            double minx = regionConfig.getConfig().getDouble(rg + ".location.min.x");
            double miny = regionConfig.getConfig().getDouble(rg + ".location.min.y");
            double minz = regionConfig.getConfig().getDouble(rg + ".location.min.z");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().getName().equals(GamesMaster.getInstance().getConfig().get("games-world").toString())) &&
                    (tox <= maxx) && (tox >= minx) && (toy <= maxy) && (toy >= miny) && (toz <= maxz) && (toz >= minz);
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getRegion(Player player) {
        
        Set<String> regs = regionConfig.getConfig().getConfigurationSection("regions")
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
        GMPlayer gmplayer = GMPlayer.getPlayerData(p);
        Set<String> regs = regionConfig.getConfig().getConfigurationSection("regions").getKeys(false);
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        
        if (regs.size() > 0)
            if (gmplayer.getCurrentRegion() == null || !regs.contains(gmplayer.getCurrentRegion())) {
                if (invs != null && invs.contains(regionConfig.getConfig()
                        .get(rg + ".inventory").toString()))
                    ih.loadInv(p, rg);
            } else if (!gmplayer.getCurrentRegion().equals(rg)) {
                
                if (invs != null && invs.contains(regionConfig.getConfig().get(gmplayer.getCurrentRegion()
                        + ".inventory").toString()) && !regionConfig.getConfig().get(gmplayer.getCurrentRegion() +
                        ".inventory").toString().equals("none"))
                    ih.saveInv(p, gmplayer.getCurrentRegion());
                
                if (invs != null && invs.contains(regionConfig.getConfig().get(rg + ".inventory").toString()))
                    ih.loadInv(p, rg);
            }
    }
    
    public void selectRegion(Player player, String region) {
        
        try {
            if (region.equals("default")) {
                player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "select"));
                return;
            }
            
            BlockVector3 min = BlockVector3.at(regionConfig.getConfig().getDouble(region + ".location.min.x"),
                    regionConfig.getConfig().getDouble(region + ".location.min.y"),
                    regionConfig.getConfig().getDouble(region + ".location.min.z"));
            BlockVector3 max = BlockVector3.at(regionConfig.getConfig().getDouble(region + ".location.max.x"),
                    regionConfig.getConfig().getDouble(region + ".location.max.y"),
                    regionConfig.getConfig().getDouble(region + ".location.max.z"));
            
            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(weplayer.getWorld()).selectPrimary(max, null);
            ls.getRegionSelector(weplayer.getWorld()).selectSecondary(min, null);
            
            player.sendMessage(Message.SUCCESS_REGION_SELECTED.replace("$REGION$", region));
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION.replace("$REGION$", region));
        }
    }
}
