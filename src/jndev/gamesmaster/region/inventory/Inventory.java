package jndev.gamesmaster.region.inventory;

import jndev.gamesmaster.region.inventory.inventorytype.InventoryType;
import jndev.gamesmaster.GamesMaster;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * class to handle inventory saves
 */
public class Inventory {
    
    private static HashMap<InventoryType, HashMap<Player, Inventory>> inventories = new HashMap<>();
    private Player player;
    private int exp;
    private double health;
    private int hunger;
    private float saturation;
    private float exhaustion;
    private int air;
    private float fall;
    private int fire;
    private int slot;
    private Collection<PotionEffect> effects = new ArrayList<>();
    private HashMap<Integer, ItemStack> contents = new HashMap<>();
    private InventoryType inventoryType;
    
    /**
     * creates a new inventory save
     *
     * @param inventoryType inventory type to make
     * @param player owner of inventory
     */
    public Inventory(InventoryType inventoryType, Player player) {
        this.player = player;
        this.inventoryType = inventoryType;
        HashMap<Player, Inventory> inv = new HashMap<>(inventories.get(inventoryType));
        inv.put(player, this);
        inventories.put(inventoryType, inv);
    }
    
    /**
     * @param player player owner of inventory
     * @param inventoryType inventory type to get
     * @return inventory of specified type
     */
    public static Inventory getInventory(Player player, InventoryType inventoryType) {
        return inventories.get(inventoryType).get(player);
    }
    
    /**
     * @return all inventories
     */
    public static HashMap<InventoryType, HashMap<Player, Inventory>> getInventories() {
        return inventories;
    }
    
    void setRemainingAir(int air) {
        this.air = air;
    }
    
    int getRemainingAir() {
        return air;
    }
    
    void setExhaustion(float exhaustion) {
        this.exhaustion = exhaustion;
    }
    
    float getExhaustion() {
        return exhaustion;
    }
    
    void setExp(int exp) {
        this.exp = exp;
    }
    
    int getExp() {
        return exp;
    }
    
    void setContents(HashMap<Integer, ItemStack> contents) {
        this.contents = contents;
    }
    
    HashMap<Integer, ItemStack> getContents() {
        return contents;
    }
    
    void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }
    
    Collection<PotionEffect> getEffects() {
        return effects;
    }
    
    void setFallDistance(float fall) {
        this.fall = fall;
    }
    
    float getFallDistance() {
        return fall;
    }
    
    void setFireTicks(int fire) {
        this.fire = fire;
    }
    
    int getFireTicks() {
        return fire;
    }
    
    void setHealth(double health) {
        this.health = health;
    }
    
    double getHealth() {
        return health;
    }
    
    void setHunger(int hunger) {
        this.hunger = hunger;
    }
    
    int getHunger() {
        return hunger;
    }
    
    void setSaturation(float saturation) {
        this.saturation = saturation;
    }
    
    float getSaturation() {
        return saturation;
    }
    
    void setSelectedSlot(int slot) {
        this.slot = slot;
    }
    
    int getSelectedSlot() {
        return slot;
    }
    
    /**
     * loads player inventory
     */
    public void load() {
        player.giveExpLevels(-1000000000);
        player.giveExpLevels(exp);
        player.setHealth(health);
        player.setFoodLevel(hunger);
        player.setSaturation(saturation);
        player.setExhaustion(exhaustion);
        player.setRemainingAir(air);
        player.setFallDistance(fall);
        player.setFireTicks(fire);
        player.getInventory().setHeldItemSlot(slot);
        player.addPotionEffects(effects);
        for (int i = 0; i < contents.keySet().size(); i++) player.getInventory().setItem(i, contents.get(i));
        player.updateInventory();
    }
    
    /**
     * saves player inventory
     */
    public void save() {
        exp = player.getTotalExperience();
        health = player.getHealth();
        hunger = player.getFoodLevel();
        saturation = player.getSaturation();
        exhaustion = player.getExhaustion();
        air = player.getRemainingAir();
        fall = player.getFallDistance();
        fire = player.getFireTicks();
        slot = player.getInventory().getHeldItemSlot();
        this.effects = player.getActivePotionEffects();
        int currentslot = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack == null) contents.put(currentslot, new ItemStack(Material.AIR, 0));
            else contents.put(currentslot, stack);
            currentslot++;
        }
    }
    
    /**
     * quickly copies and pastes the player's inventory, used for pwi fix
     */
    public void reload() {
        ItemStack[] contents = player.getInventory().getContents();
        int exp = player.getTotalExperience();
        double health = player.getHealth();
        int hunger = player.getFoodLevel();
        float saturation = player.getSaturation();
        float exhaustion = player.getExhaustion();
        int air = player.getRemainingAir();
        float fall = player.getFallDistance();
        int firetick = player.getFireTicks();
        int slot = player.getInventory().getHeldItemSlot();
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        
        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().setContents(contents);
                player.getPlayer().giveExpLevels(-1000000000);
                player.getPlayer().giveExp(exp);
                player.setHealth(health);
                player.setFoodLevel(hunger);
                player.setSaturation(saturation);
                player.setExhaustion(exhaustion);
                player.setRemainingAir(air);
                player.setFallDistance(fall);
                player.setFireTicks(firetick);
                player.getInventory().setHeldItemSlot(slot);
                for (PotionEffect allEffects : player.getActivePotionEffects()) {
                    player.removePotionEffect(allEffects.getType());
                }
                player.addPotionEffects(effects);
            }
        }.runTaskLater(GamesMaster.getInstance(), 1);
    }
    
    /**
     * removes the inventory data for this player
     */
    public void remove() {
        inventories.get(inventoryType).remove(player);
    }
    
}
