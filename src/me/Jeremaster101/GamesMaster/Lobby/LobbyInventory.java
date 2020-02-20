package me.Jeremaster101.GamesMaster.Lobby;

import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.GadgetItem;
import me.Jeremaster101.GamesMaster.Player.GMPlayer;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class LobbyInventory {
    
    /**
     * @return slot number for gadgets
     */
    public static int getGadgetSlot() {
        return Config.getConfig(ConfigType.GADGET).getConfig().getInt("gadget-slot");
    }
    
    private final Sound[] records = new Sound[]{
            Sound.MUSIC_DISC_13,
            Sound.MUSIC_DISC_BLOCKS,
            Sound.MUSIC_DISC_CAT,
            Sound.MUSIC_DISC_CHIRP,
            Sound.MUSIC_DISC_FAR,
            Sound.MUSIC_DISC_MALL,
            Sound.MUSIC_DISC_MELLOHI,
            Sound.MUSIC_DISC_STAL,
            Sound.MUSIC_DISC_STRAD,
            Sound.MUSIC_DISC_WAIT,
            Sound.MUSIC_DISC_WARD
    };
    
    public void clearLobbyInv(Player p) {
        p.setGlowing(false);
        p.resetPlayerWeather();
        
        if (p.getInventory().getItem(4) != null && p.getInventory().getItem(4).equals(GadgetItem.STORMBREAKER_RELOAD.getItem())) {
            for (Player all : LobbyHandler.getPlayersInLobby()) {
                GMPlayer gmp = GMPlayer.getPlayer(p);
                if (gmp.isInLobby()) {
                    all.setPlayerWeather(WeatherType.CLEAR);
                }
            }
        }
        
        p.setExp(0f);
        
        for (Sound s : records) p.stopSound(s);
        if (p.getGameMode() == GameMode.SURVIVAL) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        for (PotionEffect allEffects : p.getActivePotionEffects()) {
            p.removePotionEffect(allEffects.getType());
        }
        
        //todo remove lobby items
        
    }
    
    public void loadLobbyInv(Player p) {
        
        for (PotionEffect allEffects : p.getActivePotionEffects()) {
            p.removePotionEffect(allEffects.getType());
        }
        
        p.getInventory().setHeldItemSlot(4);
        p.getInventory().setHeldItemSlot(4);
        p.getPlayer().giveExpLevels(-1000000000);
        p.getPlayer().setHealth(20);
        p.getPlayer().setFoodLevel(20);
        p.getPlayer().setSaturation(5);
        p.getPlayer().setExhaustion(0);
        p.getPlayer().setFireTicks(0);
        p.setFlying(false);
        p.getInventory().clear();
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        p.setExp(1f);
        
        //todo give lobby items
        
    }
}
