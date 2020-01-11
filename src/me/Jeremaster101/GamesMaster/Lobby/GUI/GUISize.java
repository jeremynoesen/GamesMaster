package me.Jeremaster101.GamesMaster.Lobby.GUI;

/**
 * Used to keep GUI sizes multiples of 9
 */
public enum GUISize {
    ONE_ROW(9), TWO_ROWS(18), THREE_ROWS(27), FOUR_ROWS(36), FIVE_ROWS(45), SIX_ROWS(54);
    
    private int size;
    
    /**
     * @param size integer size
     */
    GUISize(int size) {
        this.size = size;
    }
    
    /**
     * @return integer size of GUISize
     */
    public int getInteger() {
        return this.size;
    }
}
