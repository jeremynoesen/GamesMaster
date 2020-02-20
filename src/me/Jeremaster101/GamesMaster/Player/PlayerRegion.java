package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Region.Inventory.Inventory;
import me.Jeremaster101.GamesMaster.Region.Region;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Set;

public class PlayerRegion {
    
    private Player player;
    private Region loadedRegion;
    private PlayerData data;
    
    private ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    private ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    
    public PlayerRegion(Player player) {
        this.player = player;
        data = GMPlayer.getPlayer(player).getPlayerData();
        loadedRegion = Region.getRegion(data.getDataFile().getString("loaded-region"));
    }
    
    /**
     * @return name of currently loaded region
     */
    public Region getLoadedRegion() {
        return loadedRegion;
    }
    
    /**
     * @param region name to set as loaded region
     */
    public void setLoadedRegion(Region region) {
        loadedRegion = region;
        data.getDataFile().set("current-region", region.getName());
        data.savePlayerData();
        GMPlayer.getPlayer(player).updateRegionHandler(this);
    }
    
    
    /**
     * @return name of region player is standing in
     */
    public Region getCurrentRegion() {
        if (LobbyHandler.isGamesWorld(player.getWorld())) {
            for (Region region : Region.getRegions().values()) {
                if (region.containsLocation(player.getLocation()))
                    return region;
            }
            return Region.getRegion("default");
        }
        return null;
    }
    
    /**
     * makes a player's gamemode the current gamemode for the region
     */
    void fixGamemode() {
        if (getLoadedRegion() != null && player.getWorld() == LobbyHandler.gamesWorld()) {
            GameMode mode = getLoadedRegion().getGamemode();
            
            player.setGameMode(mode);
            
            if (mode.equals(GameMode.SURVIVAL)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }
                }.runTaskLater(GamesMaster.getInstance(), 1);
            }
        }
    }
    
    void loadRegion(Player player, Region region) {
        GMPlayer gmplayer = GMPlayer.getPlayer(player);
        Set<String> regs = regionConfig.getConfig().getConfigurationSection("regions").getKeys(false);
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        
        if (regs.size() > 0)
            if (getLoadedRegion() == null || !Region.getRegions().contains(getLoadedRegion())) {
                if (Inventory.getInventories().contains(region.getInventory())) {
                    gmplayer.getInventory().load();
                    player.setGameMode(region.getGamemode());
                }
            } else if (!gmplayer.getRegionHandler().getLoadedRegion().equals(region)) {
                
                if (invs != null && invs.contains(regionConfig.getConfig().get(gmplayer.getCurrentRegion()
                        + ".inventory").toString()) && !regionConfig.getConfig().get(gmplayer.getCurrentRegion() +
                        ".inventory").toString().equals("none"))
                    gmplayer.getInventory().save(gmplayer.getCurrentRegion());
                
                if (invs != null && invs.contains(regionConfig.getConfig().get(region + ".inventory").toString())) {
                    gmplayer.getInventory()..load();
                    player.setGameMode(region.getGamemode());
                }
            }
    }
    
}
