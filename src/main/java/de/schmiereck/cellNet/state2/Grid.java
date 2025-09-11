package de.schmiereck.cellNet.state2;

public class Grid {
    final int sizeX;
    final int sizeY;
    public Cell[][] cellArr;

    public Grid(final int sizeX, final int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}
