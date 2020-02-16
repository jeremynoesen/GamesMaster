package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * player data file management class
 */
public class PlayerData {
    
    private File playerFile;
    private YamlConfiguration playerData;
    private Player player;
    
    /**
     * creates player's data file
     *
     * @param p player to make file for
     */
    public PlayerData(Player p) {
        player = p;
        playerFile = new File(GamesMaster.getInstance().getDataFolder() + File.separator + "playerdata",
                this.player.getUniqueId().toString() + ".yml");
        playerData = YamlConfiguration.loadConfiguration(playerFile);
    }
    
    /**
     * @return player data file
     */
    public YamlConfiguration getDataFile() {
        return playerData;
    }
    
    /**
     * reload player data file
     */
    public void reloadPlayerData() {
        playerData = YamlConfiguration.loadConfiguration(playerFile);
    }
    
    /**
     * save player data file
     */
    public void savePlayerData() {
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * reset player data file
     */
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

}