package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryCreation {


    private final Message msg = new Message();

    public void addInventories(String inv, Player player) {
        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs == null || !invs.contains(inv)) {
            assert invs != null;
            invs.add(inv);
            player.sendMessage(msg.SUCCESS_INV_ADDED.replace("$INV$", inv));
            InventoryConfig.getConfig().set("inventories", invs);
            InventoryConfig.saveConfig();
        } else {
            player.sendMessage(msg.ERROR_INV_EXISTS);
        }
    }

    public void deleteInventories(String inv, Player player) {
        List<String> invs = InventoryConfig.getConfig().getStringList("inventories");
        if (invs != null && invs.contains(inv)) {
            if(inv.equals("default")) {
                player.sendMessage(msg.ERROR_DEFAULT_INV.replace("$ACTION$", "remove"));
                return;
            }
            player.sendMessage(msg.SUCCESS_INV_REMOVED.replace("$INV$", inv));
            invs.remove(inv);
            InventoryConfig.getConfig().set("inventories", invs);
            InventoryConfig.saveConfig();
            for (Player allOn : Bukkit.getOnlinePlayers()) {
                File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                        allOn.getUniqueId().toString() + ".yml");
                YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
                if (cur.get("current") != null) {
                    cur.set(inv, null);
                    try {
                        cur.save(playerInvConfigFile);
                    } catch (IOException exce) {
                        exce.printStackTrace();
                    }
                }
            }
            for (OfflinePlayer allOff : Bukkit.getOfflinePlayers()) {
                File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                        allOff.getUniqueId().toString() + ".yml");
                YamlConfiguration cur = YamlConfiguration.loadConfiguration(playerInvConfigFile);
                if (cur.get("current") != null) {
                    cur.set(inv, null);
                    try {
                        cur.save(playerInvConfigFile);
                    } catch (IOException exce) {
                        exce.printStackTrace();
                    }
                }
            }
        } else {
            player.sendMessage(msg.ERROR_UNKNOWN_INV);
        }
    }
}
