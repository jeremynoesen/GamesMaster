package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import me.Jeremaster101.GamesMaster.Player.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class InventoryHandler {
    
    private ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    
    public void loadInv(Player p, String region) {
        GMPlayer gmplayer = GMPlayer.getPlayer(p);
        PlayerData data = gmplayer.getData();
        org.bukkit.inventory.PlayerInventory inv = p.getInventory();
        String inventory = "inventories." + regionConfig.getConfig().get(region + ".inventory").toString();
        GameMode mode = GameMode.valueOf((regionConfig.getConfig()
                .getString(region + ".gamemode").toUpperCase()));
        
        if (region.equals("lobby")) {
            (new LobbyInventory()).loadLobbyInv(p);
            
        } else if (!regionConfig.getConfig().get(region + ".inventory").toString().equals("none") &&
                (gmplayer.getCurrentRegion() == null || !regionConfig.getConfig().get(region + ".inventory")
                        .toString().equals(regionConfig.getConfig().get(gmplayer.getCurrentRegion()
                                + ".inventory").toString()))) {
            
            if (data.getDataFile().get(inventory + ".experience") != null) {
                p.getPlayer().giveExpLevels(-1000000000);
                p.getPlayer().giveExp(data.getDataFile().getInt(inventory + ".experience"));
            } else
                p.getPlayer().giveExpLevels(-1000000000);
            
            if (data.getDataFile().get(inventory + ".health") != null) {
                p.getPlayer().setHealth(data.getDataFile().getDouble(inventory + ".health"));
            } else
                p.getPlayer().setHealth(20);
            
            if (data.getDataFile().get(inventory + ".hunger.hunger") != null) {
                p.getPlayer().setFoodLevel(data.getDataFile().getInt(inventory + ".hunger.hunger"));
            } else
                p.getPlayer().setFoodLevel(20);
            
            if (data.getDataFile().get(inventory + ".hunger.saturation") != null) {
                p.getPlayer().setSaturation(Float.parseFloat(data.getDataFile().get(inventory + ".hunger.saturation").toString()));
            } else
                p.getPlayer().setSaturation(5);
            
            if (data.getDataFile().get(inventory + ".hunger.exhaustion") != null) {
                p.getPlayer().setExhaustion(Float.parseFloat(data.getDataFile().get(inventory + ".hunger.exhaustion").toString()));
            } else
                p.getPlayer().setExhaustion(0);
            
            if (data.getDataFile().get(inventory + ".remaining-air") != null) {
                p.getPlayer().setRemainingAir(data.getDataFile().getInt(inventory + ".remaining-air"));
            } else
                p.getPlayer().setRemainingAir(p.getMaximumAir());
            
            if (data.getDataFile().get(inventory + ".fall-distance") != null) {
                p.getPlayer().setFallDistance(Float.parseFloat(data.getDataFile().get(inventory + ".fall-distance").toString()));
            } else
                p.getPlayer().setFallDistance(0);
            
            if (data.getDataFile().get(inventory + ".fire-ticks") != null) {
                p.getPlayer().setFireTicks(data.getDataFile().getInt(inventory + ".fire-ticks"));
            } else
                p.getPlayer().setFireTicks(0);
            
            if (data.getDataFile().get(inventory + ".selected-slot") != null) {
                p.getPlayer().getInventory().setHeldItemSlot(data.getDataFile().getInt(inventory + ".selected-slot"));
            } else
                p.getPlayer().getInventory().setHeldItemSlot(0);
            
            for (PotionEffect allEffects : p.getActivePotionEffects()) {
                p.removePotionEffect(allEffects.getType());
            }
            
            if (data.getDataFile().get(inventory + ".effects", null) != null) {
                for (int effect = 0; effect < data.getDataFile().getConfigurationSection(inventory + ".effects").getKeys(false)
                        .size(); effect++) {
                    PotionEffect peffect = (PotionEffect) data.getDataFile().get(inventory + ".effects." + effect);
                    p.addPotionEffect(peffect);
                }
            }
            
            for (int slotcounter = 0; slotcounter < inv.getSize(); slotcounter++) {
                ItemStack slot = new ItemStack(Material.AIR, 0);
                if (data.getDataFile().get(inventory + ".contents." + slotcounter, null) != null) {
                    slot = (ItemStack) data.getDataFile().get(inventory + ".contents." + slotcounter);
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
    
    public void saveInv(Player p, String region) {
        PlayerData data = GMPlayer.getPlayer(p).getData();
        String inventory = "inventories." + regionConfig.getConfig().get(region + ".inventory").toString();
        if (p.getPlayer().getTotalExperience() > 0) {
            data.getDataFile().set(inventory + ".experience", p.getPlayer().getTotalExperience());
        } else
            data.getDataFile().set(inventory + ".experience", null);
        
        if (p.getPlayer().getHealth() < 20) {
            data.getDataFile().set(inventory + ".health", p.getPlayer().getHealth());
        } else
            data.getDataFile().set(inventory + ".health", null);
        
        if (p.getPlayer().getFoodLevel() < 20) {
            data.getDataFile().set(inventory + ".hunger.hunger", p.getPlayer().getFoodLevel());
        } else
            data.getDataFile().set(inventory + ".hunger.hunger", null);
        
        if (p.getPlayer().getSaturation() != 5) {
            data.getDataFile().set(inventory + ".hunger.saturation", p.getPlayer().getSaturation());
        } else
            data.getDataFile().set(inventory + ".hunger.saturation", null);
        
        if (p.getPlayer().getExhaustion() > 0) {
            data.getDataFile().set(inventory + ".hunger.exhaustion", p.getPlayer().getExhaustion());
        } else
            data.getDataFile().set(inventory + ".hunger.exhaustion", null);
        
        if (p.getPlayer().getRemainingAir() < p.getMaximumAir()) {
            data.getDataFile().set(inventory + ".remaining-air", p.getPlayer().getRemainingAir());
        } else
            data.getDataFile().set(inventory + ".remaining-air", null);
        
        if (p.getPlayer().getFallDistance() > 0) {
            data.getDataFile().set(inventory + ".fall-distance", p.getPlayer().getFallDistance());
        } else
            data.getDataFile().set(inventory + ".fall-distance", null);
        
        if (p.getPlayer().getFireTicks() > -20) {
            data.getDataFile().set(inventory + ".fire-ticks", p.getPlayer().getFireTicks());
        } else
            data.getDataFile().set(inventory + ".fire-ticks", null);
        
        data.getDataFile().set(inventory + ".selected-slot", p.getInventory().getHeldItemSlot());
        
        data.getDataFile().set(inventory + ".effects", null);
        int currenteffect = 0;
        for (PotionEffect effect : p.getActivePotionEffects()) {
            data.getDataFile().set(inventory + ".effects." + currenteffect, effect);
            currenteffect++;
        }
        
        data.getDataFile().set(inventory + ".contents", null);
        int currentslot = 0;
        for (ItemStack stack : p.getInventory().getContents()) {
            data.getDataFile().set(inventory + ".contents." + currentslot, stack);
            currentslot++;
        }
        
        data.savePlayerData();
    }
    
    public void copyInv(Player p) {
        ItemStack[] contents = p.getInventory().getContents();
        int exp = p.getTotalExperience();
        double health = p.getHealth();
        int hunger = p.getFoodLevel();
        float saturation = p.getSaturation();
        float exhaustion = p.getExhaustion();
        int air = p.getRemainingAir();
        float fall = p.getFallDistance();
        int firetick = p.getFireTicks();
        int slot = p.getInventory().getHeldItemSlot();
        Collection<PotionEffect> effects = p.getActivePotionEffects();
        
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getInventory().setContents(contents);
                p.getPlayer().giveExpLevels(-1000000000);
                p.getPlayer().giveExp(exp);
                p.setHealth(health);
                p.setFoodLevel(hunger);
                p.setSaturation(saturation);
                p.setExhaustion(exhaustion);
                p.setRemainingAir(air);
                p.setFallDistance(fall);
                p.setFireTicks(firetick);
                p.getInventory().setHeldItemSlot(slot);
                for (PotionEffect allEffects : p.getActivePotionEffects()) {
                    p.removePotionEffect(allEffects.getType());
                }
                p.addPotionEffects(effects);
            }
        }.runTaskLater(GamesMaster.getInstance(), 1);
    }
}
