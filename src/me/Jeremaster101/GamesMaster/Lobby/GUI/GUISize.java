package me.Jeremaster101.GamesMaster.Lobby.GUI;

public enum GUISize {
    ONE_ROW(9), TWO_ROWS(18), THREE_ROWS(27), FOUR_ROWS(36), FIVE_ROWS(45), SIX_ROWS(54);
    
    private int size;
    
    GUISize(int size) {
        this.size = size;
    }
    
    public int getInteger() {
        return this.size;
    }
}
