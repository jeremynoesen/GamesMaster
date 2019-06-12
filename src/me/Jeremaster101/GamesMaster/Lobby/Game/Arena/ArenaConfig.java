package me.Jeremaster101.GamesMaster.Lobby.Game.Arena;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class ArenaConfig {

    private static File arenaConfigFile;
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
        arenaConfigFile = new File(GamesMaster.plugin.getDataFolder(), "arenas.yml");
        if (!arenaConfigFile.exists()) {
            copy(GamesMaster.plugin.getResource("arenas.yml"), arenaConfigFile);
        }
        config = YamlConfiguration.loadConfiguration(arenaConfigFile);
    }

    public static void saveConfig() {
        try {
            config.save(arenaConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

}
