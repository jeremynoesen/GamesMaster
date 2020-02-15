package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.GamesMaster;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class GMPlayer {
    
    private File playerFile;
    private YamlConfiguration playerData;
    private Player player;
    private GUI gadgets;
    private GUI cosmetics;
    private GUI preferences;
    
    //todo register per-player invs here
    public GMPlayer(Player p) {
        player = p;
        playerFile = new File(GamesMaster.getInstance().getDataFolder() + File.separator + "playerdata",
                this.player.getUniqueId().toString() + ".yml");
        playerData = YamlConfiguration.loadConfiguration(playerFile);
    }
    
    public static GMPlayer getPlayerData(Player player) {
        return new GMPlayer(player);
    }
    
    public YamlConfiguration getDataFile() {
        return playerData;
    }
    
    public void reloadPlayerData() {
        playerData = YamlConfiguration.loadConfiguration(playerFile);
    }
    
    public void savePlayerData() {
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void resetPlayerData() {
        playerFile.delete();
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerFile = new File(GamesMaster.getInstance().getDataFolder() + File.separator + "playerdata",
                this.player.getUniqueId().toString() + ".yml");
        playerData = YamlConfiguration.loadConfiguration(playerFile);
    }
    
    public String getCurrentRegion() {
        if (playerData.get("current-region") != null)
            return playerData.get("current-region").toString();
        else
            return null;
    }
    
    public void setCurrentRegion(String region) {
        playerData.set("current-region", region);
    }
    
    public boolean isMusicMuted() {//todo allow self music
        if (playerData.get("lobby-options.music-muted") == null) return false;
        else return playerData.getBoolean("lobby-options.music-muted");
    }
    
    public void setMusicMuted(boolean muted) {
        playerData.set("lobby-options.music-muted", muted);
    }
}
