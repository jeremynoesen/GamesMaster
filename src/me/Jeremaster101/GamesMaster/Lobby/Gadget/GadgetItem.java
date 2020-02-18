package me.Jeremaster101.GamesMaster.Lobby.Gadget;

import org.bukkit.inventory.ItemStack;

/**
 * all gadget items, including reload items
 */
public enum GadgetItem {
    FIREWORK_CANNON("FIREWORK_CANNON"),
    FIZZY_LIFTING_DRINK("FIZZY_LIFTING_DRINK"),
    GRAPPLING_HOOK("GRAPPLING_HOOK"),
    PAINTBALL_GUN("PAINTBALL_GUN"),
    SLIME_LAUNCHER("SLIME_LAUNCHER"),
    STORMBREAKER("STORMBREAKER"),
    FIREWORK_CANNON_RELOAD("FIREWORK_CANNON_RELOAD"),
    FIZZY_LIFTING_DRINK_RELOAD("FIZZY_LIFTING_DRINK_RELOAD"),
    GRAPPLING_HOOK_RELOAD("GRAPPLING_HOOK_RELOAD"),
    PAINTBALL_GUN_RELOAD("PAINTBALL_GUN_RELOAD"),
    SLIME_LAUNCHER_RELOAD("SLIME_LAUNCHER_RELOAD"),
    STORMBREAKER_RELOAD("STORMBREAKER_RELOAD");
    
    public String itemName;
    
    GadgetItem(String itemName) {
        this.itemName = itemName;
    }
    
    /**
     * @return string name of item
     */
    public String getName() {
        return itemName;
    }
    
    /**
     * @return item by name
     */
    public ItemStack getItem() {
        return GadgetItemBuilder.getItem(itemName);
    }
}
