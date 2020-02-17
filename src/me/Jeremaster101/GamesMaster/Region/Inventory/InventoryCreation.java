package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Message;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class InventoryCreation {
    
    private ConfigManager inventoryConfig = Configs.getConfig(ConfigType.INVENTORY);

    public void addInventories(String inv, Player player) {
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(inv)) {
            assert invs != null;
            invs.add(inv);
            player.sendMessage(Message.SUCCESS_INV_ADDED.replace("$INV$", inv));
            inventoryConfig.getConfig().set("inventories", invs);
            inventoryConfig.saveConfig();
        } else {
            player.sendMessage(Message.ERROR_INV_EXISTS);
        }
    }

    public void deleteInventories(String inv, Player player) {
        List<String> invs = inventoryConfig.getConfig().getStringList("inventories");
        if (invs != null && invs.contains(inv)) {
            if(inv.equals("default")) {
                player.sendMessage(Message.ERROR_DEFAULT_INV.replace("$ACTION$", "remove"));
                return;
            }
            player.sendMessage(Message.SUCCESS_INV_REMOVED.replace("$INV$", inv));
            invs.remove(inv);
            inventoryConfig.getConfig().set("inventories", invs);
            inventoryConfig.saveConfig();
            for (Player allOn : Bukkit.getOnlinePlayers()) {
                GMPlayer gmplayer = GMPlayer.getPlayer(allOn);
                if (gmplayer.getCurrentRegion() != null) {
                    gmplayer.getData().getDataFile().set("inventories." + inv, null);
                    gmplayer.getData().savePlayerData();
                }
            }
            for (OfflinePlayer allOff : Bukkit.getOfflinePlayers()) {
                GMPlayer gmplayer = GMPlayer.getPlayer(allOff.getPlayer());
                if (gmplayer.getCurrentRegion() != null) {
                    gmplayer.getData().getDataFile().set("inventories." + inv, null);
                    gmplayer.getData().savePlayerData();
                }
            }
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_INV);
        }
    }
}
