package jndev.gamesmaster.lobby.gui;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

/**
 * events for clicking in guis
 */
public class GUIListener implements Listener {
    
    /**
     * listener for clicking in guis to make buttons work
     *
     * @param e inventory click event
     */
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        GUI gui = GUI.getGUI(e.getClickedInventory());
        if (gui != null) {
            gui.onClick(e);
            e.setCancelled(true);
        }
    }
    
    /**
     * prevent players from moving items in guis
     *
     * @param e inventory move item event
     */
    @EventHandler
    public void onMove(InventoryMoveItemEvent e) {
        if (e.getInitiator().getHolder() instanceof Player ||
                e.getSource().getHolder() instanceof Player ||
                e.getDestination().getHolder() instanceof Player) {
            Player player = (Player) e.getInitiator().getHolder();
            GUI sourceGUI = GUI.getGUI(e.getSource());
            GUI destinationGUI = GUI.getGUI(e.getDestination());
            GUI initiatorGUI = GUI.getGUI(e.getInitiator());
            if (sourceGUI != null || destinationGUI != null || initiatorGUI != null) {
                e.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
            }
        }
    }
}