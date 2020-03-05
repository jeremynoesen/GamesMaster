package jndev.gamesmaster.player;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerSaver {
    
    public static void save(GMPlayer gmplayer) {
        YamlConfiguration playerFile = gmplayer.getPlayerData().getDataFile();
    }
    
}
