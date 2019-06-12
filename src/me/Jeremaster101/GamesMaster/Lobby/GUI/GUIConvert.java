package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Message.Message;

class GUIConvert {

    private final Message msg = new Message();

    String invNameToGame(String invName) {
        if (invName.equals(msg.UI_BLOCK_PARTY)) return "blockparty";
        if (invName.equals(msg.UI_ELYTRA_RACE)) return "elytrarace";
        if (invName.equals(msg.UI_HIDE_AND_SEEK)) return "hideandseek";
        if (invName.equals(msg.UI_HUNGER_GAMES)) return "hungergames";
        if (invName.equals(msg.UI_SKYWARS)) return "skywars";
        if (invName.equals(msg.UI_SPEED_BUILDERS)) return "speedbuilders";
        if (invName.equals(msg.UI_SPLEEF)) return "spleef";
        if (invName.equals(msg.UI_MURDER_MYSTERY)) return "murdermystery";
        if (invName.equals(msg.UI_BEDWARS)) return "bedwars";
        if (invName.equals(msg.UI_PARKOUR)) return "parkour";
        if (invName.equals(msg.UI_CHICKENWARS)) return "chickenwars";
        return "error";
    }

    int gameToColorInt(String game) {
        switch (game) {
            case "blockparty":
                return 11;
            case "elytrarace":
                return 8;
            case "hideandseek":
                return 1;
            case "hungergames":
                return 2;
            case "skywars":
                return 3;
            case "speedbuilders":
                return 4;
            case "spleef":
                return 5;
            case "murdermystery":
                return 9;
            case "bedwars":
                return 6;
            case "parkour":
                return 15;
            case "chickenwars":
                return 14;
        }
        return 0;
    }
}