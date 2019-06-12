package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class UnlockGadget implements Listener {

    public boolean canUnlockStormbreaker(Player p) {
        double mins = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return mins >= 10 * 24 * 60 * 60 * 20;
    }

}
