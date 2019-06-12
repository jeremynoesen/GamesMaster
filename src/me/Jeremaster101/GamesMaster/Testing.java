package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Testing implements Listener {

    GUIItem item = new GUIItem();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        e.getPlayer().getInventory().setItemInMainHand(item.game("&3&lTest", "DIAMOND", "HideAndSeek is a unique " +
                "gamemode where players are disguised as blocks."));
        e.getPlayer().updateInventory();
    }
}
