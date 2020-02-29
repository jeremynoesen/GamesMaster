package jndev.gamesmaster.player;

import jndev.gamesmaster.region.inventory.Inventory;
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
public class PlayerInventory {
    
    private static HashMap<Player, HashMap<Inventory, PlayerInventory>> savedInventories = new HashMap<>();
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
    private Inventory inventory;
    
    public PlayerInventory(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }
    
    /**
     * @param player player to get inventory for
     * @param inventory inventory type to get
     * @return PlayerInventory of the type
     */
    public static PlayerInventory getPlayerInventory(Player player, Inventory inventory) {
        return savedInventories.get(player).get(inventory);
    }
    
    /**
     * add a new inventory to the loaded list of inventories
     *
     * @param player player to add an inventory for
     * @param inventory inventory type
     * @param playerInventory player specific inventory
     */
    public static void addPlayerInventory(Player player, Inventory inventory, PlayerInventory playerInventory) {
        HashMap<Inventory, PlayerInventory> playerInv = new HashMap<>();
        playerInv.put(inventory, playerInventory);
        savedInventories.put(player, playerInv);
    }
    
    //the following setters are used for PlayerInventoryBuilder
    
    void setAir(int air) {
        this.air = air;
    }
    
    void setExhaustion(float exhaustion) {
        this.exhaustion = exhaustion;
    }
    
    void setExp(int exp) {
        this.exp = exp;
    }
    
    void setContents(HashMap<Integer, ItemStack> contents) {
        this.contents = contents;
    }
    
    void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }
    
    void setFall(float fall) {
        this.fall = fall;
    }
    
    void setFire(int fire) {
        this.fire = fire;
    }
    
    void setHealth(double health) {
        this.health = health;
    }
    
    void setHunger(int hunger) {
        this.hunger = hunger;
    }
    
    void setSaturation(float saturation) {
        this.saturation = saturation;
    }
    
    void setSlot(int slot) {
        this.slot = slot;
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
     * saves player inventory to file and hashmap
     */
    public void save() {
        PlayerData data = GMPlayer.getPlayer(player).getPlayerData();
        String inventory = "inventories." + this.inventory.getName();
        exp = player.getTotalExperience();
        if (player.getPlayer().getTotalExperience() > 0) {
            data.getDataFile().set(inventory + ".experience", player.getTotalExperience());
        } else
            data.getDataFile().set(inventory + ".experience", null);
        
        health = player.getHealth();
        if (player.getPlayer().getHealth() < 20) {
            data.getDataFile().set(inventory + ".health", player.getHealth());
        } else
            data.getDataFile().set(inventory + ".health", null);
        
        hunger = player.getFoodLevel();
        if (player.getPlayer().getFoodLevel() < 20) {
            data.getDataFile().set(inventory + ".hunger.hunger", player.getFoodLevel());
        } else
            data.getDataFile().set(inventory + ".hunger.hunger", null);
        
        saturation = player.getSaturation();
        if (player.getPlayer().getSaturation() != 5) {
            data.getDataFile().set(inventory + ".hunger.saturation", player.getSaturation());
        } else
            data.getDataFile().set(inventory + ".hunger.saturation", null);
        
        exhaustion = player.getExhaustion();
        if (player.getPlayer().getExhaustion() > 0) {
            data.getDataFile().set(inventory + ".hunger.exhaustion", player.getExhaustion());
        } else
            data.getDataFile().set(inventory + ".hunger.exhaustion", null);
        
        air = player.getRemainingAir();
        if (player.getPlayer().getRemainingAir() < player.getMaximumAir()) {
            data.getDataFile().set(inventory + ".remaining-air", player.getRemainingAir());
        } else
            data.getDataFile().set(inventory + ".remaining-air", null);
        
        fall = player.getFallDistance();
        if (player.getPlayer().getFallDistance() > 0) {
            data.getDataFile().set(inventory + ".fall-distance", player.getFallDistance());
        } else
            data.getDataFile().set(inventory + ".fall-distance", null);
        
        fire = player.getFireTicks();
        if (player.getPlayer().getFireTicks() > -20) {
            data.getDataFile().set(inventory + ".fire-ticks", player.getFireTicks());
        } else
            data.getDataFile().set(inventory + ".fire-ticks", null);
        
        slot = player.getInventory().getHeldItemSlot();
        data.getDataFile().set(inventory + ".selected-slot", player.getInventory().getHeldItemSlot());
        
        data.getDataFile().set(inventory + ".effects", null);
        int currenteffect = 0;
        Collection<PotionEffect> effects = new ArrayList<>(player.getActivePotionEffects());
        for (PotionEffect effect : player.getActivePotionEffects()) {
            data.getDataFile().set(inventory + ".effects." + currenteffect, effect);
            currenteffect++;
        }
        this.effects = effects;
        
        int currentslot = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            data.getDataFile().set(inventory + ".contents." + currentslot, stack);
            if (stack == null) contents.put(currentslot, new ItemStack(Material.AIR, 0));
            else contents.put(currentslot, stack);
            currentslot++;
        }
        
        data.savePlayerData();
        
        HashMap<Inventory, PlayerInventory> playerInv = new HashMap<>();
        playerInv.put(this.inventory, this);
        savedInventories.put(player, playerInv);
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
    
}
