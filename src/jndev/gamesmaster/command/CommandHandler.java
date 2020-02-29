package jndev.gamesmaster.command;

import jndev.gamesmaster.config.Config;
import jndev.gamesmaster.config.ConfigType;
import jndev.gamesmaster.Message;
import jndev.gamesmaster.config.ConfigManager;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHandler {
    
    ConfigManager commandConfig = Config.getConfig(ConfigType.COMMAND);

    public void blockCmd(Player p, String cmd) {
        List<String> blockedcmds = commandConfig.getConfig().getStringList("blocked-cmds");
        if (!blockedcmds.contains(cmd)) {
            blockedcmds.add(cmd);
            commandConfig.getConfig().set("blocked-cmds", blockedcmds);
            commandConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_CMD_BLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(Message.ERROR_CMD_ALREADY_BLOCKED);
    }

    public void unblockCmd(Player p, String cmd) {
        List<String> blockedcmds = commandConfig.getConfig().getStringList("blocked-cmds");
        if (blockedcmds.contains(cmd)) {
            blockedcmds.remove(cmd);
            commandConfig.getConfig().set("blocked-cmds", blockedcmds);
            commandConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_CMD_UNBLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(Message.ERROR_CMD_NOT_BLOCKED);
    }
    
    public void addAlias(Player p, String cmd, String alias) {//todo finish
    
    }
    
    public void removeAlias(Player p, String cmd, String alias) {
    
    }
}
