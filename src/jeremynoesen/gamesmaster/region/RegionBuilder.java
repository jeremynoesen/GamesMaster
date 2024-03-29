package jeremynoesen.gamesmaster.region;

import jeremynoesen.gamesmaster.inventorytype.InventoryType;
import jeremynoesen.gamesmaster.config.Config;
import jeremynoesen.gamesmaster.config.ConfigManager;
import jeremynoesen.gamesmaster.config.ConfigType;
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
            double minx = regionConfig.getConfig().getDouble(region + ".bounds.min.x");
            double miny = regionConfig.getConfig().getDouble(region + ".bounds.min.y");
            double minz = regionConfig.getConfig().getDouble(region + ".bounds.min.z");
            double maxx = regionConfig.getConfig().getDouble(region + ".bounds.max.x");
            double maxy = regionConfig.getConfig().getDouble(region + ".bounds.max.y");
            double maxz = regionConfig.getConfig().getDouble(region + ".bounds.max.z");
            World world = Bukkit.getServer().getWorld(regionConfig.getConfig().getString(region + ".bounds.world"));
            
            region.setBounds(minx, miny, minz, maxx, maxy, maxz, world);
            region.setGamemode(GameMode.valueOf(regionConfig.getConfig().getString(region + ".gamemode")));
            region.setLeave(regionConfig.getConfig().getString(region + ".leave"));
            region.setInventoryType(InventoryType.getInventoryType(regionConfig.getConfig().getString(region + ".inventory")));
            region.setEnabled(regionConfig.getConfig().getBoolean(region + ".enabled"));
        }
    }
    
}
