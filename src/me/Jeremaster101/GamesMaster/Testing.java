package me.Jeremaster101.GamesMaster;

import me.Jeremaster101.GamesMaster.Lobby.GUI.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class Testing implements Listener {
    
    OldGUIItem item = new OldGUIItem();
    
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
            
            GUIBuilder.build(GUIType.COSMETICS);
            
            GUI gui = GUI.getGUI(GUIType.COSMETICS);
            gui.open(e.getPlayer());
           
        }
    }
}
