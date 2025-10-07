package de.schmiereck.cellNet.state2fry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridService {
    public static final int[] allRuleNrArr = new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    };

    public static final int[] booleanRuleNrArr = new int[] {
            // ZERO:    size: [2, 1], 2      RulePosNr: 0, 1, 2, 4, 6, 8, 10, 12, 14
            0,
            // ONE:    size: [2, 1], 2      RulePosNr: 27, 29, 30, 33, 35, 36, 39, 41, 42, 45, 46, 47, 48
            11,
            // IS:    size: [2, 1], 2      RulePosNr: 43
            //14,
            // NOT:    size: [2, 1], 2      RulePosNr: 4
            //4,

            // AND:     size: [2, 1], 2 RulePosNr: 25
            8,
            // OR:      size: [2, 1], 2 RulePosNr: 43
            14,
            // NAND:    size: [2, 1], 2 RulePosNr: 22
            7,
            // NOR:     size: [2, 1], 2 RulePosNr:  4
            1,
            // XOR:     size: [2, 1], 2 RulePosNr: 19
            6,
            // XNOR:    size: [2, 1], 2 RulePosNr: 28
            9
    };

    public static final int[] ruleNrArr = booleanRuleNrArr;
    //public static final int[] ruleNrArr = allRuleNrArr;

    public static final int RULE_COUNT = ruleNrArr.length; // 16
    public static final int MAX_RULE_NR = ruleNrArr.length - 1; // 15

    public static Grid createGridByRuleNr(final int sizeX, final int sizeY, final int ruleNr) {
        // sizeY = Anzahl der Regel-Zeilen (ohne Input-Layer)
        final int totalSizeY = sizeY;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        for (int y = 0; y < totalSizeY; y++) {
            grid.rowArr[y] = new Row(sizeX);
            grid.rowArr[y].cellArr = new Cell[sizeX];

            for (int x = 0; x < sizeX; x++) {
                final Cell cell = new Cell();
                cell.setRuleNr(ruleNr);
                cell.value = 0;
                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    public static Grid createGridByRuleNr(final int[] rowSizeXArr, final int ruleNr,
                                          final boolean noCommutative) {
        final int posNr = 0;
        return createGridByRuleNrAndPosNr(rowSizeXArr, ruleNr, posNr, noCommutative);
    }

    public record RulePosNrPair(int ruleNr, long posNr) {
        public RulePosNrPair(int ruleNr, long posNr) {
            this.ruleNr = ruleNr;
            this.posNr = posNr;
        }
    }

    public static Grid createGridByRulePosNr(final int[] rowSizeXArr, final long rulePosNr,
                                             final boolean noCommutative) {
        final RulePosNrPair rulePosNrPair = calcRulePosNrPair(rowSizeXArr, rulePosNr, noCommutative);
        return createGridByRuleNrAndPosNr(rowSizeXArr, rulePosNrPair.ruleNr, rulePosNrPair.posNr, noCommutative);
    }

    public static RulePosNrPair calcRulePosNrPair(int[] rowSizeXArr, long rulePosNr, boolean noCommutative) {
        final long maxPositionCombinations = PositionGridUtils.calcMaxPositionCombinations(rowSizeXArr, noCommutative);
        final int ruleNr;
        final long posNr;
        if (maxPositionCombinations == 0) {
            ruleNr = ((int) (rulePosNr));
            posNr = (rulePosNr);
        } else {
            ruleNr = ((int) (rulePosNr / maxPositionCombinations));
            posNr = (rulePosNr % maxPositionCombinations);
        }
        return new RulePosNrPair(ruleNr, posNr);
    }

    /**
     * Erweiterte createGrid Methode mit posNr Parameter für verschiedene Position-Kombinationen.
     */
    public static Grid createGridByRuleNrAndPosNr(final int[] rowSizeXArr,
                                                  final int ruleNr, final long posNr,
                                                  final boolean noCommutative) {
        // sizeY = Anzahl der Regel-Zeilen (erste Zeile ist der Input-Layer)
        final int totalSizeY = rowSizeXArr.length;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];

        long currentPosNr = posNr;

        // Input-Layer (y=0) ist die erste Zeile.
        for (int y = 0; y < totalSizeY; y++) {
            final int rowSizeX = rowSizeXArr[y];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];

            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();

                if (y > 0) {
                    cell.setRuleNr(ruleNr);
                    // Regel-Zeilen: Positions basierend auf posNr berechnen
                    // Input-Layer (y=0) bekommt die Standard-Positions
                    currentPosNr = PositionGridUtils.calcNextPositionsForPosNr(cell, rowSizeXArr, y, currentPosNr, noCommutative);
                } else {
                    // Input-Layer: Standard-Positions
                    cell.leftPosX = 0;
                    cell.rightPosX = 0;//Cell.RIGHT_OFF_X;
                    cell.setRuleNr(0); // Input-Layer
                }
                cell.value = 0;

                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    /**
     * Erzeugt ein Grid, bei dem jede Zelle ab Zeile 1 ihre RuleNr entsprechend der Stellen im 256er-System von gridNr erhält.
     * Die Zellen werden zeilenweise (y,x) von oben links nach unten rechts befüllt.
     * Die erste Zeile (y=0) ist der Input-Layer und bekommt ruleNr=0.
     */
    public static Grid createGridForCombination(final int[] rowSizeXArr, final BigInteger gridNr, final long posNr,
                                                final boolean noCommutative) {
        // sizeY = Anzahl der Regel-Zeilen (mit Input-Layer)
        final int totalSizeY = rowSizeXArr.length;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];

        long currentPosNr = posNr;
        BigInteger countNr = gridNr;

        for (int y = 0; y < totalSizeY; y++) {
            // Input-Layer (y=0) bekommt die Größe der ersten Regel-Zeile
            final int rowSizeX = rowSizeXArr[y];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];
            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();
                if (y > 0) {
                    cell.setRuleNr(calcRuleNrByCountNr(countNr));
                    //System.out.printf("%d-", cell.ruleNr);
                    countNr = calcNextCountNr(countNr);
                    currentPosNr = PositionGridUtils.calcNextPositionsForPosNr(cell, rowSizeXArr, y, currentPosNr, noCommutative);
                } else {
                    // Input-Layer: Standard-Positions
                    cell.leftPosX = 0;
                    cell.rightPosX = 0;//Cell.RIGHT_OFF_X;
                    cell.setRuleNr(0); // Input-Layer
                }
                cell.value = 0;

                grid.rowArr[y].cellArr[x] = cell;
            }
        }
        return grid;
    }

    /**
     * Erzeugt ein Grid, bei dem jede Zelle ab Zeile 1 ihre RuleNr entsprechend der Stellen im 256er-System von gridNr erhält.
     * Die Zellen werden zeilenweise (y,x) von oben links nach unten rechts befüllt.
     * Die erste Zeile (y=0) ist der Input-Layer und bekommt ruleNr=0.
     */
    public static Grid createGridForCombination(final int[] rowSizeXArr, final BigInteger gridNr) {
        // sizeY = Anzahl der Regel-Zeilen (mit Input-Layer)
        final int totalSizeY = rowSizeXArr.length;
        final Grid grid = new Grid(totalSizeY);
        grid.rowArr = new Row[totalSizeY];
        BigInteger countNr = gridNr;
        for (int y = 0; y < totalSizeY; y++) {
            final int rowSizeX = rowSizeXArr[y];

            grid.rowArr[y] = new Row(rowSizeX);
            grid.rowArr[y].cellArr = new Cell[rowSizeX];
            for (int x = 0; x < rowSizeX; x++) {
                final Cell cell = new Cell();
                if (y > 0) {
                    cell.setRuleNr(calcRuleNrByCountNr(countNr));
                    countNr = calcNextCountNr(countNr);
                } else {
                    cell.setRuleNr(0); // Input-Layer
                }
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

    public static Row retieveOutputRow(final Grid grid) {
        return grid.rowArr[grid.sizeY - 1];
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
                copy.setRuleNr(orig.getRuleNr());
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
                int newRuleNr = cell.getRuleNr() + carry;
                if (newRuleNr > MAX_RULE_NR) {
                    cell.setRuleNr(0);
                    carry = 1;
                } else {
                    cell.setRuleNr(newRuleNr);
                    carry = 0;
                }
            }
        }
        // Wenn nach dem Inkrementieren alle Zellen 0 sind, war die letzte Kombination erreicht
        boolean allZero = true;
        for (int y = 0; y < sizeY; y++) {
            final Row row = nextGrid.rowArr[y];
            for (int x = 0; x < row.sizeX; x++) {
                if (row.cellArr[x].getRuleNr() != 0) {
                    allZero = false;
                    break;
                }
            }
            if (!allZero) break;
        }
        if (allZero) return null;
        return nextGrid;
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

    private static int calcRuleNrByCountNr(final BigInteger countNr) {
        return countNr.mod(BigInteger.valueOf(RULE_COUNT)).intValue();
    }

    private static BigInteger calcNextCountNr(final BigInteger countNr) {
        return countNr.divide(BigInteger.valueOf(RULE_COUNT));
    }
}
