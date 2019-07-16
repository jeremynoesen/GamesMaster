package me.Jeremaster101.GamesMaster.Region;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.Region.Inventory.InventoryConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class RegionCreation {


    private final LobbyHandler lh = new LobbyHandler();

    public void removeRegion(Player player, String region) {

        if (region.equals("default")) {
            player.sendMessage(Message.ERROR_DEFAULT_REGION.replace("$ACTION$", "remove"));
            return;
        }
        if (RegionConfig.getConfig().get("regions." + region) != null) {
            RegionConfig.getConfig().set("regions." + region, null);
            RegionConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_REGION_REMOVED.replace("$REGION$", region));
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION.replace("$REGION$", region));
        }
    }

    public void addRegion(Player player, String region, String inv, String mode, String leavecmd) {

        if (RegionConfig.getConfig().get("regions." + region) != null) {
            player.sendMessage(Message.ERROR_REGION_EXISTS);
            return;
        }

        if (region.equals("default")) {
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
            return;
        }

        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region selection = null;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }

        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(inv)) {
            player.sendMessage(Message.ERROR_UNKNOWN_INV);
            return;
        }
        try {
            GameMode.valueOf(mode.toUpperCase());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_GAMEMODE);
            return;
        }

        if (selection != null) {
            if (Objects.requireNonNull(selection.getWorld()).getName().equals(lh.gamesWorld().getName())) {
                String areaname = "regions." + region;
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();

                RegionConfig.getConfig().set(areaname + ".location.min.x", minx);
                RegionConfig.getConfig().set(areaname + ".location.min.y", miny);
                RegionConfig.getConfig().set(areaname + ".location.min.z", minz);
                RegionConfig.getConfig().set(areaname + ".location.max.x", maxx);
                RegionConfig.getConfig().set(areaname + ".location.max.y", maxy);
                RegionConfig.getConfig().set(areaname + ".location.max.z", maxz);
                RegionConfig.getConfig().set(areaname + ".gamemode", mode);
                RegionConfig.getConfig().set(areaname + ".inventory", inv);
                if (leavecmd != null) RegionConfig.getConfig().set(areaname + ".leave", leavecmd.replace("_", " "));
                else RegionConfig.getConfig().set(areaname + ".leave", null);
                RegionConfig.saveConfig();

                player.sendMessage(Message.SUCCESS_REGION_SET.replace("$REGION$", region));

            } else {
                player.sendMessage(Message.ERROR_WORLD);
            }
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }

    public void updateRegionBounds(Player player, String region) {

        if (RegionConfig.getConfig().get("regions." + region) == null) {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return;
        }

        if (region.equals("default")) {
            player.sendMessage(Message.ERROR_DEFAULT_REGION);
            return;
        }

        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        Region selection = null;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }

        if (selection != null) {
            if (Objects.requireNonNull(selection.getWorld()).getName().equals(lh.gamesWorld().getName())) {
                String areaname = "regions." + region;
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();

                RegionConfig.getConfig().set(areaname + ".location.min.x", minx);
                RegionConfig.getConfig().set(areaname + ".location.min.y", miny);
                RegionConfig.getConfig().set(areaname + ".location.min.z", minz);
                RegionConfig.getConfig().set(areaname + ".location.max.x", maxx);
                RegionConfig.getConfig().set(areaname + ".location.max.y", maxy);
                RegionConfig.getConfig().set(areaname + ".location.max.z", maxz);
                RegionConfig.saveConfig();

                player.sendMessage(Message.SUCCESS_UPDATED_REGION_BOUNDS.replace("$REGION$", region));

            } else {
                player.sendMessage(Message.ERROR_WORLD);
            }
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }

    public void updateRegionInv(Player player, String region, String inv) {

        if (RegionConfig.getConfig().get("regions." + region) == null) {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return;
        }

        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(inv)) {
            player.sendMessage(Message.ERROR_UNKNOWN_INV);
        } else {
            RegionConfig.getConfig().set("regions." + region + ".inventory", inv);
            RegionConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_UPDATED_REGION_INV.replace("$REGION$", region).replace("$INV$", inv));
        }
    }

    public void updateRegionMode(Player player, String region, String mode) {

        if (RegionConfig.getConfig().get("regions." + region) == null) {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return;
        }

        try {
            GameMode.valueOf(mode.toUpperCase());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_GAMEMODE);
            return;
        }

        RegionConfig.getConfig().set("regions." + region + ".gamemode", mode.toUpperCase());
        RegionConfig.saveConfig();
        player.sendMessage(Message.SUCCESS_UPDATED_REGION_MODE.replace("$REGION$", region).replace("$MODE$", mode.toLowerCase()));
    }

    public void updateRegionLeave(Player player, String region, String leave) {

        if (RegionConfig.getConfig().get("regions." + region) == null) {
            player.sendMessage(Message.ERROR_UNKNOWN_REGION);
            return;
        }

        if (leave != null) {
            RegionConfig.getConfig().set("regions." + region + ".leave", leave);
            player.sendMessage(Message.SUCCESS_UPDATED_REGION_LEAVE.replace("$REGION$", region).replace("$CMD$", leave));
        }
        else {
            RegionConfig.getConfig().set("regions." + region + ".leave", null);
            player.sendMessage(Message.SUCCESS_REMOVED_REGION_LEAVE.replace("$REGION$", region));
        }
        RegionConfig.saveConfig();
    }
}
