package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Message.Message;
import me.Jeremaster101.GamesMaster.GMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class InventoryCreation {

    public void addInventories(String inv, Player player) {
        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(inv)) {
            assert invs != null;
            invs.add(inv);
            player.sendMessage(Message.SUCCESS_INV_ADDED.replace("$INV$", inv));
            InventoryConfig.getConfig().set("inventories", invs);
            InventoryConfig.saveConfig();
        } else {
            player.sendMessage(Message.ERROR_INV_EXISTS);
        }
    }

    public void deleteInventories(String inv, Player player) {
        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs != null && invs.contains(inv)) {
            if(inv.equals("default")) {
                player.sendMessage(Message.ERROR_DEFAULT_INV.replace("$ACTION$", "remove"));
                return;
            }
            player.sendMessage(Message.SUCCESS_INV_REMOVED.replace("$INV$", inv));
            invs.remove(inv);
            InventoryConfig.getConfig().set("inventories", invs);
            InventoryConfig.saveConfig();
            for (Player allOn : Bukkit.getOnlinePlayers()) {
                GMPlayer gmplayer = GMPlayer.getPlayerData(allOn);
                if (gmplayer.getCurrentRegion() != null) {
                    gmplayer.getDataFile().set("inventories." + inv, null);
                    gmplayer.savePlayerData();
                }
            }
            for (OfflinePlayer allOff : Bukkit.getOfflinePlayers()) {
                GMPlayer gmplayer = GMPlayer.getPlayerData(allOff.getPlayer());
                if (gmplayer.getCurrentRegion() != null) {
                    gmplayer.getDataFile().set("inventories." + inv, null);
                    gmplayer.savePlayerData();
                }
            }
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_INV);
        }
    }
}
