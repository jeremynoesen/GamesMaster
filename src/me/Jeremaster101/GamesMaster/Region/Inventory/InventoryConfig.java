package me.Jeremaster101.GamesMaster.Region.Inventory;

import me.Jeremaster101.GamesMaster.GamesMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class InventoryConfig {

    private static File inventoryConfigFile;
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
        inventoryConfigFile = new File(GamesMaster.plugin.getDataFolder(), "inventories.yml");
        if(!inventoryConfigFile.exists()) {
            copy(GamesMaster.plugin.getResource("inventories.yml"), inventoryConfigFile);
        }
        config = YamlConfiguration.loadConfiguration(inventoryConfigFile);
    }

    public static void saveConfig() {
        try {
            config.save(inventoryConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }
}
