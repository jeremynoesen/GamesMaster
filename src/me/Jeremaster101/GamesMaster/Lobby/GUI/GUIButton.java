package me.Jeremaster101.GamesMaster.Lobby.GUI;

import org.bukkit.entity.Player;

public interface GUIButton {
    boolean onLeftClick(Player player);
    boolean onRightClick(Player player);
}
