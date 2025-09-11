package de.schmiereck.cellNet.state3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellNetServiceTest {
    @Test
    public void testCalcGrid_rule30() {
        // Rule 30: 00011110 (binär), 30 (dezimal)
        int ruleNr = 30;
        int sizeX = 5;
        int sizeY = 3;
        Grid grid = new Grid(sizeX, sizeY);
        grid.cellArrArr = new Cell[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                grid.cellArrArr[y][x] = new Cell();
                grid.cellArrArr[y][x].ruleNr = ruleNr;
                grid.cellArrArr[y][x].value = 0;
            }
        }
        // Startwert: nur die mittlere Zelle in der ersten Zeile ist 1
        grid.cellArrArr[0][sizeX/2].value = 1;

        CellNetService.calcGrid(grid);

        // Erwartete Werte für Rule 30
        // Zeile 0: 0 0 1 0 0
        // Zeile 1: 0 1 1 1 0
        // Zeile 2: 1 1 0 0 1
        int[][] expected = {
            {0,0,1,0,0},
            {0,1,1,1,0},
            {1,1,0,0,1}
        };
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                assertEquals(expected[y][x], grid.cellArrArr[y][x].value, "Fehler bei Zeile "+y+", Spalte "+x);
            }
        }
    }
}

