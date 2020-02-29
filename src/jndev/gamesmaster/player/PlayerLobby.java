package jndev.gamesmaster.player;

import jndev.gamesmaster.lobby.gadget.GadgetItem;
import jndev.gamesmaster.lobby.gadget.GadgetItemBuilder;
import jndev.gamesmaster.lobby.LobbyInventory;
import jndev.gamesmaster.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLobby {
    
    private Player player;
    
    public PlayerLobby(Player player) {
        this.player = player;
    }
    
    /**
     * @return true if the player is in the lobby
     */
    public boolean isInLobby() {
        Location l = player.getLocation();
        return Region.getRegion("lobby").containsLocation(l);
    }
    
    /**
     * @return current gadget the player has
     */
    public GadgetItem getGadgetItem() {
        if (player.getInventory().getItem(LobbyInventory.getGadgetSlot()) != null)
            return GadgetItem.valueOf(GadgetItemBuilder.getName(player.getInventory().getItem(LobbyInventory.getGadgetSlot())));
        return null;
    }
    
    /**
     * set the active gadget for the player
     *
     * @param item gadget to set
     */
    public void setGadgetItem(GadgetItem item) {
        player.getInventory().setItem(LobbyInventory.getGadgetSlot(), item.getItem());
        player.updateInventory();
    }
    
}
