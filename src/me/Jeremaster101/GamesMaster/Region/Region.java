package me.Jeremaster101.GamesMaster.Region;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class Region {//todo finish this class
    
    private final LobbyHandler lh = new LobbyHandler();
    private String region;
    private Player player;
    
    public Region(Player player, String region) {//todo make worldguard region if installed
        this.player = player;
        if (region != null) {
            this.region = region;
            if (RegionConfig.getConfig().getConfigurationSection(region) == null) {
                //todo remove messages from class
                
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
                        
                        RegionConfig.getConfig().set(regionName + ".enabled", false);
                        RegionConfig.getConfig().set(regionName + ".location.min.x", minx);
                        RegionConfig.getConfig().set(regionName + ".location.min.y", miny);
                        RegionConfig.getConfig().set(regionName + ".location.min.z", minz);
                        RegionConfig.getConfig().set(regionName + ".location.max.x", maxx);
                        RegionConfig.getConfig().set(regionName + ".location.max.y", maxy);
                        RegionConfig.getConfig().set(regionName + ".location.max.z", maxz);
                        RegionConfig.saveConfig();
                        
                        player.sendMessage(Message.SUCCESS_REGION_SET.replace("$REGION$", region));
                        
                    } else {
                        player.sendMessage(Message.ERROR_WORLD);
                    }
                }
            }
        }
    }
    
    public static Region getRegion(Player player, String region) {
        if (RegionConfig.getConfig().get(region) != null) {
            return new Region(player, region);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return null;
        }
    }
    
    public void remove() {
        
        if (region.equals("default")) {
            player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "remove"));
        } else {
            if (RegionConfig.getConfig().get(region) != null) {
                RegionConfig.getConfig().set(region, null);
                RegionConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_REGION_REMOVED.replace("$REGION$", region));
            } else {
                player.sendMessage(Message.ERROR_UNKNOWN_REGION.replace("$REGION$", region));
            }
        }
    }
    
    public void setBounds() {
        
        if (RegionConfig.getConfig().get(region) != null) {
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
                        
                        RegionConfig.getConfig().set(regionName + ".location.min.x", minx);
                        RegionConfig.getConfig().set(regionName + ".location.min.y", miny);
                        RegionConfig.getConfig().set(regionName + ".location.min.z", minz);
                        RegionConfig.getConfig().set(regionName + ".location.max.x", maxx);
                        RegionConfig.getConfig().set(regionName + ".location.max.y", maxy);
                        RegionConfig.getConfig().set(regionName + ".location.max.z", maxz);
                        RegionConfig.saveConfig();
                        
                        player.sendMessage(Message.SUCCESS_UPDATED_REGION_BOUNDS.replace("$REGION$", region));
                        
                    } else {
                        player.sendMessage(Message.ERROR_WORLD);
                    }
                } else {
                    player.sendMessage(Message.ERROR_NULL_BOUNDS);
                }
            } else
                player.sendMessage(Message.ERROR_DEFAULT_REGION);
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
    }
    
    public String getInventory() {
        if (RegionConfig.getConfig().get(region) != null &&
                RegionConfig.getConfig().get(region + ".inventory") != null) {
            return RegionConfig.getConfig().getString(region + ".inventory");
        }
        return null;
    }
    
    public void setInventory(String inv) {
        
        if (RegionConfig.getConfig().get(region) != null) {
            List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
            if (invs == null || !invs.contains(inv)) {
                player.sendMessage(Message.ERROR_UNKNOWN_INV);
            } else {
                RegionConfig.getConfig().set(region + ".inventory", inv);
                RegionConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_UPDATED_REGION_INV.replace("$REGION$", region).replace("$INV$", inv));
            }
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
    }
    
    public GameMode getGamemode() {
        if (RegionConfig.getConfig().get(region) != null &&
                RegionConfig.getConfig().get(region + ".gamemode") != null) {
            return GameMode.valueOf(RegionConfig.getConfig().getString(region + ".gamemode"));
        }
        return null;
    }
    
    public void setGamemode(String mode) {
        
        if (RegionConfig.getConfig().get(region) != null) {
            
            try {
                GameMode.valueOf(mode.toUpperCase());
            } catch (Exception e) {
                player.sendMessage(Message.ERROR_GAMEMODE);
                return;
            }
            
            RegionConfig.getConfig().set(region + ".gamemode", mode.toUpperCase());
            RegionConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_REGION_MODE.replace("$REGION$", region).replace("$MODE$", mode.toLowerCase()));
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
    }
    
    public String getLeave() {
        if (!region.equals("default")) {
            if (RegionConfig.getConfig().get(region) != null &&
                    RegionConfig.getConfig().get(region + ".leave") != null) {
                return RegionConfig.getConfig().getString(region + ".leave");
            }
        }
        return null;
    }
    
    public void setLeave(String leave) {
        if (!region.equals("default")) {
            if (RegionConfig.getConfig().get(region) != null) {
                if (leave != null) {
                    RegionConfig.getConfig().set(region + ".leave", leave);
                    player.sendMessage(Message.SUCCESS_UPDATED_REGION_LEAVE.replace("$REGION$", region).replace("$CMD$", leave));
                } else {
                    RegionConfig.getConfig().set(region + ".leave", "null");
                    player.sendMessage(Message.SUCCESS_REMOVED_REGION_LEAVE.replace("$REGION$", region));
                }
                RegionConfig.saveConfig();
            } else
                player.sendMessage(Message.ERROR_UNKNOWN_REGION);
        } else
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
    }
    
    public boolean isEnabled() {
        if (!region.equals("default")) {
            if (RegionConfig.getConfig().get(region) != null &&
                    RegionConfig.getConfig().get(region + ".enabled") != null) {
                return RegionConfig.getConfig().getBoolean(region + ".enabled");
            }
        } else
            return true;
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        if (!region.equals("default")) {
            if (RegionConfig.getConfig().get(region) != null) {
                if (getGamemode() != null && getInventory() != null) { //todo finish
                    RegionConfig.getConfig().set(region + ".enabled", enabled);
                } else
                    player.sendMessage(Message.ERROR_CANT_ENABLE);
            } else
                player.sendMessage(Message.ERROR_UNKNOWN_REGION);
        } else
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
    }
    
}
