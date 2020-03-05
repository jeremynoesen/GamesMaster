package jndev.gamesmaster.gadget;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.config.ConfigManager;
import jndev.gamesmaster.gui.GUIType;
import JNDev.GamesMasterr.Lobby.Gadget.Gadgets.*;
import JNDev.gamesmaster.Lobby.Gadget.Gadgets.*;
import JNDevv.gamesmaster.Lobby.Gadget.Gadgets.*;
import jndev.gamesmaster.gadget.gadgets.*;
import me.Jeremaster101.GamesMaster.Lobby.Gadget.Gadgets.*;
import jndev.gamesmaster.lobby.LobbyInventory;
import jndev.gamesmaster.Message;
import jndev.gamesmaster.player.GMPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseGadget implements Listener {
    
    ConfigManager gameConfig = Config.getConfig(ConfigType.GAME);
    
    //todo gold launchpads
    
    /**
     * unified method to use all gadgets
     *
     * @param gadget gadget to use
     * @param player player using gadget
     */
    private void useGadget(Gadget gadget, Player player) {
        switch (gadget) {
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
    public void onInteract(PlayerInteractEvent e) {//todo configurable items and lockables
        Player p = e.getPlayer();
        GMPlayer gmp = GMPlayer.getGMPlayer(p);
        Action a = e.getAction();
        if (gmp.isInLobby()) {
            if (a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) {
                
                if (p.getInventory().getHeldItemSlot() == LobbyInventory.getGadgetSlot()) {
                    if (p.getInventory().getItem(LobbyInventory.getGadgetSlot()) != null) {
                        e.setCancelled(true);
                        useGadget(Gadget.valueOf(GadgetItemBuilder.getName(p.getInventory().getItemInMainHand())), p);
                    }
                }
                
                //todo move to diff class
                if (p.getInventory().getItemInMainHand().equals()) {//todo lobby item
                    e.setCancelled(true);
                    gmp.openGUI(GUIType.COSMETICS);
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1); //todo custom
                }
                
                if (p.getInventory().getItemInMainHand().equals()) { //todo lobby item
                    e.setCancelled(true);
                    if (gameConfig.getConfig().getConfigurationSection("games") != null &&
                            gameConfig.getConfig().getConfigurationSection("games").getKeys(false).size() > 0) {
                        gmp.openGUI(GUIType.GAME);
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
