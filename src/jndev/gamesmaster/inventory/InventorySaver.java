package jndev.gamesmaster.inventory;

import jndev.gamesmaster.player.GMPlayer;
import jndev.gamesmaster.inventorytype.InventoryType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;

/**
 * saves saved player inventories
 */
public class InventorySaver {
    
    /**
     * saves all inventories for all loaded players
     */
    public static void saveAll() {
        for(GMPlayer gmplayer : GMPlayer.getGMPlayers()) {
            saveAllForPlayer(gmplayer.getPlayer());
        }
    }
    
    /**
     * saves all inventory types for a specified player
     *
     * @param player player owner of inventories
     */
    public static void saveAllForPlayer(Player player) {
        GMPlayer.getGMPlayer(player).getPlayerData().getDataFile().set("inventories", null);
        for(InventoryType type : InventoryType.getInventoryTypes()) {
            save(type, player);
        }
    }
    
    /**
     * saves one inventory for a player to file
     *
     * @param inventoryType inventory type to save
     * @param player player owner of inventory
     */
    public static void save(InventoryType inventoryType, Player player) {
        GMPlayer gmplayer = GMPlayer.getGMPlayer(player);
        YamlConfiguration playerFile = gmplayer.getPlayerData().getDataFile();
        Inventory inventory = gmplayer.getInventory(inventoryType);
        String invName = "inventories." + inventoryType.getName();
        
        int exp = inventory.getExp();
        double health = inventory.getHealth();
        int hunger = inventory.getHunger();
        float saturation = inventory.getSaturation();
        float exhaustion = inventory.getExhaustion();
        int air = inventory.getRemainingAir();
        float fall = inventory.getFallDistance();
        int fire = inventory.getFireTicks();
        int slot = inventory.getSelectedSlot();
        Collection<PotionEffect> effects = inventory.getEffects();
        HashMap<Integer, ItemStack> contents = inventory.getContents();
        
        playerFile.set(invName + ".experience", exp);
        playerFile.set(invName + ".health", health);
        playerFile.set(invName + ".hunger", hunger);
        playerFile.set(invName + ".saturation", saturation);
        playerFile.set(invName + ".exhaustion", exhaustion);
        playerFile.set(invName + ".remaining-air", air);
        playerFile.set(invName + ".fall-distance", fall);
        playerFile.set(invName + ".fire-ticks", fire);
        playerFile.set(invName + ".selected-slot", slot);
        
        playerFile.set(invName + ".effects", null);
        int effectCounter = 0;
        for (PotionEffect effect : effects) {
            playerFile.set(invName + ".effects." + effectCounter, effect);
        }
        
        playerFile.set(invName + ".contents", null);
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                playerFile.set(invName + ".contents." + i, contents.get(i));
            }
        }
        
        gmplayer.getPlayerData().savePlayerData();
    }
    
}
