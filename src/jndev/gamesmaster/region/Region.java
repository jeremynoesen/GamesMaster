package jndev.gamesmaster.region;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.region.inventory.Inventory;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.lobby.LobbyHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * regions used in the plugin
 */
public class Region { //todo when using with commands, check for default when running command
    //todo REDO OTHER CLASSES THIS WAY
    private static ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    private static HashMap<String, Region> regions = new HashMap<>();
    private String name;
    private Inventory inventory;
    private String leave;
    private GameMode mode;
    private boolean enabled;
    private double bounds[][];
    private World world;
    
    /**
     * create a new region object by name
     *
     * @param name name of region to create
     */
    public Region(String name) {
        this.name = name;
        regions.put(name, this);
    }
    
    /**
     * @param name name of region to retrieve
     * @return the region of specified name
     */
    public static Region getRegion(String name) {
        return regions.get(name);
    }
    
    /**
     * @return all regions
     */
    public static Collection<Region> getRegions() {
        return regions.values();
    }
    
    /**
     * save the region to the hashmap
     */
    public void save() {
        regions.put(name, this);
    }
    
    /**
     * remove the region from the hashmap and config
     */
    public void remove() {
        regionConfig.getConfig().set(name, null);
        regionConfig.saveConfig();
        regions.remove(name);
    }
    
    /**
     * @return name of the region
     */
    public String getName() {
        return name;
    }
    
    /**
     * set bounds of region using worldedit
     */
    public void setBoundsWithWorldEdit(Player player) {
        
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            return;
        }
        
        if (selection != null) {
            if (Objects.requireNonNull(selection.getWorld()).getName().equals(LobbyHandler.gamesWorld().getName())) {
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();
                World world = BukkitAdapter.adapt(selection.getWorld());
                
                setBounds(minx, miny, minz, maxx, maxy, maxz, world);
                
            }
        }
    }
    
    /**
     * set region bounds through parameters
     *
     * @param minx  minimum x bound
     * @param miny  minimum y bound
     * @param minz  minimum z bound
     * @param maxx  maximum x bound
     * @param maxy  maximum y bound
     * @param maxz  maximum z bound
     * @param world world region is in
     */
    public void setBounds(double minx, double miny, double minz, double maxx, double maxy, double maxz, World world) {
        regionConfig.getConfig().set(name + ".location.min.x", minx);
        regionConfig.getConfig().set(name + ".location.min.y", miny);
        regionConfig.getConfig().set(name + ".location.min.z", minz);
        regionConfig.getConfig().set(name + ".location.max.x", maxx);
        regionConfig.getConfig().set(name + ".location.max.y", maxy);
        regionConfig.getConfig().set(name + ".location.max.z", maxz);
        regionConfig.getConfig().set(name + ".location.world", world.getName());
        regionConfig.saveConfig();
        
        bounds[0][0] = minx;
        bounds[0][1] = miny;
        bounds[0][2] = minz;
        bounds[1][0] = maxx;
        bounds[1][1] = maxy;
        bounds[1][2] = maxz;
        this.world = world;
        save();
    }
    
    /**
     * @return array that holds bounds of region
     */
    public double[][] getBounds() {
        return bounds;
    }
    
    /**
     * @return world region is in
     */
    public World getWorld() {
        return world;
    }
    
    /**
     * @return name of inventory used
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * set inventory for region
     *
     * @param inventory inventory to set
     */
    public void setInventory(Inventory inventory) {
        regionConfig.getConfig().set(name + ".inventory", inventory.getName());
        regionConfig.saveConfig();
        this.inventory = inventory;
        save();
    }
    
    /**
     * @return gamemode for the region
     */
    public GameMode getGamemode() {
        return mode;
    }
    
    /**
     * @param mode gamemode to set for region
     */
    public void setGamemode(GameMode mode) {
        regionConfig.getConfig().set(name + ".gamemode", mode.toString());
        regionConfig.saveConfig();
        this.mode = mode;
        save();
    }
    
    /**
     * @return leave command for region
     */
    public String getLeave() {
        return leave;
    }
    
    /**
     * set what happens to a player when they leave a region
     *
     * @param leave string command or location
     */
    public void setLeave(String leave) {
        if (leave != null) regionConfig.getConfig().set(name + ".leave", leave);
        else regionConfig.getConfig().set(name + ".leave", "null");
        regionConfig.saveConfig();
        this.leave = leave;
        save();
    }
    
    /**
     * @return true if the region is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * enable a region if it is fully set up
     *
     * @param enabled boolean to enable region
     */
    public void setEnabled(boolean enabled) {
        if (getGamemode() != null && getInventory() != null && getBounds() != null && getWorld() != null) {
            regionConfig.getConfig().set(name + ".enabled", enabled);
            this.enabled = enabled;
            save();
        }
    }
    
    /**
     * @param location location to check
     * @return true if location is in region
     */
    public boolean containsLocation(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double minx = bounds[0][0];
        double miny = bounds[0][1];
        double minz = bounds[0][2];
        double maxx = bounds[1][0];
        double maxy = bounds[1][1];
        double maxz = bounds[1][2];
        
        if (minx < x && x < maxx) {
            if (miny < y && y < maxy) {
                if (minz < z && z < maxz) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return true if the region is the default region
     */
    public boolean isDefault() {
        return name.equals("default");
    }
    
}
