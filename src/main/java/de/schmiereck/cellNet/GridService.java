package de.schmiereck.cellNet;

public class GridService {
    public static Grid createGrid(final int sizeX, final int sizeY, final int ruleNr) {
        final Grid grid = new Grid(sizeX, sizeY);
        grid.cellArr = new Cell[sizeY][sizeX];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                final Cell cell = new Cell();
                cell.ruleNr = ruleNr;
                cell.value = 0;
                grid.cellArr[y][x] = cell;
            }
        }
        return grid;
    }

    public static void submitInput(final Grid grid, final int[] inputArr) {
        for (int x = 0; x < inputArr.length; x++) {
            if (x < grid.sizeX) {
                final Cell cell = grid.cellArr[0][x];
                cell.value = inputArr[x];
            } else {
                throw new RuntimeException("Input array to big.");
            }
        }
    }

    public static int[] retieveOutput(final Grid grid) {
        final int[] outputArr = new int[grid.sizeX];
        for (int x = 0; x < grid.sizeX; x++) {
            final Cell cell = grid.cellArr[grid.sizeY - 1][x];
            outputArr[x] = cell.value;
        }
        return outputArr;
    }
}
