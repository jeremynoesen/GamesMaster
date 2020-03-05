package jndev.gamesmaster.inventory;

import jndev.gamesmaster.player.GMPlayer;
import jndev.gamesmaster.player.PlayerFile;
import jndev.gamesmaster.inventorytype.InventoryType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * reads saved player invs from player file and converts it to an Inventory
 */
public class InventoryBuilder {
    
    /**
     * builds all saved player inventories
     *
     * @param player player to build inventories for
     */
    public static void buildAll(Player player) {
        for(InventoryType inventoryType : InventoryType.getInventoryTypes()) {
            build(inventoryType, player);
        }
    }
    
    /**
     * build player inventory from file
     *
     * @param player player to build for
     * @param inventoryType inventory type to build
     */
    public static void build(InventoryType inventoryType, Player player) {
        GMPlayer gmplayer = GMPlayer.getGMPlayer(player);
        PlayerFile data = gmplayer.getPlayerData();
        String invName = "inventories." + inventoryType.getName();
        Inventory inventory = new Inventory(inventoryType, player);
        
        if (data.getDataFile().get(invName + ".experience") != null) {
            inventory.setExp(data.getDataFile().getInt(invName + ".experience"));
        } else
            inventory.setExp(0);
        
        if (data.getDataFile().get(invName + ".health") != null) {
            inventory.setHealth(data.getDataFile().getDouble(invName + ".health"));
        } else
            inventory.setHealth(20);
        
        if (data.getDataFile().get(invName + ".hunger.hunger") != null) {
            inventory.setHunger(data.getDataFile().getInt(invName + ".hunger"));
        } else
            inventory.setHunger(20);
        
        if (data.getDataFile().get(invName + ".hunger.saturation") != null) {
            inventory.setSaturation(Float.parseFloat(data.getDataFile().getString(invName + ".saturation")));
        } else
            inventory.setSaturation(5);
        
        if (data.getDataFile().get(invName + ".hunger.exhaustion") != null) {
            inventory.setExhaustion(Float.parseFloat(data.getDataFile().getString(invName + ".exhaustion")));
        } else
            inventory.setExhaustion(0);
        
        if (data.getDataFile().get(invName + ".remaining-air") != null) {
            inventory.setRemainingAir(data.getDataFile().getInt(invName + ".remaining-air"));
        } else
            inventory.setRemainingAir(player.getMaximumAir());
        
        if (data.getDataFile().get(invName + ".fall-distance") != null) {
            inventory.setFallDistance(Float.parseFloat(data.getDataFile().get(invName + ".fall-distance").toString()));
        } else
            inventory.setFallDistance(0);
        
        if (data.getDataFile().get(invName + ".fire-ticks") != null) {
            inventory.setFireTicks(data.getDataFile().getInt(invName + ".fire-ticks"));
        } else
            inventory.setFireTicks(0);
        
        if (data.getDataFile().get(invName + ".selected-slot") != null) {
            inventory.setSelectedSlot(data.getDataFile().getInt(invName + ".selected-slot"));
        } else
            inventory.setSelectedSlot(0);
        
        Collection<PotionEffect> effects = new ArrayList<>();
        if (data.getDataFile().get(invName + ".effects", null) != null) {
            for (int effect = 0; effect < data.getDataFile().getConfigurationSection(invName + ".effects").getKeys(false)
                    .size(); effect++) {
                PotionEffect peffect = (PotionEffect) data.getDataFile().get(invName + ".effects." + effect);
                effects.add(peffect);
            }
        }
        inventory.setEffects(effects);
        
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
        inventory.setContents(contents);
    }
}
