package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.Message.Message;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHandler {

    private final Message msg = new Message();

    public void blockCmd(Player p, String cmd) {
        List<String> blockedcmds = CommandConfig.getConfig().getStringList("blocked-cmds");
        if (!blockedcmds.contains(cmd)) {
            blockedcmds.add(cmd);
            CommandConfig.getConfig().set("blocked-cmds", blockedcmds);
            CommandConfig.saveConfig();
            p.sendMessage(msg.SUCCESS_CMD_BLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(msg.ERROR_CMD_ALREADY_BLOCKED);
    }

    public void unblockCmd(Player p, String cmd) {
        List<String> blockedcmds = CommandConfig.getConfig().getStringList("blocked-cmds");
        if (blockedcmds.contains(cmd)) {
            blockedcmds.remove(cmd);
            CommandConfig.getConfig().set("blocked-cmds", blockedcmds);
            CommandConfig.saveConfig();
            p.sendMessage(msg.SUCCESS_CMD_UNBLOCKED.replace("$CMD$", cmd));
        } else
            p.sendMessage(msg.ERROR_CMD_NOT_BLOCKED);
    }
}
