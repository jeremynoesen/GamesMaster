package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.LobbyInventory;
import me.Jeremaster101.GamesMaster.Region.RegionConfig;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class InventoryHandler {


    public void loadInv(Player p, String region) {
        File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                p.getUniqueId().toString() + ".yml");
        YamlConfiguration pInv = YamlConfiguration.loadConfiguration(playerInvConfigFile);
        org.bukkit.inventory.PlayerInventory inv = p.getInventory();
        String start = RegionConfig.getConfig().get("regions." + region + ".inventory").toString();
        GameMode mode = GameMode.valueOf((RegionConfig.getConfig()
                .getString("regions." + region + ".gamemode")));

        if (region.equals("lobby")) {
            LobbyInventory lInv = new LobbyInventory();
            lInv.loadLobbyInv(p);

        } else if (!RegionConfig.getConfig().get("regions." + region + ".inventory").toString().equals("none") &&
                (pInv.get("current") == null || !RegionConfig.getConfig().get("regions." + region + ".inventory")
                        //todo make method to get player file
                        .toString().equals(RegionConfig.getConfig().get("regions." + pInv.get("current").toString()
                                + ".inventory").toString()))) {

            if (pInv.get(start + ".experience") != null) {
                p.getPlayer().giveExpLevels(-1000000000);
                p.getPlayer().giveExp(pInv.getInt(start + ".experience"));
            } else
                p.getPlayer().giveExpLevels(-1000000000);

            if (pInv.get(start + ".health") != null) {
                p.getPlayer().setHealth(pInv.getDouble(start + ".health"));
            } else
                p.getPlayer().setHealth(20);

            if (pInv.get(start + ".hunger.hunger") != null) {
                p.getPlayer().setFoodLevel(pInv.getInt(start + ".hunger.hunger"));
            } else
                p.getPlayer().setFoodLevel(20);

            if (pInv.get(start + ".hunger.saturation") != null) {
                p.getPlayer().setSaturation(Float.parseFloat(pInv.get(start + ".hunger.saturation").toString()));
            } else
                p.getPlayer().setSaturation(5);

            if (pInv.get(start + ".hunger.exhaustion") != null) {
                p.getPlayer().setExhaustion(Float.parseFloat(pInv.get(start + ".hunger.exhaustion").toString()));
            } else
                p.getPlayer().setExhaustion(0);

            if (pInv.get(start + ".remaining-air") != null) {
                p.getPlayer().setRemainingAir(pInv.getInt(start + ".remaining-air"));
            } else
                p.getPlayer().setRemainingAir(p.getMaximumAir());

            if (pInv.get(start + ".fall-distance") != null) {
                p.getPlayer().setFallDistance(Float.parseFloat(pInv.get(start + ".fall-distance").toString()));
            } else
                p.getPlayer().setFallDistance(0);

            if (pInv.get(start + ".fire-ticks") != null) {
                p.getPlayer().setFireTicks(pInv.getInt(start + ".fire-ticks"));
            } else
                p.getPlayer().setFireTicks(0);

            for (PotionEffect allEffects : p.getActivePotionEffects()) {
                p.removePotionEffect(allEffects.getType());
            }

            if (pInv.get(start + ".effects", null) != null) {
                for (int effect = 0; effect < pInv.getConfigurationSection(start + ".effects").getKeys(false)
                        .size(); effect++) {
                    PotionEffect peffect = (PotionEffect) pInv.get(start + ".effects." + effect);
                    p.addPotionEffect(peffect);
                }
            }

            for (int slotcounter = 0; slotcounter < inv.getSize(); slotcounter++) {
                ItemStack slot = new ItemStack(Material.AIR, 0);
                if (pInv.get(start + ".inventory." + slotcounter, null) != null) {
                    slot = (ItemStack) pInv.get(start + ".inventory." + slotcounter);
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
            }.runTaskLater(GamesMaster.plugin, 1);
        }

        pInv.set("current", region);

        try {
            pInv.save(playerInvConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInv(Player p, String region) {
        File playerInvConfigFile = new File(GamesMaster.plugin.getDataFolder() + File.separator + "playerdata",
                p.getUniqueId().toString() + ".yml");
        YamlConfiguration pInv = YamlConfiguration.loadConfiguration(playerInvConfigFile);
        String start = RegionConfig.getConfig().get("regions." + region + ".inventory").toString();
        if (p.getPlayer().getTotalExperience() > 0) {
            pInv.set(start + ".experience", p.getPlayer().getTotalExperience());
        } else
            pInv.set(start + ".experience", null);

        if (p.getPlayer().getHealth() < 20) {
            pInv.set(start + ".health", p.getPlayer().getHealth());
        } else
            pInv.set(start + ".health", null);

        if (p.getPlayer().getFoodLevel() < 20) {
            pInv.set(start + ".hunger.hunger", p.getPlayer().getFoodLevel());
        } else
            pInv.set(start + ".hunger.hunger", null);

        if (p.getPlayer().getSaturation() != 5) {
            pInv.set(start + ".hunger.saturation", p.getPlayer().getSaturation());
        } else
            pInv.set(start + ".hunger.saturation", null);

        if (p.getPlayer().getExhaustion() > 0) {
            pInv.set(start + ".hunger.exhaustion", p.getPlayer().getExhaustion());
        } else
            pInv.set(start + ".hunger.exhaustion", null);

        if (p.getPlayer().getRemainingAir() < p.getMaximumAir()) {
            pInv.set(start + ".remaining-air", p.getPlayer().getRemainingAir());
        } else
            pInv.set(start + ".remaining-air", null);

        if (p.getPlayer().getFallDistance() > 0) {
            pInv.set(start + ".fall-distance", p.getPlayer().getFallDistance());
        } else
            pInv.set(start + ".fall-distance", null);

        if (p.getPlayer().getFireTicks() > -20) {
            pInv.set(start + ".fire-ticks", p.getPlayer().getFireTicks());
        } else
            pInv.set(start + ".fire-ticks", null);

        pInv.set(start + ".effects", null);
        int currenteffect = 0;
        for (PotionEffect effect : p.getActivePotionEffects()) {
            pInv.set(start + ".effects." + currenteffect, effect);
            currenteffect++;
        }

        pInv.set(start + ".inventory", null);
        int currentslot = 0;
        for (ItemStack stack : p.getInventory().getContents()) {
            pInv.set(start + ".inventory." + currentslot, stack);
            currentslot++;
        }

        try {
            pInv.save(playerInvConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                for (PotionEffect allEffects : p.getActivePotionEffects()) {
                    p.removePotionEffect(allEffects.getType());
                }
                p.addPotionEffects(effects);
            }
        }.runTaskLater(GamesMaster.plugin, 1);
    }
}
