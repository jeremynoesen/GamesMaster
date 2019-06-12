package me.Jeremaster101.GamesMaster.Lobby.Game;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class GameConfig {

    private static File gameConfigFile;
    private static YamlConfiguration config;

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        gameConfigFile = new File(GamesMaster.plugin.getDataFolder(), "games.yml");
        if(!gameConfigFile.exists()) {
            copy(GamesMaster.plugin.getResource("games.yml"), gameConfigFile);
        }
        config = YamlConfiguration.loadConfiguration(gameConfigFile);
    }

    public static void saveConfig() {
        try {
            config.save(gameConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

}
