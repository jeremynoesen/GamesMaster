package jeremynoesen.gamesmaster.inventorytype;

import jeremynoesen.gamesmaster.inventory.Inventory;

import java.util.Collection;
import java.util.HashMap;

/**
 * inventories used for regions
 */
public class InventoryType { //todo dont edit config here
    
    private static HashMap<String, InventoryType> inventoryTypes = new HashMap<>();
    private String name;
    
    /**
     * adds a new inventory to the list of inventories
     *
     * @param name inventory name
     */
    public InventoryType(String name) {
        this.name = name;
        inventoryTypes.put(name, this);
    }
    
    /**
     * gets inventory by name
     *
     * @param name inventory name
     * @return inventory
     */
    public static InventoryType getInventoryType(String name) {
        return inventoryTypes.get(name);
    }
    
    /**
     * @return all inventories
     */
    public static Collection<InventoryType> getInventoryTypes() {
        return inventoryTypes.values();
    }
    
    /**
     * @return name of inventory
     */
    public String getName() {
        return name;
    }
    
    /**
     * removes inventory from config and plugin
     */
    public void remove() {
        inventoryTypes.remove(name);
        Inventory.getInventories().remove(this);
    }
    
    /**
     * @return true if the inventory is the default
     */
    public boolean isDefault() {
        return name.equals("default");
    }
    
}
