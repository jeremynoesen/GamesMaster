package me.Jeremaster101.GamesMaster.Player;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * handling of player preferences
 */
public class PlayerPreferences {//todo read from player file
    
    private boolean music;
    private boolean players;
    private boolean effects;
    private boolean gadgets;
    private Player player;
    private YamlConfiguration data;
    
    public PlayerPreferences(Player player) {
        this.player = player;
        data = GMPlayer.getPlayer(player).getData().getDataFile();
        music = data.getBoolean("preferences.public-music");
        players = data.getBoolean("preferences.players-visible");
        gadgets = data.getBoolean("preferences.gadget-interaction");
        effects = data.getBoolean("preferences.effects-visible");
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
    }
    
    /**
     * @param enabled enable or disable player visiblity
     */
    public void setPlayersVisible(boolean enabled) {
        players = enabled;
        data.set("preferences.players-visible", enabled);
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
    }
    
    /**
     * @return true if gadgets can interact with player
     */
    public boolean canGadgetInteract() {
        return gadgets;
    }
    
}
