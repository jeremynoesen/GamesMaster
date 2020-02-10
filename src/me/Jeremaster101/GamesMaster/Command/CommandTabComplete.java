package me.Jeremaster101.GamesMaster.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        
        ArrayList<String> tabList = new ArrayList<>();
        
        if (sender instanceof Player) {
            
            Player player = (Player) sender;
    
            for (int i = 0; i < args.length; i++) {
                
                args[i] = args[i].toLowerCase();
                
            }
            
            //todo finish
    
        }
        
        return tabList;
    
    }
    
}
