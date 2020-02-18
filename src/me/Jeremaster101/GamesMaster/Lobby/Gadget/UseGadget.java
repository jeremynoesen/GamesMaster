package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Config;
import me.Jeremaster101.GamesMaster.Lobby.GUI.OldGUIInventory;
import me.Jeremaster101.GamesMaster.Lobby.GUI.OldGUIItem;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.Gadgets.*;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseGadget implements Listener {
    
    private final OldGUIInventory guiInv = new OldGUIInventory();
    private final OldGUIItem guiitem = new OldGUIItem();
    private final LobbyHandler lh = new LobbyHandler();
    
    ConfigManager gameConfig = Config.getConfig(ConfigType.GAME);
    
    //todo gold launchpads
    
    /**
     * unified method to use all gadgets
     *
     * @param gadget gadget to use
     * @param player player using gadget
     */
    private void useGadget(Gadget gadget, Player player) {
        switch(gadget) {
            case FIREWORK_CANNON:
                FireworkCannon.use(player);
                break;
            case FIZZY_LIFTING_DRINK:
                FizzyLiftingDrink.use(player);
                break;
            case PAINTBALL_GUN:
                PaintballGun.use(player);
                break;
            case SLIME_LAUNCHER:
                SlimeLauncher.use(player);
                break;
            case STORMBREAKER:
                Stormbreaker.use(player);
                break;
        }
    }
    
    /**
     * allows for right clicking a gadget to work
     *
     * @param e player interact event
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {//todo redo with configurable items and lockables
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (lh.isInLobby(p)) {
            if (a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) {
                
                if(false/*get gadget slot*/) {
                    //check if null
                    e.setCancelled(true);
                    useGadget(Gadget.valueOf(GadgetItemBuilder.getName(p.getInventory().getItemInMainHand())), p);
                }
                
                if (p.getInventory().getItemInMainHand().equals(guiitem.cosmeticUI())) {
                    e.setCancelled(true);
                    p.openInventory(guiInv.cosmeticUI(p));
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                }
                
                if (p.getInventory().getItemInMainHand().equals(guiitem.gameUI())) {
                    e.setCancelled(true);
                    if (gameConfig.getConfig().getConfigurationSection("games") != null &&
                            gameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() > 0) {
                        p.openInventory(guiInv.gameUI());
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 2, 2);
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 2, 2);
                        p.sendMessage(Message.ERROR_NO_GAMES);
                    }
                }
            }
        }
    }
}
