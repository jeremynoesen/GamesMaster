package me.Jeremaster101.GamesMaster.Command;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class CommandConfig {

    private static File commandConfigFile;
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
        commandConfigFile = new File(GamesMaster.plugin.getDataFolder(), "commands.yml");
        if(!commandConfigFile.exists()) {
            copy(GamesMaster.plugin.getResource("commands.yml"), commandConfigFile);
        }
        config = YamlConfiguration.loadConfiguration(commandConfigFile);
    }

    public static void saveConfig() {
        try {
            config.save(commandConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

}
