package me.Jeremaster101.GamesMaster.Config;

import me.Jeremaster101.GamesMaster.GamesMaster;

import java.io.File;
import java.io.InputStream;

/**
 * enum to make getting separate configs easier
 */
public enum ConfigType {
    COMMAND("commands.yml"), MESSAGE("messages.yml"), ARENA("arenas.yml"), GAME("games.yml"), LOBBY("lobby.yml"),
    GUI("GUI.yml"), INVENTORY("inventories.yml"), REGION("regions.yml"), GADGET("gadgets.yml");
    
    public String fileName;
    
    ConfigType(String file) {
        this.fileName = file;
    }
    
    /**
     * @return config file of the matching file name
     */
    public File getFile() {
        return new File(GamesMaster.getInstance().getDataFolder(), fileName);
    }
    
    /**
     * @return input stream of resource from inside plugin jar
     */
    public InputStream getResource() {
        return GamesMaster.getInstance().getResource(fileName);
    }
    
    /**
     * save resource from plugin jar to plugin folder
     */
    public void saveResource() {
        GamesMaster.getInstance().saveResource(fileName, false);
    }
}
