package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHandler {

    public void blockCmd(Player p, String cmd) {
        List<String> blockedcmds = CommandConfig.getConfig().getStringList("blocked-cmds");
        if (!blockedcmds.contains(cmd)) {
            blockedcmds.add(cmd);
            CommandConfig.getConfig().set("blocked-cmds", blockedcmds);
            CommandConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_CMD_BLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(Message.ERROR_CMD_ALREADY_BLOCKED);
    }

    public void unblockCmd(Player p, String cmd) {
        List<String> blockedcmds = CommandConfig.getConfig().getStringList("blocked-cmds");
        if (blockedcmds.contains(cmd)) {
            blockedcmds.remove(cmd);
            CommandConfig.getConfig().set("blocked-cmds", blockedcmds);
            CommandConfig.saveConfig();
            p.sendMessage(Message.SUCCESS_CMD_UNBLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(Message.ERROR_CMD_NOT_BLOCKED);
    }
}
