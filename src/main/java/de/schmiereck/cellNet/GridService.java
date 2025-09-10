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

    /**
     * Erzeugt ein neues Grid mit der nächsten RuleNr-Kombination für alle Zellen.
     * Die RuleNrs werden wie ein Zähler im 256er-System behandelt (0-255).
     * Gibt null zurück, wenn die letzte Kombination (alle 255) erreicht wurde.
     */
    public static Grid createNextRuleCombinationGrid(final Grid grid) {
        final int sizeX = grid.sizeX;
        final int sizeY = grid.sizeY;
        final Grid nextGrid = new Grid(sizeX, sizeY);
        nextGrid.cellArr = new Cell[sizeY][sizeX];
        // Zellen kopieren
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Cell orig = grid.cellArr[y][x];
                Cell copy = new Cell();
                copy.ruleNr = orig.ruleNr;
                copy.value = 0; // value zurücksetzen
                nextGrid.cellArr[y][x] = copy;
            }
        }
        // RuleNr-Kombination inkrementieren (wie Zähler)
        int carry = 1;
        outer:
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Cell cell = nextGrid.cellArr[y][x];
                if (carry == 0) break outer;
                int newRuleNr = cell.ruleNr + carry;
                if (newRuleNr > 255) {
                    cell.ruleNr = 0;
                    carry = 1;
                } else {
                    cell.ruleNr = newRuleNr;
                    carry = 0;
                }
            }
        }
        // Wenn nach dem Inkrementieren alle Zellen 0 sind, war die letzte Kombination erreicht
        boolean allZero = true;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (nextGrid.cellArr[y][x].ruleNr != 0) {
                    allZero = false;
                    break;
                }
            }
            if (!allZero) break;
        }
        if (allZero) return null;
        return nextGrid;
    }
}
