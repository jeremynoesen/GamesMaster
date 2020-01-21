package me.Jeremaster101.GamesMaster.Region;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class Region {//todo finish this class
    
    private final LobbyHandler lh = new LobbyHandler();
    private String region;
    private Player player;
    
    static ConfigManager regionConfig = Configs.getConfig(ConfigType.REGION);
    
    public Region(Player player, String region) {//todo make worldguard region if installed
        this.player = player;
        if (region != null) {
            this.region = region;
            if (regionConfig.getConfig().getConfigurationSection(region) == null) {
                
                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");//todo make option if there is no worldedit
                com.sk89q.worldedit.regions.Region selection;
                try {
                    selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
                } catch (Exception e) {
                    player.sendMessage(Message.ERROR_NULL_BOUNDS);
                    return;
                }
                
                if (selection != null) {
                    if (Objects.requireNonNull(selection.getWorld()).getName().equals(lh.gamesWorld().getName())) {
                        String regionName = region;
                        double minx = selection.getMinimumPoint().getX();
                        double miny = selection.getMinimumPoint().getY();
                        double minz = selection.getMinimumPoint().getZ();
                        double maxx = selection.getMaximumPoint().getX();
                        double maxy = selection.getMaximumPoint().getY();
                        double maxz = selection.getMaximumPoint().getZ();
                        
                        regionConfig.getConfig().set(regionName + ".enabled", false);
                        regionConfig.getConfig().set(regionName + ".location.min.x", minx);
                        regionConfig.getConfig().set(regionName + ".location.min.y", miny);
                        regionConfig.getConfig().set(regionName + ".location.min.z", minz);
                        regionConfig.getConfig().set(regionName + ".location.max.x", maxx);
                        regionConfig.getConfig().set(regionName + ".location.max.y", maxy);
                        regionConfig.getConfig().set(regionName + ".location.max.z", maxz);
                        regionConfig.saveConfig();
                        
                        player.sendMessage(Message.SUCCESS_REGION_SET.replace("$REGION$", region));
                        
                    } else {
                        player.sendMessage(Message.ERROR_WORLD);
                    }
                }
            }
        }
    }
    
    public static Region getRegion(Player player, String region) {
        if (regionConfig.getConfig().get(region) != null) {
            return new Region(player, region);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return null;
        }
    }
    
    boolean exists() {
        if (regionConfig.getConfig().get(region) != null)
            return true;
        else player.sendMessage(Message.ERROR_UNKNOWN_REGION);
        return false;
    }
    
    public void remove() {
        
        if (region.equals("default")) {
            player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "remove"));
        } else {
            if (exists()) {
                regionConfig.getConfig().set(region, null);
                regionConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_REGION_REMOVED.replace("$REGION$", region));
            } else {
                player.sendMessage(Message.ERROR_UNKNOWN_REGION.replace("$REGION$", region));
            }
        }
    }
    
    public void setBounds() {
        
        if (exists()) {
            if (!region.equals("default")) {
                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                
                com.sk89q.worldedit.regions.Region selection;
                try {
                    selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
                } catch (Exception e) {
                    player.sendMessage(Message.ERROR_NULL_BOUNDS);
                    return;
                }
                
                if (selection != null) {
                    if (Objects.requireNonNull(selection.getWorld()).getName().equals(lh.gamesWorld().getName())) {
                        String regionName = region;
                        double minx = selection.getMinimumPoint().getX();
                        double miny = selection.getMinimumPoint().getY();
                        double minz = selection.getMinimumPoint().getZ();
                        double maxx = selection.getMaximumPoint().getX();
                        double maxy = selection.getMaximumPoint().getY();
                        double maxz = selection.getMaximumPoint().getZ();
                        
                        regionConfig.getConfig().set(regionName + ".location.min.x", minx);
                        regionConfig.getConfig().set(regionName + ".location.min.y", miny);
                        regionConfig.getConfig().set(regionName + ".location.min.z", minz);
                        regionConfig.getConfig().set(regionName + ".location.max.x", maxx);
                        regionConfig.getConfig().set(regionName + ".location.max.y", maxy);
                        regionConfig.getConfig().set(regionName + ".location.max.z", maxz);
                        regionConfig.saveConfig();
                        
                        player.sendMessage(Message.SUCCESS_UPDATED_REGION_BOUNDS.replace("$REGION$", region)); //todo match message names to actual methods
                        
                    } else {
                        player.sendMessage(Message.ERROR_WORLD);
                    }
                } else {
                    player.sendMessage(Message.ERROR_NULL_BOUNDS);
                }
            } else
                player.sendMessage(Message.ERROR_DEFAULT_REGION);
        }
    }
    
    public String getInventory() {
        if (exists() && regionConfig.getConfig().get(region + ".inventory") != null) {
            return regionConfig.getConfig().getString(region + ".inventory");
        }
        return null;
    }
    
    public void setInventory(String inv) {
        
        if (exists()) {
            List<String> invs = Configs.getConfig(ConfigType.INVENTORY).getConfig().getStringList("inventories");
            if (invs == null || !invs.contains(inv)) {
                player.sendMessage(Message.ERROR_UNKNOWN_INV);
            } else {
                regionConfig.getConfig().set(region + ".inventory", inv);
                regionConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_UPDATED_REGION_INV.replace("$REGION$", region).replace("$INV$", inv));
            }
        }
    }
    
    public GameMode getGamemode() {
        if (exists() && regionConfig.getConfig().get(region + ".gamemode") != null) {
            return GameMode.valueOf(regionConfig.getConfig().getString(region + ".gamemode"));
        }
        return null;
    }
    
    public void setGamemode(String mode) {
        
        if (exists()) {
            
            try {
                GameMode.valueOf(mode.toUpperCase());
            } catch (Exception e) {
                player.sendMessage(Message.ERROR_GAMEMODE);
                return;
            }
            
            regionConfig.getConfig().set(region + ".gamemode", mode.toUpperCase());
            regionConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_REGION_MODE.replace("$REGION$", region).replace("$MODE$", mode.toLowerCase()));
        }
    }
    
    public String getLeave() {
        if (!region.equals("default")) {
            if (exists() && regionConfig.getConfig().get(region + ".leave") != null) {
                return regionConfig.getConfig().getString(region + ".leave");
            }
        }
        return null;
    }
    
    public void setLeave(String leave) {
        if (!region.equals("default")) {
            if (exists()) {
                if (leave != null) {
                    regionConfig.getConfig().set(region + ".leave", leave);
                    player.sendMessage(Message.SUCCESS_UPDATED_REGION_LEAVE.replace("$REGION$", region).replace("$CMD$", leave));
                } else {
                    regionConfig.getConfig().set(region + ".leave", "null");
                    player.sendMessage(Message.SUCCESS_REMOVED_REGION_LEAVE.replace("$REGION$", region));
                }
                regionConfig.saveConfig();
            }
        } else
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
    }
    
    public boolean isEnabled() {
        if (!region.equals("default")) {
            if (exists() && regionConfig.getConfig().get(region + ".enabled") != null) {
                return regionConfig.getConfig().getBoolean(region + ".enabled");
            }
        } else
            return true;
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        if (!region.equals("default")) {
            if (exists()) {
                if (getGamemode() != null && getInventory() != null) { //todo finish
                    regionConfig.getConfig().set(region + ".enabled", enabled);
                } else
                    player.sendMessage(Message.ERROR_CANT_ENABLE);
            }
        } else
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
    }
    
}
