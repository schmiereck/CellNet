package de.schmiereck.cellNet.state2free;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class GridService {
    public static final int RULE_COUNT = 16;
    public static final int MAX_RULE_NR = 15;

    public static Grid createGridByRuleNr(final int sizeX, final int sizeY, final int ruleNr) {
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

    private static int calcMaxOffsetCombinations(int cellCount) {
        return 1 << cellCount;
    }


    public static Grid createGridByRuleNr(final int[] rowSizeXArr, final int ruleNr) {
        final int offNr = 0;
        return createGridByRuleNrAndOffNr(rowSizeXArr, ruleNr, offNr);
    }

    public static Grid createGridByRuleOffNr(final int[] rowSizeXArr, final int ruleOffNr) {
        final int maxOffsetCombinations = GridService.calcMaxOffsetCombinations(rowSizeXArr);
        final int ruleNr = ruleOffNr / maxOffsetCombinations;
        final int offNr = ruleOffNr % maxOffsetCombinations;
        return createGridByRuleNrAndOffNr(rowSizeXArr, ruleNr, offNr);
    }

    /**
     * Erweiterte createGrid Methode mit offNr Parameter für verschiedene Offset-Kombinationen.
     */
    public static Grid createGridByRuleNrAndOffNr(final int[] rowSizeXArr, final int ruleNr, final int offNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = rowSizeXArr.length + 1;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];

        int currentOffNr = offNr;

        for (int y = 0; y < totalSizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            final int rowSizeX = rowSizeXArr[y == 0 ? 0 : y - 1];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];

            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();
                cell.ruleNr = ruleNr;
                cell.value = 0;

                currentOffNr = calcOffsetsForOffNr(cell, rowSizeXArr, y, currentOffNr);

                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    /**
     * Berechnet die maximale Anzahl der Offset-Kombinationen für die gegebenen Zeilegrößen.
     * Nutzt die neue calculateOffsetCombinations-Logik ([i,i] und nur [1,0] für parentRowSizeX > 1).
     */
    public static int calcMaxOffsetCombinations(final int[] rowSizeXArr) {
        int maxCombinations = 1;
        for (int y = 1; y < rowSizeXArr.length; y++) {
            final int parentRowSizeX = rowSizeXArr[y - 1];
            final int currentRowSizeX = rowSizeXArr[y];
            if (currentRowSizeX > 0) {
                final int combinationsForThisRow = parentRowSizeX + ((parentRowSizeX > 1) ? 1 : 0);
                maxCombinations *= Math.pow(combinationsForThisRow, currentRowSizeX);
            }
        }
        return maxCombinations;
    }

    public static int calcOffsetsForOffNr(final Cell cell, final int[] rowSizeXArr, final int y, final int currentOffNr) {
        final int retOffNr;
        if (y == 0) {
            // Input-Layer: Standard-Offsets
            cell.leftOffX = 0;
            cell.rightOffX = Cell.RIGHT_OFF_X;
            retOffNr = currentOffNr;
        } else {
            // Regel-Zeilen: Offsets basierend auf offNr berechnen
            final int parentRowSizeX = rowSizeXArr[y - 1];
            final List<int[]> offsetCombinations = calculateOffsetCombinations(parentRowSizeX);

            if (!offsetCombinations.isEmpty()) {
                final int[] selectedOffset = offsetCombinations.get(currentOffNr % offsetCombinations.size());
                cell.leftOffX = selectedOffset[0];
                cell.rightOffX = selectedOffset[1];
                retOffNr = currentOffNr / offsetCombinations.size();
            } else {
                // Fallback auf Standard-Offsets
                cell.leftOffX = 0;
                cell.rightOffX = Cell.RIGHT_OFF_X;
                retOffNr = currentOffNr;
            }
        }
        return retOffNr;
    }

    /**
     * Berechnet alle möglichen leftOffX/rightOffX Kombinationen für eine gegebene Breite der Parent-Row.
     * Erlaubt Kombinationen, bei denen beide Eingänge auf denselben Parent zeigen (z.B. [0,0], [1,1], ...).
     * Zusätzlich nur die Kombination [1,0] für parentRowSizeX > 1.
     */
    public static List<int[]> calculateOffsetCombinations(final int parentRowSizeX) {
        final List<int[]> combinations = new ArrayList<>();
        // [i,i] Kombinationen
        for (int i = 0; i < parentRowSizeX; i++) {
            combinations.add(new int[]{i, i});
        }
        // Nur [1,0] Kombination für parentRowSizeX > 1
        if (parentRowSizeX > 1) {
            combinations.add(new int[]{1, 0});
        }
        return combinations;
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
    public static Grid createGridForCombination(final int[] rowSizeXArr, final BigInteger gridNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = rowSizeXArr.length + 1;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        BigInteger countNr = gridNr;
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
        final int cellCount = calcCellCount(rowSizeXArr);
        return BigInteger.valueOf(GridService.RULE_COUNT).pow(cellCount);
    }

    public static int calcCellCount(final int[] rowSizeXArr) {
        return Arrays.stream(rowSizeXArr).sum();
    }

    public static int calcMaxRuleNr() {
        return GridService.MAX_RULE_NR;
    }

    private static int calcRuleNrByCountNr(BigInteger countNr) {
        return countNr.mod(BigInteger.valueOf(RULE_COUNT)).intValue();
    }

    private static BigInteger calcNextCountNr(BigInteger countNr) {
        return countNr.divide(BigInteger.valueOf(RULE_COUNT));
    }
}
