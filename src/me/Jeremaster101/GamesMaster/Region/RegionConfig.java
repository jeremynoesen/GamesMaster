package me.Jeremaster101.GamesMaster.Region;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class RegionConfig {

    private static File regionConfigFile;
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
        regionConfigFile = new File(GamesMaster.plugin.getDataFolder(), "regions.yml");
        if(!regionConfigFile.exists()) {
            copy(GamesMaster.plugin.getResource("regions.yml"), regionConfigFile);
        }
        config = YamlConfiguration.loadConfiguration(regionConfigFile);
    }

    public static void saveConfig() {
        try {
            config.save(regionConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

}
