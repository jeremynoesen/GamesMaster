package jndev.gamesmaster.region;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.inventorytype.InventoryType;
import org.bukkit.GameMode;
import org.bukkit.World;

/**
 * saves regions
 */
public class RegionSaver {
    
    public static ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    
    /**
     * saves all regions to the file
     */
    public static void saveAll() {
        regionConfig.getConfig().set("", null);
        for(Region region : Region.getRegions()) {
            save(region);
        }
    }
    
    /**
     * saves a region to the config
     *
     * @param region region to save to file
     */
    public static void save(Region region) {
        double[][] bounds = region.getBounds();
        double minx = bounds[0][0];
        double miny = bounds[0][1];
        double minz = bounds[0][2];
        double maxx = bounds[1][0];
        double maxy = bounds[1][1];
        double maxz = bounds[1][2];
        World world = region.getWorld();
        GameMode mode = region.getGamemode();
        InventoryType inventory = region.getInventoryType();
        String leave = region.getLeave();
        boolean enabled = region.isEnabled();
        String name = region.getName();
        
        regionConfig.getConfig().set(name + ".bounds.min.x", minx);
        regionConfig.getConfig().set(name + ".bounds.min.y", miny);
        regionConfig.getConfig().set(name + ".bounds.min.z", minz);
        regionConfig.getConfig().set(name + ".bounds.max.x", maxx);
        regionConfig.getConfig().set(name + ".bounds.max.y", maxy);
        regionConfig.getConfig().set(name + ".bounds.max.z", maxz);
        regionConfig.getConfig().set(name + ".bounds.world", world.getName());
        regionConfig.getConfig().set(name + ".gamemode", mode.toString());
        regionConfig.getConfig().set(name + ".inventory", inventory.getName());
        regionConfig.getConfig().set(name + ".leave", leave);
        regionConfig.getConfig().set(name + ".enabled", enabled);
        regionConfig.saveConfig();
    }
    
}
