package de.schmiereck.cellNet.state2;

import java.math.BigInteger;
import java.util.Arrays;

public class GridService {
    public static final int RULE_COUNT = 16;
    public static final int MAX_RULE_NR = 15;

    public static Grid createGrid(final int sizeX, final int sizeY, final int ruleNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = sizeY + 1;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        for (int y = 0; y < totalSizeY; y++) {
            grid.rowArr[y] = new Row(sizeX);
            grid.rowArr[y].cellArr = new Cell[sizeX];

            for (int x = 0; x < sizeX; x++) {
                final Cell cell = new Cell();
                cell.ruleNr = ruleNr;
                cell.value = 0;
                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    public static Grid createGrid(final int[] rowSizeXArr, final int sizeY, final int ruleNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = sizeY + 1;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        for (int y = 0; y < totalSizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            final int rowSizeX = rowSizeXArr[y == 0 ? 0 : y - 1];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];

            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();
                cell.ruleNr = ruleNr;
                cell.value = 0;
                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    public static void submitInput(final Grid grid, final int[] inputArr) {
        final Row inputRow = grid.rowArr[0];
        for (int x = 0; x < inputArr.length; x++) {
            if (x < inputRow.sizeX) {
                final Cell cell = inputRow.cellArr[x];
                cell.value = inputArr[x];
            } else {
                throw new RuntimeException("Input array to big for inputRow.");
            }
        }
    }

    public static int[] retieveOutput(final Grid grid) {
        final Row outoutRow = grid.rowArr[grid.sizeY - 1];
        final int[] outputArr = new int[outoutRow.sizeX];
        for (int x = 0; x < outoutRow.sizeX; x++) {
            final Cell cell = outoutRow.cellArr[x];
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
        final int sizeY = grid.sizeY;
        final Grid nextGrid = new Grid(sizeY);
        grid.rowArr = new Row[sizeY];
        // Zellen kopieren
        for (int y = 0; y < sizeY; y++) {
            final int rowSizeX = grid.rowArr[y].sizeX;
            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];
            for (int x = 0; x < rowSizeX; x++) {
                Cell orig = grid.rowArr[y].cellArr[x];
                Cell copy = new Cell();
                copy.ruleNr = orig.ruleNr;
                copy.value = 0; // value zurücksetzen
                nextGrid.rowArr[y].cellArr[x] = copy;
            }
        }
        // RuleNr-Kombination inkrementieren (wie Zähler)
        int carry = 1;
        outer:
        for (int y = 0; y < sizeY; y++) {
            final Row row = nextGrid.rowArr[y];
            for (int x = 0; x < row.sizeX; x++) {
                Cell cell = row.cellArr[x];
                if (carry == 0) break outer;
                int newRuleNr = cell.ruleNr + carry;
                if (newRuleNr > MAX_RULE_NR) {
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
            final Row row = nextGrid.rowArr[y];
            for (int x = 0; x < row.sizeX; x++) {
                if (row.cellArr[x].ruleNr != 0) {
                    allZero = false;
                    break;
                }
            }
            if (!allZero) break;
        }
        if (allZero) return null;
        return nextGrid;
    }

    /**
     * Erzeugt ein Grid, bei dem jede Zelle ab Zeile 1 ihre RuleNr entsprechend der Stellen im 256er-System von gridNr erhält.
     * Die Zellen werden zeilenweise (y,x) von oben links nach unten rechts befüllt.
     * Die erste Zeile (y=0) ist der Input-Layer und bekommt ruleNr=0.
     */
    public static Grid createGridForCombination(final int[] rowSizeXArr, final int sizeY, final java.math.BigInteger gridNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = sizeY + 1;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        java.math.BigInteger countNr = gridNr;
        for (int y = 0; y < totalSizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            final int rowSizeX = rowSizeXArr[y == 0 ? 0 : y - 1];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];
            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();
                if (y == 0) {
                    cell.ruleNr = 0; // Input-Layer
                } else {
                    cell.ruleNr = calcRuleNrByCountNr(countNr);
                    countNr = calcNextCountNr(countNr);
                }
                cell.value = 0;
                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    public static BigInteger calcMaxGridNr(final int[] rowSizeXArr) {
        final int cellCount = Arrays.stream(rowSizeXArr).sum();
        return BigInteger.valueOf(GridService.RULE_COUNT).pow(cellCount);
    }

    private static int calcRuleNrByCountNr(BigInteger countNr) {
        return countNr.mod(BigInteger.valueOf(RULE_COUNT)).intValue();
    }

    private static BigInteger calcNextCountNr(BigInteger countNr) {
        return countNr.divide(BigInteger.valueOf(RULE_COUNT));
    }

    public static void main(String[] args) {
        testCheckGeneratedRuleCombinations();
    }

    private static void testCheckGeneratedRuleCombinations() {
        final int[] rowSizeXArr = new int[]{2, 2}; // Input-Layer und eine Regel-Zeile
        final int sizeY = 1;

        final BigInteger maxGridNr = calcMaxGridNr(rowSizeXArr);

        BigInteger gridCount = BigInteger.valueOf(0L);
        while (gridCount.compareTo(maxGridNr) < 0) {
            System.out.printf("GridNr: %6d ", gridCount);
            final Grid grid = createGridForCombination(rowSizeXArr, sizeY, gridCount);

            for (int y = 0; y < grid.sizeY; y++) {
                final Row row = grid.rowArr[y];
                for (int x = 0; x < row.sizeX; x++) {
                    final Cell cell = row.cellArr[x];
                    System.out.printf("%3d ", cell.ruleNr);
                }
            }
            gridCount = gridCount.add(BigInteger.valueOf(1L));
            System.out.println();
        }
    }
}
