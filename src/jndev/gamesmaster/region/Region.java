package jndev.gamesmaster.region;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import jndev.gamesmaster.lobby.LobbyHandler;
import jndev.gamesmaster.region.inventory.inventorytype.InventoryType;
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
    private static HashMap<String, Region> regions = new HashMap<>();
    private String name;
    private InventoryType inventoryType;
    private String leave;
    private GameMode mode;
    private boolean enabled;
    private double[][] bounds = new double[2][3];
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
     * remove the region from the hashmap and config
     */
    public void remove() {
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
        bounds[0][0] = minx;
        bounds[0][1] = miny;
        bounds[0][2] = minz;
        bounds[1][0] = maxx;
        bounds[1][1] = maxy;
        bounds[1][2] = maxz;
        this.world = world;
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
    public InventoryType getInventoryType() {
        return inventoryType;
    }
    
    /**
     * set inventory for region
     *
     * @param inventoryType inventory to set
     */
    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
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
        this.mode = mode;
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
        this.leave = leave;
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
        if (getGamemode() != null && getInventoryType() != null && getBounds() != null && getWorld() != null) {
            this.enabled = enabled;
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
