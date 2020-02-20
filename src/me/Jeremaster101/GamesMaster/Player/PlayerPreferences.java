package me.Jeremaster101.GamesMaster.Player;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * handling of player preferences
 */
public class PlayerPreferences {
    
    private boolean music;
    private boolean players;
    private boolean effects;
    private boolean gadgets;
    private YamlConfiguration data;
    private GMPlayer gmplayer;
    
    /**
     * load last used preferences
     *
     * @param player player to set preferences for
     */
    public PlayerPreferences(Player player) {
        gmplayer = GMPlayer.getPlayer(player);
        data = gmplayer.getPlayerData().getDataFile();
        music = data.getBoolean("preferences.public-music");
        players = data.getBoolean("preferences.players-visible");
        gadgets = data.getBoolean("preferences.gadget-interaction");
        effects = data.getBoolean("preferences.effects-visible");
        gmplayer.savePreferences(this);
    }
    
    /**
     * save player prefs to file and update in GMPlayer
     */
    private void save() {
        gmplayer.getPlayerData().savePlayerData();
        gmplayer.savePreferences(this);
    }
    
    /**
     * @return true if music is public
     */
    public boolean isMusicPublic() {
        return music;
    }
    
    /**
     * @param enabled enable or disable public music
     */
    public void setMusicPublic(boolean enabled) {
        music = enabled;
        data.set("preferences.public-music", enabled);
        save();
    }
    
    /**
     * @param enabled enable or disable player visiblity
     */
    public void setPlayersVisible(boolean enabled) {
        players = enabled;
        data.set("preferences.players-visible", enabled);
        save();
    }
    
    /**
     * @return true if players are visible
     */
    public boolean arePlayersVisible() {
        return players;
    }
    
    /**
     * @param enabled enable or disable effects
     */
    public void setEffectsVisible(boolean enabled) {
        effects = enabled;
        data.set("preferences.effects-visible", enabled);
        save();
    }
    
    /**
     * @return true if effects are visible
     */
    public boolean areEffectsVisible() {
        return effects;
    }
    
    /**
     * @param enabled enable or disable gadget interaction
     */
    public void setGadgetInteraction(boolean enabled) {
        gadgets = enabled;
        data.set("preferences.gadget-interaction", enabled);
        save();
    }
    
    /**
     * @return true if gadgets can interact with player
     */
    public boolean canGadgetInteract() {
        return gadgets;
    }
    
}
