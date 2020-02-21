package me.Jeremaster101.GamesMaster.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
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

/**
 * handling of player region data
 */
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
            for (Region region : Region.getRegions()) {
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
    public void fixGamemode() {
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
    
    /**
     * loads a region inventory and gamemode
     *
     * @param region region to load
     */
    public void loadRegion(Region region) {
        GMPlayer gmplayer = GMPlayer.getPlayer(player);
        
        if (getLoadedRegion() == null || !Region.getRegions().contains(getLoadedRegion())) {
            if (Inventory.getInventories().contains(region.getInventory())) {
                gmplayer.getInventory(region.getInventory()).load();
                player.setGameMode(region.getGamemode());
            }
        } else if (!gmplayer.getRegionHandler().getLoadedRegion().equals(region)) {
            if (region.getInventory() != null && Inventory.getInventories().contains(region.getInventory()))
                gmplayer.getInventory(region.getInventory()).save();
            
            if (Inventory.getInventories().contains(region.getInventory())) {
                gmplayer.getInventory(region.getInventory()).load();
                player.setGameMode(region.getGamemode());
            }
        }
    }
    
    /**
     * select the bounds of a region using worldedit
     *
     * @param region region to select
     */
    public void selectRegion(Region region) {
        double[][] bounds = region.getBounds();
        BlockVector3 min = BlockVector3.at(bounds[0][0], bounds[0][1], bounds[0][2]);
        BlockVector3 max = BlockVector3.at(bounds[1][0], bounds[1][1], bounds[1][2]);
        com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
        LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
        ls.getRegionSelector(weplayer.getWorld()).selectPrimary(max, null);
        ls.getRegionSelector(weplayer.getWorld()).selectSecondary(min, null);
    }
    
}
