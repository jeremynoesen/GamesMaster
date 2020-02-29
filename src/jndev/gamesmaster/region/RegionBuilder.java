package jndev.gamesmaster.region;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.config.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;

/**
 * reads regions from config
 */
public class RegionBuilder {
    
    private static ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    
    /**
     * builds all regions in region config
     */
    public static void buildAll() {
        for(String region : regionConfig.getConfig().getKeys(false)) {
            build(region);
        }
    }
    
    /**
     * create region from config values
     *
     * @param name region to build
     */
    public static void build(String name) {
        Region region = new Region(name);
        
        if (name.equals("default")) {
            region.setGamemode(GameMode.valueOf(regionConfig.getConfig().getString(region + ".gamemode")));
        } else {
            double minx = regionConfig.getConfig().getDouble(region + ".location.min.x");
            double miny = regionConfig.getConfig().getDouble(region + ".location.min.y");
            double minz = regionConfig.getConfig().getDouble(region + ".location.min.z");
            double maxx = regionConfig.getConfig().getDouble(region + ".location.max.x");
            double maxy = regionConfig.getConfig().getDouble(region + ".location.max.y");
            double maxz = regionConfig.getConfig().getDouble(region + ".location.max.z");
            World world = Bukkit.getServer().getWorld(regionConfig.getConfig().getString(region + ".location.world"));
            
            region.setBounds(minx, miny, minz, maxx, maxy, maxz, world);
            region.setGamemode(GameMode.valueOf(regionConfig.getConfig().getString(region + ".gamemode")));
            region.setLeave(regionConfig.getConfig().getString(region + ".leave"));
            region.setInventory(regionConfig.getConfig().getString(region + ".inventory"));
            region.setEnabled(regionConfig.getConfig().getBoolean(region + ".enabled"));
        }
        region.save();
    }
    
}
