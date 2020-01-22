package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import me.Jeremaster101.GamesMaster.Config.ConfigManager;
import me.Jeremaster101.GamesMaster.Config.ConfigType;
import me.Jeremaster101.GamesMaster.Config.Configs;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIInventory;
import me.Jeremaster101.GamesMaster.Lobby.GUI.GUIItem;
import me.Jeremaster101.GamesMaster.Lobby.LobbyHandler;
import me.Jeremaster101.GamesMaster.Message;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseGadget implements Listener {


    private final Firework firework = new Firework();
    private final PaintballGun paintballGun = new PaintballGun();
    private final SlimeLauncher slimeLauncher = new SlimeLauncher();
    private final GUIInventory guiInv = new GUIInventory();
    private final FizzyLiftingDrink fizzyLiftingDrink = new FizzyLiftingDrink();
    private final GUIItem guiitem = new GUIItem();
    private final Stormbreaker stormbreaker = new Stormbreaker();
    private final LobbyHandler lh = new LobbyHandler();
    
    ConfigManager gameConfig = Configs.getConfig(ConfigType.GAME);
    
    //todo gold launchpads
    //todo beachball gadget

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (lh.isInLobby(p)) {
            if (a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) {
                if (p.getInventory().getItemInMainHand().equals(stormbreaker.stormbreaker())) {
                    e.setCancelled(true);
                    stormbreaker.useLightningSmash(p);
                }
                if (p.getInventory().getItemInMainHand().equals(fizzyLiftingDrink.fizzyLiftingDrink())) {
                    e.setCancelled(true);
                    fizzyLiftingDrink.useFizzyLiftingDrink(p);
                }
                if (p.getInventory().getItemInMainHand().equals(fizzyLiftingDrink.fizzyLiftingDrinkReload())) {
                    e.setCancelled(true);
                }
                if (p.getInventory().getItemInMainHand().equals(firework.firework())) {
                    e.setCancelled(true);
                    firework.useFirework(p);
                }
                if (p.getInventory().getItemInMainHand().equals(firework.fireworkReload())) {
                    e.setCancelled(true);
                }
                if (p.getInventory().getItemInMainHand().equals(paintballGun.paintballGun())) {
                    e.setCancelled(true);
                    paintballGun.usePaintballGun(p);
                }
                if (p.getInventory().getItemInMainHand().equals(slimeLauncher.slimeLauncher())) {
                    e.setCancelled(true);
                    slimeLauncher.useSlimeLauncher(p);
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
            } else if (a == Action.LEFT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR) {
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
                if (p.getInventory().getItemInMainHand().equals(stormbreaker.stormbreaker())) {
                    e.setCancelled(true);
                    stormbreaker.useFly(p);
                }
            }
        }
    }
}
