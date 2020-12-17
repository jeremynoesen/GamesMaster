package jeremynoesen.gamesmaster.player;

import jeremynoesen.gamesmaster.inventory.InventorySaver;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * saves player data
 */
public class PlayerSaver {
    
    /**
     * saves all loaded players' data
     */
    public static void saveAll() {
        for(GMPlayer gmPlayer : GMPlayer.getGMPlayers()) {
            save(gmPlayer);
        }
    }
    
    /**
     * saves preferences, purchased gadgets, purchased music, and inventories to the player file
     *
     * @param gmplayer gmplayer to save data for
     */
    public static void save(GMPlayer gmplayer) {
        YamlConfiguration playerFile = gmplayer.getPlayerData().getDataFile();
        boolean music = gmplayer.getPreferences().isMusicPublic();
        boolean players = gmplayer.getPreferences().arePlayersVisible();
        boolean gadgets = gmplayer.getPreferences().canGadgetInteract();
        boolean effects = gmplayer.getPreferences().areEffectsVisible();
        
        playerFile.set("preferences.public-music", music);
        playerFile.set("preferences.players-visible", players);
        playerFile.set("preferences.gadget-interaction", gadgets);
        playerFile.set("preferences.effects-visible", effects);
        gmplayer.getPlayerData().savePlayerData();
        
        //todo music and preferences purchased
        
        InventorySaver.saveAllForPlayer(gmplayer.getPlayer());
    }
    
}
