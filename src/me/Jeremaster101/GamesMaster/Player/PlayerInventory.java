package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class PlayerInventory {
    
    Player player;
    
    public PlayerInventory(Player player) {
        this.player = player;
    }
    
    private ConfigManager regionConfig = Config.getConfig(ConfigType.REGION);
    
    public void load(String region) {
        GMPlayer gmplayer = GMPlayer.getPlayer(player);
        PlayerData data = gmplayer.getPlayerData();
        org.bukkit.inventory.PlayerInventory inv = player.getInventory();
        String inventory = "inventories." + regionConfig.getConfig().get(region + ".inventory").toString();
        GameMode mode = GameMode.valueOf((regionConfig.getConfig()
                .getString(region + ".gamemode").toUpperCase()));
        
        if (region.equals("lobby")) {
            (new LobbyInventory()).loadLobbyInv(player);
            
        } else if (!regionConfig.getConfig().get(region + ".inventory").toString().equals("none") &&
                (gmplayer.getCurrentRegion() == null || !regionConfig.getConfig().get(region + ".inventory")
                        .toString().equals(regionConfig.getConfig().get(gmplayer.getCurrentRegion()
                                + ".inventory").toString()))) {
            
            if (data.getDataFile().get(inventory + ".experience") != null) {
                player.getPlayer().giveExpLevels(-1000000000);
                player.getPlayer().giveExp(data.getDataFile().getInt(inventory + ".experience"));
            } else
                player.getPlayer().giveExpLevels(-1000000000);
            
            if (data.getDataFile().get(inventory + ".health") != null) {
                player.getPlayer().setHealth(data.getDataFile().getDouble(inventory + ".health"));
            } else
                player.getPlayer().setHealth(20);
            
            if (data.getDataFile().get(inventory + ".hunger.hunger") != null) {
                player.getPlayer().setFoodLevel(data.getDataFile().getInt(inventory + ".hunger.hunger"));
            } else
                player.getPlayer().setFoodLevel(20);
            
            if (data.getDataFile().get(inventory + ".hunger.saturation") != null) {
                player.getPlayer().setSaturation(Float.parseFloat(data.getDataFile().get(inventory + ".hunger.saturation").toString()));
            } else
                player.getPlayer().setSaturation(5);
            
            if (data.getDataFile().get(inventory + ".hunger.exhaustion") != null) {
                player.getPlayer().setExhaustion(Float.parseFloat(data.getDataFile().get(inventory + ".hunger.exhaustion").toString()));
            } else
                player.getPlayer().setExhaustion(0);
            
            if (data.getDataFile().get(inventory + ".remaining-air") != null) {
                player.getPlayer().setRemainingAir(data.getDataFile().getInt(inventory + ".remaining-air"));
            } else
                player.getPlayer().setRemainingAir(player.getMaximumAir());
            
            if (data.getDataFile().get(inventory + ".fall-distance") != null) {
                player.getPlayer().setFallDistance(Float.parseFloat(data.getDataFile().get(inventory + ".fall-distance").toString()));
            } else
                player.getPlayer().setFallDistance(0);
            
            if (data.getDataFile().get(inventory + ".fire-ticks") != null) {
                player.getPlayer().setFireTicks(data.getDataFile().getInt(inventory + ".fire-ticks"));
            } else
                player.getPlayer().setFireTicks(0);
            
            if (data.getDataFile().get(inventory + ".selected-slot") != null) {
                player.getPlayer().getInventory().setHeldItemSlot(data.getDataFile().getInt(inventory + ".selected-slot"));
            } else
                player.getPlayer().getInventory().setHeldItemSlot(0);
            
            for (PotionEffect allEffects : player.getActivePotionEffects()) {
                player.removePotionEffect(allEffects.getType());
            }
            
            if (data.getDataFile().get(inventory + ".effects", null) != null) {
                for (int effect = 0; effect < data.getDataFile().getConfigurationSection(inventory + ".effects").getKeys(false)
                        .size(); effect++) {
                    PotionEffect peffect = (PotionEffect) data.getDataFile().get(inventory + ".effects." + effect);
                    player.addPotionEffect(peffect);
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
        
        if (player.getGameMode() != mode) {
            player.setGameMode(mode);
        }
        
        if (mode.equals(GameMode.SURVIVAL)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
            }.runTaskLater(GamesMaster.getInstance(), 1);
        }
        
        gmplayer.setCurrentRegion(region);
        
        player.updateInventory();
        
        data.savePlayerData();
    }
    
    public void save(String region) {
        PlayerData data = GMPlayer.getPlayer(player).getPlayerData();
        String inventory = "inventories." + regionConfig.getConfig().get(region + ".inventory").toString();
        if (player.getPlayer().getTotalExperience() > 0) {
            data.getDataFile().set(inventory + ".experience", player.getPlayer().getTotalExperience());
        } else
            data.getDataFile().set(inventory + ".experience", null);
        
        if (player.getPlayer().getHealth() < 20) {
            data.getDataFile().set(inventory + ".health", player.getPlayer().getHealth());
        } else
            data.getDataFile().set(inventory + ".health", null);
        
        if (player.getPlayer().getFoodLevel() < 20) {
            data.getDataFile().set(inventory + ".hunger.hunger", player.getPlayer().getFoodLevel());
        } else
            data.getDataFile().set(inventory + ".hunger.hunger", null);
        
        if (player.getPlayer().getSaturation() != 5) {
            data.getDataFile().set(inventory + ".hunger.saturation", player.getPlayer().getSaturation());
        } else
            data.getDataFile().set(inventory + ".hunger.saturation", null);
        
        if (player.getPlayer().getExhaustion() > 0) {
            data.getDataFile().set(inventory + ".hunger.exhaustion", player.getPlayer().getExhaustion());
        } else
            data.getDataFile().set(inventory + ".hunger.exhaustion", null);
        
        if (player.getPlayer().getRemainingAir() < player.getMaximumAir()) {
            data.getDataFile().set(inventory + ".remaining-air", player.getPlayer().getRemainingAir());
        } else
            data.getDataFile().set(inventory + ".remaining-air", null);
        
        if (player.getPlayer().getFallDistance() > 0) {
            data.getDataFile().set(inventory + ".fall-distance", player.getPlayer().getFallDistance());
        } else
            data.getDataFile().set(inventory + ".fall-distance", null);
        
        if (player.getPlayer().getFireTicks() > -20) {
            data.getDataFile().set(inventory + ".fire-ticks", player.getPlayer().getFireTicks());
        } else
            data.getDataFile().set(inventory + ".fire-ticks", null);
        
        data.getDataFile().set(inventory + ".selected-slot", player.getInventory().getHeldItemSlot());
        
        data.getDataFile().set(inventory + ".effects", null);
        int currenteffect = 0;
        for (PotionEffect effect : player.getActivePotionEffects()) {
            data.getDataFile().set(inventory + ".effects." + currenteffect, effect);
            currenteffect++;
        }
        
        data.getDataFile().set(inventory + ".contents", null);
        int currentslot = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            data.getDataFile().set(inventory + ".contents." + currentslot, stack);
            currentslot++;
        }
        
        data.savePlayerData();
    }
    
    public void copy() {
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
