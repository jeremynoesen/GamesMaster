package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Region.Inventory.Inventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * reads saved player invs from player file and converts it to a PlayerInventory
 */
public class PlayerInventoryBuilder {
    
    /**
     * builds all saved player inventories
     *
     * @param player player to build inventories for
     */
    public void buildAll(Player player) {
        for(Inventory inventory : Inventory.getInventories()) {
            build(player, inventory);
        }
    }
    
    /**
     * build player inventory from file
     *
     * @param player player to build for
     * @param inventory inventory type to build
     */
    public void build(Player player, Inventory inventory) {
        GMPlayer gmplayer = GMPlayer.getPlayer(player);
        PlayerData data = gmplayer.getPlayerData();
        String invName = inventory.getName();
        PlayerInventory playerInventory = new PlayerInventory(player, inventory);
        
        if (data.getDataFile().get(invName + ".experience") != null) {
            playerInventory.setExp(data.getDataFile().getInt(invName + ".experience"));
        } else
            playerInventory.setExp(-1000000000);
        
        if (data.getDataFile().get(invName + ".health") != null) {
            playerInventory.setHealth(data.getDataFile().getDouble(invName + ".health"));
        } else
            playerInventory.setHealth(20);
        
        if (data.getDataFile().get(invName + ".hunger.hunger") != null) {
            playerInventory.setHunger(data.getDataFile().getInt(invName + ".hunger.hunger"));
        } else
            playerInventory.setHunger(20);
        
        if (data.getDataFile().get(invName + ".hunger.saturation") != null) {
            playerInventory.setSaturation(Float.parseFloat(data.getDataFile().getString(invName + ".hunger.saturation")));
        } else
            playerInventory.setSaturation(5);
        
        if (data.getDataFile().get(invName + ".hunger.exhaustion") != null) {
            playerInventory.setExhaustion(Float.parseFloat(data.getDataFile().getString(invName + ".hunger.exhaustion")));
        } else
            playerInventory.setExhaustion(0);
        
        if (data.getDataFile().get(invName + ".remaining-air") != null) {
            playerInventory.setAir(data.getDataFile().getInt(invName + ".remaining-air"));
        } else
            playerInventory.setAir(player.getMaximumAir());
        
        if (data.getDataFile().get(invName + ".fall-distance") != null) {
            playerInventory.setFall(Float.parseFloat(data.getDataFile().get(invName + ".fall-distance").toString()));
        } else
            playerInventory.setFall(0);
        
        if (data.getDataFile().get(invName + ".fire-ticks") != null) {
            playerInventory.setFire(data.getDataFile().getInt(invName + ".fire-ticks"));
        } else
            playerInventory.setFire(0);
        
        if (data.getDataFile().get(invName + ".selected-slot") != null) {
            playerInventory.setSlot(data.getDataFile().getInt(invName + ".selected-slot"));
        } else
            playerInventory.setSlot(0);
        
        Collection<PotionEffect> effects = new ArrayList<>();
        if (data.getDataFile().get(invName + ".effects", null) != null) {
            for (int effect = 0; effect < data.getDataFile().getConfigurationSection(invName + ".effects").getKeys(false)
                    .size(); effect++) {
                PotionEffect peffect = (PotionEffect) data.getDataFile().get(invName + ".effects." + effect);
                effects.add(peffect);
            }
        }
        playerInventory.setEffects(effects);
        
        HashMap<Integer, ItemStack> contents = new HashMap<>();
        for (int slotcounter = 0; slotcounter < player.getInventory().getSize(); slotcounter++) {
            ItemStack item = new ItemStack(Material.AIR, 0);
            if (data.getDataFile().get(invName + ".contents." + slotcounter, null) != null) {
                item = (ItemStack) data.getDataFile().get(invName + ".contents." + slotcounter);
                contents.put(slotcounter, item);
            } else {
                contents.put(slotcounter, item);
            }
        }
        playerInventory.setContents(contents);
        
        PlayerInventory.addPlayerInventory(player, inventory, playerInventory);
    }
}
