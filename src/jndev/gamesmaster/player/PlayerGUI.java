package jndev.gamesmaster.player;

import jndev.gamesmaster.lobby.gui.GUIBuilder;
import jndev.gamesmaster.lobby.gui.GUIType;
import jndev.gamesmaster.lobby.gui.GUI;
import org.bukkit.entity.Player;

/**
 * class to handle GUIs that are specific to each player
 */
public class PlayerGUI {
    
    private GUI gadgetsGUI;
    private GUI cosmeticsGUI;
    private GUI preferencesGUI;
    private GUI musicGUI;
    private Player player;
    
    /**
     * copy guis from template
     *
     * @param player player to show gui
     */
    public PlayerGUI(Player player) {
        gadgetsGUI = GUIBuilder.buildPrivateGUI(GUIType.GADGET, player);
        cosmeticsGUI = GUIBuilder.buildPrivateGUI(GUIType.COSMETICS, player);
        preferencesGUI = GUIBuilder.buildPrivateGUI(GUIType.PREFERENCES, player);
        musicGUI = GUIBuilder.buildPrivateGUI(GUIType.MUSIC, player);
        this.player = player;
    }
    
    /**
     * unified open method, will open all guis and will open per-player ones depending on their type
     *
     * @param type guitype to open
     */
    public void open(GUIType type) {
        switch (type) {
            case COSMETICS:
                cosmeticsGUI.open(player);
                break;
            case GADGET:
                gadgetsGUI.open(player);
                break;
            case PREFERENCES:
                preferencesGUI.open(player);
                break;
            case MUSIC:
                musicGUI.open(player);
                break;
            case GAME:
                GUI.getGUI(GUIType.GAME).open(player);
                break;
            case GAME_LIST:
                GUI.getGUI(GUIType.GAME_LIST).open(player);
                break;
        }
    }
    
}
