package jndev.gamesmaster.lobby.gui;

import org.bukkit.entity.Player;

/**
 * button actions for gui buttons
 */
public interface GUIButton {
    boolean onLeftClick(Player player);
    
    boolean onRightClick(Player player);
}
