package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Region.Inventory.Inventory;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInventoryBuilder {
    
    public void build(Player player, Inventory inventory) {
        GMPlayer gmplayer = GMPlayer.getPlayer(player);
        PlayerData data = gmplayer.getPlayerData();
        org.bukkit.inventory.PlayerInventory inv = player.getInventory();
        String invName = inventory.getName();
        
            if (data.getDataFile().get(invName + ".experience") != null) {
                player.getPlayer().giveExpLevels(-1000000000);
                player.getPlayer().giveExp(data.getDataFile().getInt(invName + ".experience"));
            } else
                player.getPlayer().giveExpLevels(-1000000000);
            
            if (data.getDataFile().get(invName + ".health") != null) {
                player.getPlayer().setHealth(data.getDataFile().getDouble(invName + ".health"));
            } else
                player.getPlayer().setHealth(20);
            
            if (data.getDataFile().get(invName + ".hunger.hunger") != null) {
                player.getPlayer().setFoodLevel(data.getDataFile().getInt(invName + ".hunger.hunger"));
            } else
                player.getPlayer().setFoodLevel(20);
            
            if (data.getDataFile().get(invName + ".hunger.saturation") != null) {
                player.getPlayer().setSaturation(Float.parseFloat(data.getDataFile().get(invName + ".hunger.saturation").toString()));
            } else
                player.getPlayer().setSaturation(5);
            
            if (data.getDataFile().get(invName + ".hunger.exhaustion") != null) {
                player.getPlayer().setExhaustion(Float.parseFloat(data.getDataFile().get(invName + ".hunger.exhaustion").toString()));
            } else
                player.getPlayer().setExhaustion(0);
            
            if (data.getDataFile().get(invName + ".remaining-air") != null) {
                player.getPlayer().setRemainingAir(data.getDataFile().getInt(invName + ".remaining-air"));
            } else
                player.getPlayer().setRemainingAir(player.getMaximumAir());
            
            if (data.getDataFile().get(invName + ".fall-distance") != null) {
                player.getPlayer().setFallDistance(Float.parseFloat(data.getDataFile().get(invName + ".fall-distance").toString()));
            } else
                player.getPlayer().setFallDistance(0);
            
            if (data.getDataFile().get(invName + ".fire-ticks") != null) {
                player.getPlayer().setFireTicks(data.getDataFile().getInt(invName + ".fire-ticks"));
            } else
                player.getPlayer().setFireTicks(0);
            
            if (data.getDataFile().get(invName + ".selected-slot") != null) {
                player.getPlayer().getInventory().setHeldItemSlot(data.getDataFile().getInt(invName + ".selected-slot"));
            } else
                player.getPlayer().getInventory().setHeldItemSlot(0);
            
            for (PotionEffect allEffects : player.getActivePotionEffects()) {
                player.removePotionEffect(allEffects.getType());
            }
            
            if (data.getDataFile().get(invName + ".effects", null) != null) {
                for (int effect = 0; effect < data.getDataFile().getConfigurationSection(invName + ".effects").getKeys(false)
                        .size(); effect++) {
                    PotionEffect peffect = (PotionEffect) data.getDataFile().get(invName + ".effects." + effect);
                    player.addPotionEffect(peffect);
                }
            }
            
            for (int slotcounter = 0; slotcounter < inv.getSize(); slotcounter++) {
                ItemStack slot = new ItemStack(Material.AIR, 0);
                if (data.getDataFile().get(invName + ".contents." + slotcounter, null) != null) {
                    slot = (ItemStack) data.getDataFile().get(invName + ".contents." + slotcounter);
                    inv.setItem(slotcounter, slot);
                } else {
                    inv.setItem(slotcounter, slot);
                }
            }
        }
        
        if (p.getGameMode() != mode) {
            p.setGameMode(mode);
        }
        
        if (mode.equals(GameMode.SURVIVAL)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                }
            }.runTaskLater(GamesMaster.getInstance(), 1);
        }
        
        gmplayer.setCurrentRegion(region);
        
        p.updateInventory();
        
        data.savePlayerData();
    }
}
