package me.Jeremaster101.GamesMaster.Player;

import me.Jeremaster101.GamesMaster.Lobby.GUI.GUI;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIType;
import org.bukkit.entity.Player;

/**
 * class to handle GUIs that are specific to each player
 */
public class PlayerGUI {
    
    private static GUI gadgetsTemplate = GUI.getGUI(GUIType.GADGET);
    private static GUI cosmeticsTemplate = GUI.getGUI(GUIType.COSMETICS);
    private static GUI preferencesTemplate = GUI.getGUI(GUIType.PREFERENCES);
    
    private GUI gadgetsGUI;
    private GUI cosmeticsGUI;
    private GUI preferencesGUI;
    private Player player;
    
    /**
     * copy guis from template
     *
     * @param player player to show gui
     */
    public PlayerGUI(Player player) {
        gadgetsGUI = copyFromTemplate(gadgetsTemplate);
        cosmeticsGUI = copyFromTemplate(cosmeticsTemplate);
        preferencesGUI = copyFromTemplate(preferencesTemplate);
        this.player = player;
    }
    
    /**
     * copy a gui from the template so each player has their own
     *
     * @param from gui used as a template
     * @return a new gui identical to the template
     */
    private static GUI copyFromTemplate(GUI from) {
        GUI to = new GUI(null, from.getSize(), from.getName());
        to.setButtons(from.getButtons());
        to.setDecorations(from.getDecorations());
        to.setInventory(from.getInventory());
        to.save();
        return to;
    }
    
    /**
     * unified open method, will open all guis and will open per-player ones depending on their type
     *
     * @param type guitype to open
     */
    public void open(GUIType type) {
        switch(type) {
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
                GUI.getGUI(GUIType.MUSIC).open(player);
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
