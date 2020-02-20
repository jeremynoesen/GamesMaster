package me.Jeremaster101.GamesMaster.Lobby.Gadget;

/**
 * list of all gadgets
 */
public enum Gadget {
    FIREWORK_CANNON("FIREWORK_CANNON"),
    FIZZY_LIFTING_DRINK("FIZZY_LIFTING_DRINK"),
    GRAPPLING_HOOK("GRAPPLING_HOOK"),
    PAINTBALL_GUN("PAINTBALL_GUN"),
    SLIME_LAUNCHER("SLIME_LAUNCHER"),
    STORMBREAKER("STORMBREAKER");
    
    private String itemName;
    
    Gadget(String itemName) {
        this.itemName = itemName;
    }
    
    /**
     * @return string name of gadget
     */
    public String getName() {
        return itemName;
    }
    
}
