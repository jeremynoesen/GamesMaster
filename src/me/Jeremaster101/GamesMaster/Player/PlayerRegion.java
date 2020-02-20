package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Region.Region;
import me.Jeremaster101.GamesMaster.Region.RegionHandler;
import org.bukkit.entity.Player;

import java.util.Set;

public class PlayerRegion {
    
    private Player player;
    private String loadedRegion;
    private PlayerData data;
    
    private ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    private ConfigManager inventoryConfig = Config.getConfig(ConfigType.INVENTORY);
    
    public PlayerRegion(Player player) {
        this.player = player;
        data = GMPlayer.getPlayer(player).getPlayerData();
        loadedRegion = data.getDataFile().getString("loaded-region");
    }
    
    /**
     * @return name of currently loaded region
     */
    public String getLoadedRegion() {
        return loadedRegion;
    }
    
    /**
     * @param region name to set as loaded region
     */
    public void setLoadedRegion(String region) {
        loadedRegion = region;
        data.getDataFile().set("current-region", region);
        data.savePlayerData();
        GMPlayer.getPlayer(player).updateRegionHandler(this);
    }
    
    
    /**
     * @return name of region player is in
     */
    public Region getCurrentRegionName() {
        Set<String> regs = regionConfig.getConfig().getConfigurationSection("regions").getKeys(false);
        if (LobbyHandler.isGamesWorld(player.getWorld())) {
            int end = 0;
            for (String rg : regs) {
                if (regs.size() > 0) {
                    if (RegionHandler.isInRegion(player.getLocation(), rg)) {
                        return Region.getRegion(player, rg);
                    } else
                        end++;
                    if (end == regs.size()) {
                        return Region.getRegion(player, "default");
                    }
                }
            }
        }
        return Region.getRegion(player, "default");
    }
    
}
