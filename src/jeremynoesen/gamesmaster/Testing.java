package jeremynoesen.gamesmaster;

import jeremynoesen.gamesmaster.gui.GUIType;
import me.Jeremaster101.GamesMaster.Lobby.GUI.*;
import jndev.gamesmaster.player.PlayerGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Testing implements Listener {
    
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
        
        /*e.getPlayer().getInventory().setItemInMainHand(item.game("&3&lTest", "DIAMOND", "HideAndSeek is a unique " +
                "gamemode where players are disguised as blocks."));
        e.getPlayer().updateInventory();*/
            
            
            //test new gui builder and buttons
    
            /*GUI gui = new GUI(GUIType.COSMETICS, GUISize.ONE_ROW, "TEST");
            ItemStack itemStack = new ItemStack(Material.DIRT, 1);
            ItemStack itemStack1 = new ItemStack(Material.SPONGE, 1);
            gui.addToggle(itemStack, itemStack1, 0, new GUIButton() {
                @Override
                public boolean onLeftClick(Player player) {
                    gui.toggle(0);
                    return true;
                }
    
                @Override
                public boolean onRightClick(Player player) {
                    return false;
                }
            }, new GUIButton() {
                @Override
                public boolean onLeftClick(Player player) {
                    gui.toggle(0);
                    return true;
                }
    
                @Override
                public boolean onRightClick(Player player) {
                    return false;
                }
            });
            gui.addDecoration(new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1), 1);
            gui.openGUI(e.getPlayer());
            
             */
    
            PlayerGUI pgui = new PlayerGUI(e.getPlayer());
            pgui.open(GUIType.COSMETICS);
            
        }
    }
}
